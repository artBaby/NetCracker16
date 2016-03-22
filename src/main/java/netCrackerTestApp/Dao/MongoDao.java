package netCrackerTestApp.Dao;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import netCrackerTestApp.SentimentAnalysis;
import netCrackerTestApp.objects.Account;
import org.bson.Document;
import org.springframework.social.twitter.api.Tweet;

import java.util.ArrayList;
import java.util.List;


public class MongoDao {
    private static final MongoDatabase db = new MongoClient("localhost").getDatabase("db");
    private static final MongoCollection<Document> accounts = db.getCollection("accounts");
    private static final MongoCollection<Document> tweets = db.getCollection("tweets");

    public static List<Account> getAccounts() {
        List<Account> resultAccounts = new ArrayList<>();

        for (Document document : accounts.find()) {
            System.out.println(document);
            String consumerKey = (String) document.get("consumerKey");
            String consumerSecret = (String) document.get("consumerSecret");
            String accessToken = (String) document.get("accessToken");
            String accessTokenSecret = (String) document.get("accessTokenSecret");
            resultAccounts.add(new Account(consumerKey,consumerSecret,accessToken,accessTokenSecret));
        }
        return resultAccounts;
    }

    public  void saveTweet(Tweet tweet, int sentimentTweet) {
        Document document = new Document("_id", tweet.getId())
                .append("userId", tweet.getFromUserId())
                .append("textPost", tweet.getText())
                .append("isRetweet",tweet.isRetweet())
                .append("isRetweeted", tweet.isRetweeted())
                .append("retweetCount", tweet.getRetweetCount())
                .append("sentimentTweet", sentimentTweet)
                .append("views", 0);
        tweets.insertOne(document);
    }


}
