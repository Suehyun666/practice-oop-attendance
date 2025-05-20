package attendance.service;

import attendance.AttendanceRecord;
import attendance.AttendanceRepository;
import attendance.domain.RiskCrew;
import attendance.domain.DisciplinaryStatus;
import attendance.domain.Nickname;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RiskManagementService {
    private final AttendanceRepository repository;
    private final AttendanceService attendanceService;

    public RiskManagementService(AttendanceRepository repository, AttendanceService attendanceService) {
        this.repository = repository;
        this.attendanceService = attendanceService;
    }

    public List<RiskCrew> findRiskCrews() {
        List<RiskCrew> riskCrews = new ArrayList<>();
        List<Nickname> nicknames = repository.findAllNicknames();

        for (Nickname nickname : nicknames) {
            AttendanceRecord record = attendanceService.getAttendanceRecord(nickname);
            DisciplinaryStatus status = record.getDisciplinaryStatus();

            if (status != DisciplinaryStatus.NORMAL) {
                riskCrews.add(new RiskCrew(
                        nickname,
                        record.getAbsentCount(),
                        record.getLateCount(),
                        status
                ));
            }
        }

        Collections.sort(riskCrews);
        return riskCrews;
    }
}