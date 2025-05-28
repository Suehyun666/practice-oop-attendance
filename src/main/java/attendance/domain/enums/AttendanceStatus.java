package attendance.domain.enums;

public enum AttendanceStatus {
    PRESENT("출석"),
    LATE("지각"),
    ABSENT("결석");

    private final String description;
    public String getDescription() {
        return description;
    }
    AttendanceStatus(String description) {
        this.description = description;
    }
}
