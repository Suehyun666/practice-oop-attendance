package attendance.domain.enums;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public enum Week {
    MONDAY(DayOfWeek.MONDAY, LocalTime.of(13, 0), "월요일"),
    TUESDAY(DayOfWeek.TUESDAY, LocalTime.of(10, 0), "화요일"),
    WEDNESDAY(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), "수요일"),
    THURSDAY(DayOfWeek.THURSDAY, LocalTime.of(10, 0), "목요일"),
    FRIDAY(DayOfWeek.FRIDAY, LocalTime.of(10, 0), "금요일"),
    SATURDAY(DayOfWeek.SATURDAY, null, "토요일"),
    SUNDAY(DayOfWeek.SUNDAY, null, "일요일");

    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;
    private final String koreanName;

    Week(DayOfWeek dayOfWeek, LocalTime startTime, String koreanName) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.koreanName = koreanName;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public static boolean isAttendanceDay(LocalDate date) {
        //휴일 로직 추가 예정
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY &&
                dayOfWeek != DayOfWeek.SUNDAY;
    }

    public static Week from(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        for (Week day : values()) {
            if (day.dayOfWeek == dayOfWeek) {
                return day;
            }
        }
        throw new IllegalArgumentException("출석일이 아닙니다: " + date);
    }

    public static String getKoreanName(DayOfWeek dayOfWeek) {
        return from(dayOfWeek).getKoreanName();
    }

    private static Week from(DayOfWeek dayOfWeek) {
        for (Week week : values()) {
            if (week.dayOfWeek == dayOfWeek) {
                return week;
            }
        }
        throw new IllegalArgumentException("알 수 없는 요일입니다: " + dayOfWeek);
    }
}