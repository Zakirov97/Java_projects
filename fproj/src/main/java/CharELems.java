import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//public class CharELems implements ICompos {
//    String txt;
//
//    public CharELems(String txt){
//        this.txt = txt;
//        getInfo(this.txt);
//    }
//
//    public CharELems() {
//    }
//
//    public List<String> getInfo(String txt) {
//        List<String> list = new ArrayList<String>();
//        for (int i = 0; i < txt.length(); i++) {
//            list.add(String.valueOf(txt.charAt(i)));
//        }
//        return list;
//     }
//}
public class CharELems extends Middleware implements ICompos {
    String txt;
    List<String> list;

    public CharELems(String txt) {
        this.txt = txt;
        list = new ArrayList<String>();
    }

    public CharELems() {
        list = new ArrayList<String>();
    }

    public void getInfo() {
        System.out.println("All Chars in text:");
        list.forEach(System.out::println);
        System.out.println("\n");
    }

    public boolean parse(String txt) {
        try {
            for (int i = 0; i < txt.length(); i++) {
                list.add(String.valueOf(txt.charAt(i)));
            }
            System.out.println("Char elems");
        } catch (Exception ex) {
            Main.logger.error(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
        }
        return parseNext(txt);
    }

}
