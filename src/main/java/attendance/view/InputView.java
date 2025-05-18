package attendance.view;

import java.time.LocalDate;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String inputMenu() {
        System.out.println("\n오늘은 " + formatToday() + "입니다. 기능을 선택해 주세요.");
        System.out.println("1. 출석 확인");
        System.out.println("2. 출석 수정");
        System.out.println("3. 크루별 출석 기록 확인");
        System.out.println("4. 제적 위험자 확인");
        System.out.println("Q. 종료");
        return scanner.nextLine();
    }

    public String inputNickname() {
        System.out.println("\n닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String inputAttendanceNickname() {
        System.out.println("\n출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String inputTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String inputDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return scanner.nextLine();
    }

    public String inputChangeTime() {
        System.out.println("언제로 변경하겠습니까?");
        return scanner.nextLine();
    }

    private String formatToday() {
        LocalDate today = LocalDate.of(2025,12,12);
        String dayOfWeek = switch (today.getDayOfWeek()) {
            case MONDAY -> "월요일";
            case TUESDAY -> "화요일";
            case WEDNESDAY -> "수요일";
            case THURSDAY -> "목요일";
            case FRIDAY -> "금요일";
            case SATURDAY -> "토요일";
            case SUNDAY -> "일요일";
        };
        return String.format("12월 %d일 %s", today.getDayOfMonth(), dayOfWeek);
    }
}