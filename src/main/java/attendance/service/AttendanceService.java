package attendance.service;

import attendance.AttendanceRecord;
import attendance.AttendanceRepository;
import attendance.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.HashSet;

public class AttendanceService {
    private final AttendanceRepository repository;

    public AttendanceService(AttendanceRepository repository) {
        this.repository = repository;
    }

    public Attendance checkAttendance(Nickname nickname, AttendanceTime time) {
        LocalDate today = LocalDate.of(2024,12,13);
        AttendanceDate date = new AttendanceDate(today);

        if (!date.isAttendanceDay()) {
            throw new IllegalArgumentException("오늘은 출석일이 아닙니다.");
        }

        if (repository.existsByNicknameAndDate(nickname, date)) {
            throw new IllegalArgumentException("이미 출석한 기록이 있습니다. 수정 기능을 이용해주세요.");
        }

        Attendance attendance = new Attendance(nickname, date, time);
        repository.save(attendance);
        return attendance;
    }

    public Attendance updateAttendance(Nickname nickname, AttendanceDate date, AttendanceTime time) {
        if (!repository.existsByNicknameAndDate(nickname, date)) {
            throw new IllegalArgumentException("해당 날짜에 출석 기록이 없습니다.");
        }

        List<Attendance> attendances = repository.findAllByNickname(nickname);
        Attendance oldAttendance = attendances.stream()
                .filter(a -> a.getDate().getValue().equals(date.getValue()))
                .findFirst()
                .orElseThrow();

        repository.update(nickname, date, time);
        //정리 필요
        Attendance newAttendance = new Attendance(nickname, date, time);
        return newAttendance;
    }

    public Attendance getOldAttendance(Nickname nickname, AttendanceDate date){
        return this.repository.findByNicknameAndDate(nickname,date);
    }

    public AttendanceRecord getAttendanceRecord(Nickname nickname) {
        List<Attendance> attendances = repository.findAllByNickname(nickname);
        return new AttendanceRecord(nickname, attendances);
    }

    public List<RiskCrew> getRiskCrews() {
        List<RiskCrew> riskCrews = new ArrayList<>();
        // 중복 방지를 위한 Set 사용
        Set<String> processedNicknames = new HashSet<>();

        List<Nickname> nicknames = repository.findAllNicknames();

        for (Nickname nickname : nicknames) {
            String nicknameValue = nickname.getValue();

            // 이미 처리한 닉네임은 건너뛰기
            if (processedNicknames.contains(nicknameValue)) {
                continue;
            }

            processedNicknames.add(nicknameValue);

            AttendanceRecord record = getAttendanceRecord(nickname);
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