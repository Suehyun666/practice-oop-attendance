package attendance;

import attendance.domain.*;
import attendance.service.AttendanceService;
import attendance.view.InputView;
import attendance.view.ResultView;

import java.util.List;

public class AttendanceController {
    private final InputView inputView;
    private final ResultView resultView;
    private final AttendanceService attendanceService;

    public AttendanceController(InputView inputView, ResultView resultView, AttendanceService attendanceService) {
        this.inputView = inputView;
        this.resultView = resultView;
        this.attendanceService = attendanceService;
    }

    public void run() {
        while (true) {
            try {
                String menu = inputView.inputMenu();
                if (menu.equalsIgnoreCase("Q")) {
                    break;
                }
                processMenu(menu);
            } catch (Exception e) {
                resultView.printError(e.getMessage());
            }
        }
    }

    private void processMenu(String menu) {
        switch (menu) {
            case "1":
                processAttendanceCheck();
                break;
            case "2":
                processAttendanceUpdate();
                break;
            default:
                throw new IllegalArgumentException("None Menu");
        }
    }

    private void processAttendanceCheck() {
        String nicknameInput = inputView.inputNickname();
        Nickname nickname = new Nickname(nicknameInput);

        String timeInput = inputView.inputTime();
        AttendanceTime time = new AttendanceTime(timeInput);

        Attendance attendance = attendanceService.checkAttendance(nickname, time);
        resultView.printAttendanceResult(attendance);
    }

    private void processAttendanceUpdate() {
        String nicknameInput = inputView.inputAttendanceNickname();
        String dateInput = inputView.inputDate();
        String timeInput = inputView.inputChangeTime();

        Nickname nickname = new Nickname(nicknameInput);
        AttendanceDate date = new AttendanceDate(Integer.parseInt(dateInput));
        AttendanceTime time = new AttendanceTime(timeInput);

        Attendance newAttendance = attendanceService.updateAttendance(nickname, date, time);

        Attendance oldAttendance = new Attendance(nickname, date, new AttendanceTime("00:00"));

        resultView.printUpdateResult(oldAttendance, newAttendance);
    }

}