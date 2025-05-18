package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {
    private final Nickname nickname;
    private final AttendanceDate date;
    private final AttendanceTime time;

    public Attendance(Nickname nickname, AttendanceDate date, AttendanceTime time) {
        this.nickname = nickname;
        this.date = date;
        this.time = time;
    }

    public Nickname getNickname() {
        return nickname;
    }

    public AttendanceDate getDate() {
        return date;
    }

    public AttendanceTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return String.format("12월 %02d일 %s %s (%s)",
                date.getValue().getDayOfMonth(),
                date.getDayOfWeekName(),
                time.format(),
                getStatus().getDescription());
    }
}

