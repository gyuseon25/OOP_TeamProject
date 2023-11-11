public class SportReservation {
    String studentNumber;
    String type;
    String code;
    String name;
    int capacity;
    String zone;
    String date;
    String day;
    String time;

    public SportReservation(SportFacility s) {
        this.type = s.type;
        this.code = s.code;
        this.name = s.name;
        this.capacity = s.capacity;
    }

    public void printr() {
        System.out.printf("구장이름:%s  구역이름:%s  날짜:%s  요일:%s  시간대:%s  ", name, zone, date, day, time);
    }

    public void print() {
        System.out.printf("구장이름:%s  구역이름:%s  날짜:%s  요일:%s  시간대:%s  ", name, zone, date, day, time);
        if (studentNumber == null) {
            System.out.println("예약여부:가능");
        } else {
            System.out.println("예약여부:불가능");
        }
    }

    public boolean matches(String[] kwd) {
        for (String s : kwd) {
            if (s.equals(name))
                continue;
            else if (s.equals(zone))
                continue;
            else if (s.equals(date))
                continue;
            else if (s.equals(time))
                continue;
            else if (!s.equals(name))
                return false;
            else if (!s.equals(zone))
                return false;
            else if (!s.equals(date))
                return false;
            else
                return false;
        }
        return true;
    }

    public SportReservation find(String[] kwds) {
        if (matches(kwds))
            return this;
        else
            return null;
    }


}

