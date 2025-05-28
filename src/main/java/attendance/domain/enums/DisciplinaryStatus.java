package attendance.domain.enums;

public enum DisciplinaryStatus {
    NORMAL("정상"),
    WARNING("경고"),
    INTERVIEW("면담"),
    EXPULSION("제적");

    private final String description;

    DisciplinaryStatus(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public int getAttendance() {
        return this.ordinal();
    }
}