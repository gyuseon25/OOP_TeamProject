package mgr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Manager<T extends Manageable> {
    public ArrayList<T> tList = new ArrayList<>();

    Scanner openFile(String filename) {
        Scanner filein = null;
        try {
            filein = new Scanner(new File(filename));
        } catch (IOException e) {
            System.out.println("파일 입력 오류");
            System.exit(0);
        }
        return filein;
    }

    public void readAll(String filename, Factory<T> fac) {
        Scanner filein = openFile(filename);
        T t = null;
        while (filein.hasNext()) {
            t = fac.create();
            t.read(filein);
            tList.add(t);
        }
        filein.close();
    }

    public void printAll() {
        for (T t : tList)
            t.print();
        System.out.println();
    }

//    public void search(Scanner scan) {
//        String name = null;
//        String zone = null;
//        String time = null;
//        while (true) {
//            System.out.println("구장명 : ");
//            name = scan.next();
//            System.out.println("구역명 : ");
//            zone = scan.next();
//            System.out.println("시간대 : ");
//            time = scan.next();
//            String[] kwd = {name,zone,time};
//            for (T t : tList) {
//                if (t.matches(kwd))
//                    t.print();
//            }
//        }
//    }

    public T find(String[] kwd) {
        for (T t : tList) {
            if (t.matches(kwd))
                return t;
        }
        return null;
    }
}

