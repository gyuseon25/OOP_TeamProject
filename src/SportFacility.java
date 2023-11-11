import mgr.Manageable;

import java.util.ArrayList;
import java.util.Scanner;

public class SportFacility implements Manageable {
    String type;
    String code;
    String name;
    int capacity;
    ArrayList<String> zoneName = new ArrayList<>();

    @Override
    public void read(Scanner scan) {
        int num = scan.nextInt();
        switch (num) {
            case 1:
                type = "풋살장";
                break;
            case 2:
                type = "축구장";
                break;
            case 3:
                type = "농구장";
                break;
            case 4:
                type = "족구장";
                break;
            case 5:
                type = "테니스장";
                break;
            case 6:
                type = "탁구장";
                break;
        }
        code = scan.next();
        name = scan.next();
        capacity = scan.nextInt();
        String zone = scan.next();
        while (!zone.equals("0")) {
            zoneName.add(zone);
            zone = scan.next();
        }
    }

    @Override
    public void print() {
        System.out.printf("구장구분 : %s  구장코드 : %s  구장이름 : %s  수용인원수 : %d  구역이름 :  ", type, code, name, capacity);
        for (String zone : zoneName) {
            System.out.printf("%s ", zone);
        }
        System.out.println();
    }

    @Override
    public boolean matches(String[] kwds) {
        for (String kwd : kwds) {
            if (!kwd.matches(name))
                return false;
            for (String s : zoneName) {
                if (!kwd.matches(s))
                    return false;
            }
        }
        return true;
    }

}
