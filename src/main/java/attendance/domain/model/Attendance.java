package attendance.domain.model;

import java.time.LocalTime;
import attendance.domain.enums.*;
import attendance.Constants;

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

    public AttendanceStatus getStatus() {
        Week dayOfWeek = Week.from(date.getValue());
        LocalTime startTime = dayOfWeek.getStartTime();
        LocalTime attendanceTime = time.getValue();
        if (attendanceTime.isBefore(startTime) || attendanceTime.equals(startTime) || attendanceTime.isBefore(startTime.plusMinutes(Constants.PRESENT_GRACE_MINUTES + 1))) {
            return AttendanceStatus.PRESENT;}
        if (attendanceTime.isBefore(startTime.plusMinutes(Constants.LATE_LIMIT_MINUTES + 1))) {
            return AttendanceStatus.LATE;}
        return AttendanceStatus.ABSENT;
    }

    @Override
    public String toString() {
        return String.format("%d월 %02d일 %s %s (%s)",
                date.getValue().getMonthValue(),
                date.getValue().getDayOfMonth(),
                date.getDayOfWeekName(),
                time.format(),
                getStatus().getDescription());
    }
}

