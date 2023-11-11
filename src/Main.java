import mgr.Manager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    ArrayList<User> userList = new ArrayList<>();
    static ArrayList<String> timeList = new ArrayList<>(Arrays.asList("14-16", "16-18", "18-20", "20-22"));
    ArrayList<SportReservation> sportReservations = new ArrayList<>();
    //ArrayList<StudyReservation> studyReservations = new ArrayList<>();
    static Scanner scan = new Scanner(System.in);
    //FacilityManager<StudyFacility> studyFacilityManager = new Manager<>();
    Manager<SportFacility> sportFacilityManager = new Manager<>();
    ArrayList<String> weekDates = new ArrayList<>();
    ArrayList<String> weekDays = new ArrayList<>();
    // 출력 형식 지정 (예: "2023-11-11")
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    void run() {
        getWeekDateAndDay();
        //studyFacilityManager.readAll("studyfacilities.txt,() -> new StudyFacility();
        sportFacilityManager.readAll("sportfacilities.txt", () -> new SportFacility());
        //makeStudyReservation();
        makeSportReservation();
        menu();
    }

    void getWeekDateAndDay() {
        LocalDate currentDate = LocalDate.now();
        for (int i = 1; i <= 7; i++) {
            LocalDate date = currentDate.plusDays(i);
            String dateOfWeek = date.format(formatter);
            weekDates.add(dateOfWeek);
            // 요일을 문자열로 변환
            String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            weekDays.add(dayOfWeek);
        }

    }

    void makeSportReservation() {
        SportReservation sr = null;
        for (SportFacility s : sportFacilityManager.tList) {
            for (int i = 0; i < 7; i++) {
                for (String zn : s.zoneName) {
                    for (String time : this.timeList) {
                        sr = new SportReservation(s);
                        sr.zone = zn;
                        sr.time = time;
                        sr.date = weekDates.get(i);
                        sr.day = weekDays.get(i);
                        sportReservations.add(sr);
                    }
                }
            }
        }
    }

    void searchSportReservation() {
        while (true) {
            System.out.print("구장이름 : (0 입력시 종료) ");
            String name = scan.next();
            if (name.equals("0"))
                return;
            System.out.print("구역이름 : ");
            String zone = scan.next();
            System.out.print("날짜 : (1주일 내 yyyy-MM-dd형식으로 입력) ");
            String date = scan.next();
            System.out.print("시간대 : ");
            String time = scan.next();
            String[] kwd = {name, zone, date, time};
            for (SportReservation s : sportReservations) {
                if (s.matches(kwd))
                    s.print();
            }
            System.out.println();
        }
    }

    void search() {
        System.out.println("(1)스터디시설 검색  (2)체육시설 검색  (기타)종료");
        int num = scan.nextInt();
        switch (num) {
            case 1:
                //searchStudyReservation();
                break;
            case 2:
                searchSportReservation();
                break;
            default:
                break;
        }
    }

    void sportReservation() {
        System.out.print("학번을 입력하세요: ");
        String studentNumber = scan.next();
        System.out.print("구장이름을 입력하세요: ");
        String name = scan.next();
        System.out.print("구역이름을 입력하세요: ");
        String zone = scan.next();
        System.out.print("날짜를 입력하세요: (1주일 내 yyyy-MM-dd형식으로 입력/당일예약 불가) ");
        String date = scan.next();
        System.out.print("시간대를 입력하세요: ");
        String time = scan.next();
        String[] kwds = {name, zone, date, time};
        SportReservation sr;
        User user = findUser(studentNumber);
        if (user == null) {
            System.out.println("존재하지 않는 유저입니다. 유저 정보를 먼저 생성하세요 !");
            return;
        }
        if (!weekDates.contains(date)) {
            System.out.println("예약할 수 없는 날짜입니다!");
            return;
        }
        if (checkDuplicate(user, kwds))
            return;
        for (SportReservation s : sportReservations) {
            if (s.matches(kwds)) {
                sr = s.find(kwds);
                sr.studentNumber = studentNumber;
                System.out.println("예약이 완료 됐습니다.\n");
                user.rList.add(sr);
                break;
            }
        }

    }

    boolean checkDuplicate(User user, String[] kwds) {
        return user.check(kwds);
    }

    void createUser() {
        System.out.print("생성하고자하는 유저의 학번을 입력하세요: ");
        String studentNumber = scan.next();
        if (findUser(studentNumber) == null) {
            User u = new User(studentNumber);
            userList.add(u);
            System.out.println("유저 생성 완료!");
        } else
            System.out.println("이미 존재하는 계정입니다!");
    }

    User findUser(String studentNumber) {
        for (User u : userList) {
            if (u.studentNumber.equals(studentNumber))
                return u;
        }
        return null;
    }

    void searchUser() {
        System.out.print("학번을 입력하세요: ");
        String studentNumber = scan.next();
        User user = findUser(studentNumber);
        if (user == null) {
            System.out.println("존재하지 않는 유저입니다. 유저 정보를 먼저 생성하세요 !");
            return;
        }
        System.out.printf("%s 유저의 예약 내용\n", studentNumber);
        for (SportReservation sr : user.rList) {
            sr.printr();
            System.out.println();
        }
        System.out.println();
    }

    void reservation() {
        System.out.println("(1)스터디시설 예약  (2)체육시설 예약  (기타)종료");
        int num = scan.nextInt();
        switch (num) {
            case 1:
                //studyReservation();
                break;
            case 2:
                sportReservation();
                break;
            default:
                break;
        }
    }


    void menu() {
        int num;
        while (true) {
            System.out.println("(1)스터디시설 출력  (2)체육시설 출력  (3)검색  (4)예약  (5)유저생성  (6)유저검색  (기타)종료 ");
            num = scan.nextInt();
            if (num < 1 || num > 7)
                break;
            switch (num) {
                case 1:
                    //studyFacilityManager.printAll();
                    break;
                case 2:
                    sportFacilityManager.printAll();
                    break;
                case 3:
                    search();
                    break;
                case 4:
                    reservation();
                    break;
                case 5:
                    createUser();
                    break;
                case 6:
                    searchUser();
                    break;
                default:
                    break;
            }
        }
    }

    public static void main(String[] args) {
        Main myMain = new Main();
        myMain.run();
    }
}