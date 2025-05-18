package attendance.domain;

import java.time.LocalDate;

public class AttendanceDate {
    private final LocalDate value;

    public AttendanceDate(LocalDate value) {
        this.value = value;
    }

    public AttendanceDate(int day) {
        this.value = LocalDate.of(2024, 12, day);
    }

    public LocalDate getValue() {
        return value;
    }

    public String getDayOfWeekName() {
        java.time.DayOfWeek dayOfWeek = value.getDayOfWeek();
        switch (dayOfWeek) {
            case MONDAY: return "월요일";
            case TUESDAY: return "화요일";
            case WEDNESDAY: return "수요일";
            case THURSDAY: return "목요일";
            case FRIDAY: return "금요일";
            case SATURDAY: return "토요일";
            case SUNDAY: return "일요일";
            default: throw new IllegalStateException("알 수 없는 요일입니다.");
        }
    }

    public boolean isAttendanceDay() {
        return DayOfWeek.isAttendanceDay(value);
    }
}
