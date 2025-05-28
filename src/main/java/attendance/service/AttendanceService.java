package attendance.service;

import attendance.repository.AttendanceRepository;
import attendance.Constants;
import attendance.domain.enums.*;
import attendance.domain.model.*;
import attendance.domain.analysis.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AttendanceService {
    private final AttendanceRepository repository;

    public AttendanceService(AttendanceRepository repository) {
        this.repository = repository;
    }

    public void checkDay(){
        LocalDate today = Constants.getToday();
        AttendanceDate date = new AttendanceDate(today);
        if (!date.isAttendanceDay()) {
            throw new IllegalArgumentException("오늘은 출석일이 아닙니다.");
        }
    }
    public void checkNickname(Nickname nickname){
        List<Nickname> nicknames = repository.findAllNicknames();
        boolean exists = nicknames.stream()
                .anyMatch(n -> n.getValue().equals(nickname.getValue()));
        if (!exists) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
    }

    public Attendance checkAttendance(Nickname nickname, AttendanceTime time) {
        LocalDate today = Constants.getToday();
        AttendanceDate date = new AttendanceDate(today);

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

        repository.update(nickname, date, time);
        return new Attendance(nickname, date, time);
    }

    public Attendance getOldAttendance(Nickname nickname, AttendanceDate date){
        return repository.findByNicknameAndDate(nickname, date);
    }

    public AttendanceRecord getAttendanceRecord(Nickname nickname) {
        List<Attendance> attendances = repository.findAllByNickname(nickname);
        return new AttendanceRecord(nickname, attendances);
    }

    public List<RiskCrew> getRiskCrews() {
        List<RiskCrew> riskCrews = new ArrayList<>();
        List<Nickname> nicknames = repository.findAllNicknames();

        for (Nickname nickname : nicknames) {
            try {
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
            } catch (Exception e) {
                System.out.println("Debug: " + nickname.getValue() + " 처리 중 오류: " + e.getMessage());
            }
        }

        Collections.sort(riskCrews);
        return riskCrews;
    }
}