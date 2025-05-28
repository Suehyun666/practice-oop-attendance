package attendance.domain.model;

import java.time.LocalTime;

public class AttendanceTime {
    private final LocalTime value;

    public AttendanceTime(LocalTime value) {
        this.value = value;
    }

    public AttendanceTime(String timeString) {
        String[] parts = timeString.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        this.value = LocalTime.of(hour, minute);
    }

    public LocalTime getValue() {
        return value;
    }

    public String format() {
        return String.format("%02d:%02d", value.getHour(), value.getMinute());
    }
}
