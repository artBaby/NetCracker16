package netCrackerTestApp;

import netCrackerTestApp.Dao.MongoDao;
import netCrackerTestApp.objects.SentimentTweet;
import org.joda.time.DateTime;

import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class MainForTests {

    public static void main(String[] arg) throws IOException {
        MongoDao mongoDao = MongoDao.getInstance();
        SentimentAnalysis sentimentAnalysis = new SentimentAnalysis();
        List<SentimentTweet> tweetsFromDB = mongoDao.getTweets("Advocacy","","2016-03-31");
        System.out.println("tweetsFromDB.size() = " + tweetsFromDB.size());

        List<SentimentTweet> textSomeTweetsOnTopic = sentimentAnalysis.getTweetsOnTopic(tweetsFromDB);
        for (SentimentTweet sentimentTweet : textSomeTweetsOnTopic) {
            System.out.println("sentimentTweet = " + sentimentTweet.getTextPost() +"  "+ sentimentTweet.getSentimentResult());
        }
//        Date date = new Date();
//        System.out.println("date.getTime() = " + date.getTime());
//
//        DateTime dateTime = new DateTime("2016-03-12").plusDays(1);
//        System.out.println("dateTime.getMillis()= "+dateTime.getMillis());
//        System.out.println("dateTime = " + dateTime.getYear() +"  "+ dateTime.getMonthOfYear()+"  " +dateTime.getDayOfMonth() +"  "+dateTime.getHourOfDay());
    }
}
