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

        History history = new History();
        history.getSentimentResultByTopicAndDate("0:0:0:0:0:0:0:1","sd", 1460386968930L);
    }
}
