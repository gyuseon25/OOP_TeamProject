import java.util.ArrayList;

public class User {

    public User(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    String studentNumber;
    ArrayList<SportReservation> rList = new ArrayList<>();

    public boolean check(String[] kwds) {
        String date = kwds[2];
        String time = kwds[3];
        for (SportReservation sr : rList) {
            if (sr.date.equals(date) && sr.time.equals(time)) {
                System.out.println("동일 시간에 다른 곳을 예약 했으므로 예약할 수 없습니다!\n");
                return true;
            }
        }
        return false;
    }
}
