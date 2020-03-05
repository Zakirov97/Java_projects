import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.*;

import static org.apache.logging.log4j.LogManager.getLogger;

public class Main {

    final static File file = new File("C:\\text.txt");
    public static final Logger logger = getLogger(Main.class);
    static List<ICompos> allElems = new ArrayList<ICompos>();

    public static void main(String[] args){

        String txt = "";
        try {
            Scanner sc = new Scanner(file);
            txt = sc.useDelimiter("\\Z").next();
            System.out.println(txt);
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
            logger.error(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
        }

        //Compositor
        CharELems charELems = new CharELems(txt);
        WordElems wordElems = new WordElems(txt);
        SentElems sentElems = new SentElems(txt);
        PrgphElems prgphElems = new PrgphElems(txt);
        allElems.add(charELems);
        allElems.add(wordElems);
        allElems.add(sentElems);
        allElems.add(prgphElems);

        //Chain Of Responsibility
        Middleware middleware = prgphElems;
        middleware.linkWith(sentElems).linkWith(wordElems).linkWith(charELems);
        middleware.parse(txt);

        allElems.forEach((f) -> f.getInfo());

        //Sort 1
        List<String> sort1 = prgphElems.sortByCntSenteces();
        System.out.println(sort1);

        //Sort 2
        List<String> sort2 = sentElems.sortByWordLengthInSentences();
        System.out.println(sort2);

        //Sort 3
        List<String> sort3 = prgphElems.sortByCountWordsInSenteces();
        System.out.println(sort2);

    }

}

