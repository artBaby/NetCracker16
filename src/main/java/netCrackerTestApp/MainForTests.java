package netCrackerTestApp;

import netCrackerTestApp.Dao.MongoDao;
import netCrackerTestApp.objects.SentimentTweet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainForTests {

    public static void main(String[] arg) throws IOException {
        MongoDao mongoDao = MongoDao.getInstance();
        List<SentimentTweet> tweetsFromDB = mongoDao.getTweetsFromDB("Japan");
        for (SentimentTweet sentimentTweet : tweetsFromDB) {
            System.out.println("sentimentTweet = " + sentimentTweet.getTextPost() + "    "+sentimentTweet.getSentimentResult());
        }
        SentimentAnalysis sentimentAnalysis = new SentimentAnalysis();
        Map<Integer, Long> sentimentResultOnTopic = sentimentAnalysis.getSentimentResultOnTopic(tweetsFromDB);
        sentimentResultOnTopic.forEach((integer, aLong) -> System.out.println("integer = " + integer+ "  count="+ aLong));

        List<SentimentTweet> textSomeTweetsOnTopic = sentimentAnalysis.getTweetsOnTopic(tweetsFromDB);
        for (SentimentTweet sentimentTweet : textSomeTweetsOnTopic) {
            System.out.println("sentimentTweet = " + sentimentTweet.getTextPost() +"  "+ sentimentTweet.getSentimentResult());
        }

    }
}
