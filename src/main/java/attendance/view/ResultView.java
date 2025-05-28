package attendance.view;

import attendance.domain.analysis.AttendanceRecord;
import attendance.domain.model.*;
import attendance.domain.analysis.*;
import java.util.List;

public class ResultView {
    public void printAttendanceResult(Attendance attendance) {
        System.out.println("\n" + attendance);
    }

    public void printUpdateResult(Attendance oldAttendance, Attendance newAttendance) {
        System.out.printf("\n%s -> %s (%s) 수정 완료!\n",
                oldAttendance,
                newAttendance.getTime().format(),
                newAttendance.getStatus().getDescription());
    }

    public void printAttendanceRecord(AttendanceRecord record) {
        System.out.printf("\n이번 달 %s의 출석 기록입니다.\n\n", record.getNickname().getValue());

        for (Attendance attendance : record.getAttendances()) {
            System.out.println(attendance);
        }

        System.out.println();
        System.out.printf("출석: %d회\n", record.getPresentCount());
        System.out.printf("지각: %d회\n", record.getLateCount());
        System.out.printf("결석: %d회\n\n", record.getAbsentCount());

        switch (record.getDisciplinaryStatus()) {
            case WARNING:
                System.out.println("경고 대상자입니다.");
                break;
            case INTERVIEW:
                System.out.println("면담 대상자입니다.");
                break;
            case EXPULSION:
                System.out.println("제적 대상자입니다.");
                break;
            default:
                break;
        }
    }

    public void printRiskCrews(List<RiskCrew> riskCrews) {
        System.out.println("\n제적 위험자 조회 결과");
        for (RiskCrew crew : riskCrews) {
            System.out.println(crew);
        }
    }

    public void printError(String message) {
        System.out.println("\n[ERROR] " + message);
    }
}