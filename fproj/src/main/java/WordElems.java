import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordElems extends Middleware implements ICompos {
    private final String regex = "\\w+";
    String txt;
    List<String> list;
    public WordElems(String txt) {
        this.txt = txt;
        list = new ArrayList<String>();
    }

    public WordElems() {
        list = new ArrayList<String>();
    }

    public void getInfo() {
        System.out.println("All Words in text:");
        list.forEach(System.out::println);
        System.out.println("\n");
    }

    public boolean parse(String txt) {
        try{
            Pattern p = Pattern.compile(regex);
            Matcher matcher = p.matcher(txt);

            while (matcher.find()) {
                list.add(txt.substring(matcher.start(),matcher.end()).trim());
            }
            System.out.println("Word elems");
        }catch (Exception ex){
            Main.logger.error(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
        }
        return parseNext(txt);
    }
}
