package attendance;

import attendance.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

public class AttendanceRecord {
    private final Nickname nickname;
    private final List<Attendance> attendances;
    private static final int LATE_COUNT_FOR_ABSENT = 3;

    public AttendanceRecord(Nickname nickname, List<Attendance> attendances) {
        this.nickname = nickname;
        this.attendances = attendances.stream()
                .filter(a -> a.getNickname().getValue().equals(nickname.getValue()))
                .collect(Collectors.toList());
    }

    public Nickname getNickname() {
        return nickname;
    }

    public List<Attendance> getAttendances() {
        return attendances.stream()
                .sorted(Comparator.comparing(a -> a.getDate().getValue()))
                .collect(Collectors.toList());
    }

    public int getPresentCount() {
        return (int) attendances.stream()
                .filter(a -> a.getStatus() == AttendanceStatus.PRESENT)
                .count();
    }

    public int getLateCount() {
        return (int) attendances.stream()
                .filter(a -> a.getStatus() == AttendanceStatus.LATE)
                .count();
    }

    public int getDirectAbsentCount() {
        return (int) attendances.stream()
                .filter(a -> a.getStatus() == AttendanceStatus.ABSENT)
                .count();
    }

    public int getAbsentCount() {
        return getDirectAbsentCount() + (getLateCount() / LATE_COUNT_FOR_ABSENT);
    }

    public DisciplinaryStatus getDisciplinaryStatus() {
        int absentCount = getAbsentCount();

        if (absentCount > 5) {
            return DisciplinaryStatus.EXPULSION;
        }
        if (absentCount >= 3) {
            return DisciplinaryStatus.INTERVIEW;
        }
        if (absentCount >= 2) {
            return DisciplinaryStatus.WARNING;
        }
        return DisciplinaryStatus.NORMAL;
    }
}