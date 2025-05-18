package attendance.view;

import attendance.AttendanceRecord;
import attendance.domain.*;

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

    public void printError(String message) {
        System.out.println("\n[ERROR] " + message);
    }
}