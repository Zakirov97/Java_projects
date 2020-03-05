import org.apache.logging.log4j.core.appender.rolling.action.IfAll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrgphElems extends Middleware implements ICompos {
    private final String regex = "\\t.+";
    String txt;
    List<String> list;

    public PrgphElems(String txt) {
        this.txt = txt;
        list = new ArrayList<String>();
    }

    public PrgphElems() {
        list = new ArrayList<String>();
    }

    public void getInfo() {
        System.out.println("All Paragraphs in text:");
        list.forEach(System.out::println);
        System.out.println("\n");
    }

    public boolean parse(String txt) {
        boolean b = false;
        try {
            Pattern p = Pattern.compile(regex);
            Matcher matcher = p.matcher(txt);

            while (matcher.find()) {
                list.add(txt.substring(matcher.start(), matcher.end()).trim());
            }
            b = true;
            System.out.println("Paragraph elems");
        } catch (Exception ex) {
            Main.logger.error(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
        }
        return parseNext(txt);
    }

    public List<String> sortByCntSenteces() {
        List<String> tempList = list;
//        Для проверки
//        String s = tempList.get(0);
//        tempList.remove(s);
//        tempList.add(s);
        tempList.sort((x, y) -> {
            Pattern p1 = Pattern.compile("[^\\.\\!\\?]*[\\.\\!\\?]");
            Matcher matcher1 = p1.matcher(x);
            int cnt1 = 0;
            while (matcher1.find()) {
                cnt1++;
            }

            Pattern p2 = Pattern.compile("[^\\.\\!\\?]*[\\.\\!\\?]");
            Matcher matcher2 = p2.matcher(y);
            int cnt2 = 0;
            while (matcher2.find()) {
                cnt2++;
            }
            return Integer.compare(cnt2, cnt1);
        });
        return tempList;
    }

    public List<String> sortByCountWordsInSenteces() {
        List<String> tempList = new ArrayList<>();
        list.forEach((f) -> {
            List<String> sentences = new ArrayList();
            Pattern p1 = Pattern.compile("[^\\.\\!\\?]*[\\.\\!\\?]");
            Matcher matcher1 = p1.matcher(f);
            ;
            while (matcher1.find()) {
                sentences.add(f.substring(matcher1.start(), matcher1.end()));
            }
            sentences.sort((x, y) -> {
                List<String> wordsX = new ArrayList<String>();
                Pattern pWords = Pattern.compile("\\w+");
                Matcher matcherWords = pWords.matcher(x);
                int cnt1 = 0;
                while (matcherWords.find()) {
                    cnt1++;
                }

                List<String> wordsY = new ArrayList<String>();
                Pattern pWords2 = Pattern.compile("\\w+");
                Matcher matcherWords2 = pWords2.matcher(y);
                int cnt2 = 0;
                while (matcherWords2.find()) {
                    cnt2++;
                }
                return Integer.compare(cnt2, cnt1);
            });
            StringBuffer sb = new StringBuffer();
            sentences.forEach((f2) -> sb.append(f2));
            tempList.add(sb.toString());
        });
        return tempList;
    }
}