package attendance;

import attendance.service.AttendanceService;
import attendance.view.InputView;
import attendance.view.ResultView;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        AttendanceController controller = new AttendanceController(
                new InputView(new Scanner(System.in)),
                new ResultView(),
                new AttendanceService(new AttendanceRepository("src/main/resources/attendances.csv"))
        );
        controller.run();
    }
}
