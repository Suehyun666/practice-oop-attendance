package attendance;

import java.time.LocalDate;

public class Constants {
    public static final int YEAR = 2024;
    public static final int MONTH = 12;
    public static final int TODAY_DAY = 13;
    public static final int LATE_COUNT_FOR_ABSENT = 3;

    public static final int PRESENT_GRACE_MINUTES = 5;
    public static final int LATE_LIMIT_MINUTES = 30;
    t
    // 현재 날짜 반환
    public static LocalDate getToday() {
        //return LocalDate.now();
        return LocalDate.of(YEAR, MONTH, TODAY_DAY);
    }
}