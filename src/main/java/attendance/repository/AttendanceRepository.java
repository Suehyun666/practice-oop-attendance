package attendance.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Comparator;
import attendance.domain.model.*;

public class AttendanceRepository {
    private final List<Attendance> attendances;
    private final String filePath;

    public AttendanceRepository(String filePath) {
        this.filePath = filePath;
        this.attendances = loadAttendances();
    }

    private List<Attendance> loadAttendances() {
        List<Attendance> loadedAttendances = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // 헤더 건너뛰기
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String nicknameValue = parts[0];
                String dateTimeString = parts[1];

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);

                Nickname nickname = new Nickname(nicknameValue);
                AttendanceDate date = new AttendanceDate(dateTime.toLocalDate());
                AttendanceTime time = new AttendanceTime(dateTime.toLocalTime());

                loadedAttendances.add(new Attendance(nickname, date, time));
            }
        } catch (IOException e) {
            throw new RuntimeException("출석 데이터를 불러오는데 실패했습니다.", e);
        }
        return loadedAttendances;
    }

    public List<Attendance> findAllByNickname(Nickname nickname) {
        return attendances.stream()
                .filter(a -> a.getNickname().getValue().equals(nickname.getValue()))
                .collect(Collectors.toList());

    }

    public Attendance findByNicknameAndDate(Nickname nickname, AttendanceDate date) {
        return attendances.stream()
                .filter(a -> a.getNickname().getValue().equals(nickname.getValue()))
                .filter(a -> a.getDate().getValue().equals(date.getValue()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 닉네임입니다."));
    }

    public boolean existsByNicknameAndDate(Nickname nickname, AttendanceDate date) {
        return attendances.stream()
                .anyMatch(a -> a.getNickname().getValue().equals(nickname.getValue()) &&
                        a.getDate().getValue().equals(date.getValue()));
    }

    public void save(Attendance attendance) {
        attendances.add(attendance);
        // 실제 파일에 저장하는 로직은 없음
    }

    public void update(Nickname nickname, AttendanceDate date, AttendanceTime time) {
        // 기존 출석 기록 제거
        attendances.removeIf(a -> a.getNickname().getValue().equals(nickname.getValue()) &&
                a.getDate().getValue().equals(date.getValue()));

        attendances.add(new Attendance(nickname, date, time));
        // 실제 파일에 저장하는 로직은 없음
        Collections.sort(attendances, Comparator.comparing(a -> a.getDate().getValue()));
    }

    public List<Nickname> findAllNicknames() {
        return attendances.stream()
                .map(Attendance::getNickname)
                .map(Nickname::getValue)
                .distinct()
                .map(Nickname::new)
                .collect(Collectors.toList());
    }

    public List<Attendance> findAll() {
        return new ArrayList<>(attendances);
    }
}