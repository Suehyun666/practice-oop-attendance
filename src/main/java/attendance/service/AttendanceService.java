package attendance.service;

import attendance.AttendanceRecord;
import attendance.AttendanceRepository;
import attendance.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AttendanceService {
    private final AttendanceRepository repository;

    public AttendanceService(AttendanceRepository repository) {
        this.repository = repository;
    }

    public Attendance checkAttendance(Nickname nickname, AttendanceTime time) {
        LocalDate today = LocalDate.of(2025,12,12); //수정 예정
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

        Attendance newAttendance = new Attendance(nickname, date, time);
        return newAttendance;
    }

    public AttendanceRecord getAttendanceRecord(Nickname nickname) {
        List<Attendance> attendances = repository.findAllByNickname(nickname);
        return new AttendanceRecord(nickname, attendances);
    }

}