package attendance.domain.model;

import java.time.LocalDate;
import attendance.domain.enums.*;
import attendance.Constants;
public class AttendanceDate {
    private final LocalDate value;

    public AttendanceDate(LocalDate value) {
        this.value = value;
    }

    public AttendanceDate(int day) {
        this.value = LocalDate.of(Constants.YEAR, Constants.MONTH, day);
    }

    public LocalDate getValue() {
        return value;
    }

    public String getDayOfWeekName() {
        return Week.getKoreanName(value.getDayOfWeek());
    }

    public boolean isAttendanceDay() {
        return Week.isAttendanceDay(value);
    }
}
