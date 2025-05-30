package attendance.controller;

import attendance.domain.analysis.AttendanceRecord;
import attendance.domain.analysis.RiskCrew;
import attendance.domain.model.Attendance;
import attendance.domain.model.AttendanceDate;
import attendance.domain.model.AttendanceTime;
import attendance.domain.model.Nickname;
import attendance.service.AttendanceService;
import attendance.view.*;

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
            case "3":
                processAttendanceRecord();
                break;
            case "4":
                processRiskCrews();
                break;
            default:
                throw new IllegalArgumentException("None Menu");
        }
    }

    private void processAttendanceCheck() {
        attendanceService.checkDay();

        String nicknameInput = inputView.inputNickname();
        Nickname nickname = new Nickname(nicknameInput);
        attendanceService.checkNickname(nickname);

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

        Attendance oldAttendance = attendanceService.getOldAttendance(nickname,date);
        Attendance newAttendance = attendanceService.updateAttendance(nickname, date, time);
        
        resultView.printUpdateResult(oldAttendance, newAttendance);
    }

    private void processAttendanceRecord() {
        String nicknameInput = inputView.inputNickname();
        Nickname nickname = new Nickname(nicknameInput);

        AttendanceRecord record = attendanceService.getAttendanceRecord(nickname);
        resultView.printAttendanceRecord(record);
    }

    private void processRiskCrews() {
        List<RiskCrew> riskCrews = attendanceService.getRiskCrews();
        resultView.printRiskCrews(riskCrews);
    }
}