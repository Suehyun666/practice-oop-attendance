package attendance.view;

import attendance.AttendanceRecord;
import attendance.domain.*;

import java.util.List;

public class ResultView {
    public void printAttendanceResult(Attendance attendance) {
        System.out.println("\n" + attendance);
    }

    public void printError(String message) {
        System.out.println("\n[ERROR] " + message);
    }
}