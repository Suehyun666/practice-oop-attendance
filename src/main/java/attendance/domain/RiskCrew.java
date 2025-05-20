package attendance.domain;

public class RiskCrew implements Comparable<RiskCrew> {
    private final Nickname nickname;
    private final int absentCount;
    private final int lateCount;
    private final DisciplinaryStatus status;

    public RiskCrew(Nickname nickname, int absentCount, int lateCount, DisciplinaryStatus status) {
        this.nickname = nickname;
        this.absentCount = absentCount;
        this.lateCount = lateCount;
        this.status = status;
    }

    public Nickname getNickname() {
        return nickname;
    }

    public int getAbsentCount() {
        return absentCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    public DisciplinaryStatus getStatus() {
        return status;
    }

    @Override
    public int compareTo(RiskCrew other) {
        // 상태 비교 (제적 > 면담 > 경고)
        int statusComparison = other.status.ordinal() - this.status.ordinal();
        if (statusComparison != 0) {
            return statusComparison;
        }

        // 결석 횟수 비교 (내림차순)
        int absentComparison = other.absentCount - this.absentCount;
        if (absentComparison != 0) {
            return absentComparison;
        }

        // 지각 횟수 비교 (내림차순)
        int lateComparison = other.lateCount - this.lateCount;
        if (lateComparison != 0) {
            return lateComparison;
        }

        // 닉네임 비교 (오름차순)
        return this.nickname.getValue().compareTo(other.nickname.getValue());
    }

    @Override
    public String toString() {
        return String.format("- %s: 결석 %d회, 지각 %d회 (%s)",
                nickname.getValue(), absentCount, lateCount, status.getDescription());
    }
}