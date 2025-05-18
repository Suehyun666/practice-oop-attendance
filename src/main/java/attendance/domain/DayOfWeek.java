package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public enum DayOfWeek {
    MONDAY(DayOfWeek.MONDAY, LocalTime.of(13, 0)),
    TUESDAY(DayOfWeek.TUESDAY, LocalTime.of(10, 0)),
    WEDNESDAY(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0)),
    THURSDAY(DayOfWeek.THURSDAY, LocalTime.of(10, 0)),
    FRIDAY(DayOfWeek.FRIDAY, LocalTime.of(10, 0));

    private final LocalTime startTime;

    DayOfWeek(DayOfWeek dayOfWeek, LocalTime startTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
    }

    public DayOfWeek getJavaDayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public static boolean isAttendanceDay(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY &&
                dayOfWeek != DayOfWeek.SUNDAY;
    }

    public static DayOfWeek getDayOfWeek(LocalDate date) {
        DayOfWeek javaDayOfWeek = date.getDayOfWeek();
        for (DayOfWeek day : values()) {
            if (day.dayOfWeek == javaDayOfWeek) {
                return day;
            }
        }
        throw new IllegalArgumentException("출석일이 아닙니다: " + date);
    }
}