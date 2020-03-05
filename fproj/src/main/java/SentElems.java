import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentElems extends Middleware implements ICompos {
    private final String regex = "[^\\.\\!\\?]*[\\.\\!\\?]";
    String txt;
    List<String> list;

    public SentElems(String txt) {
        this.txt = txt;
        list = new ArrayList<String>();
    }

    public SentElems() {
        list = new ArrayList<String>();
    }

    public void getInfo() {
        System.out.println("All Sentences in text:");
        list.forEach(System.out::println);
        System.out.println("\n");
    }

    public boolean parse(String txt) {
        try {
            Pattern p = Pattern.compile(regex);
            Matcher matcher = p.matcher(txt);

            while (matcher.find()) {
                list.add(txt.substring(matcher.start(), matcher.end()).trim());
            }
            System.out.println("Sent elems");
        } catch (Exception ex) {
            Main.logger.error(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
        }
        return parseNext(txt);
    }


    public List<String> sortByWordLengthInSentences() {
        List<String> tempList = new ArrayList<String>();
        list.forEach((f) -> {
            List<String> words = new ArrayList<String>();
            Pattern p1 = Pattern.compile("\\w+");
            Matcher matcher1 = p1.matcher(f);
            while (matcher1.find()) {
                words.add(f.substring(matcher1.start(),matcher1.end()));
            }
            //Тут я подумал насчёт вида отсортированного предложения. Пришел к выводу что без разницы в каком он виде выглядит.
            words.sort(Comparator.comparingInt(String::length).reversed());
            tempList.add(String.valueOf(words));
        });

        return tempList;
    }
}