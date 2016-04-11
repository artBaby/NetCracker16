package netCrackerTestApp;

import netCrackerTestApp.Dao.History;
import netCrackerTestApp.Dao.MongoDao;
import netCrackerTestApp.objects.SentimentTweet;
import org.joda.time.DateTime;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;


public class MainForTests {

    public static void main(String[] arg) throws IOException {
//        History history = new History();
//        HashMap<Date, String> topicsAndDate = history.getTopicsAndDate("0:0:0:0:0:0:0:1");
//        for (Date date : topicsAndDate.keySet()) {
//            System.out.println(topicsAndDate.get(date));
//        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss.SSS");
        Date date1 = new Date();
        System.out.println("date1 = " + date1);
        System.out.println("date1Mil = " + date1.getTime());
        String format = simpleDateFormat.format(date1.getTime());
        System.out.println("format= " + format);

        String expectedPattern = "yyyy.MM.dd 'at' HH:mm:ss.SSS";
        SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
        try {
            Date date2 = formatter.parse(format);
            System.out.println("date2 = " + date2);
            System.out.println("date2Mil = " + date2.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
