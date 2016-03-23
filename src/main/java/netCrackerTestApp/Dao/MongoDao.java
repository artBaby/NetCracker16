package netCrackerTestApp.Dao;


import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import netCrackerTestApp.objects.Account;
import netCrackerTestApp.objects.SentimentTweet;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.Tweet;

import java.util.ArrayList;
import java.util.List;


public class MongoDao {
    private final Logger logger = LoggerFactory.getLogger(MongoDao.class);
    private final MongoDatabase db = new MongoClient("localhost").getDatabase("db");
    private final MongoCollection<Document> accounts = db.getCollection("accounts");
    private final MongoCollection<Document> tweets = db.getCollection("tweets");

    public List<Account> getAccounts() {
        List<Account> resultAccounts = new ArrayList<>();

        for (Document document : accounts.find()) {
            System.out.println(document);
            String consumerKey = (String) document.get("consumerKey");
            String consumerSecret = (String) document.get("consumerSecret");
            String accessToken = (String) document.get("accessToken");
            String accessTokenSecret = (String) document.get("accessTokenSecret");
            resultAccounts.add(new Account(consumerKey, consumerSecret, accessToken, accessTokenSecret));
        }
        return resultAccounts;
    }

    public void saveTweet(Tweet tweet, int sentimentTweet) {
        Document document = new Document("_id", tweet.getId())
                .append("userName", tweet.getUser().getName())
                .append("textPost", tweet.getText())
                .append("isRetweet", tweet.isRetweet())
                .append("isRetweeted", tweet.isRetweeted())
                .append("retweetCount", tweet.getRetweetCount())
                .append("sentimentTweet", sentimentTweet)
                .append("views", 0);
        tweets.insertOne(document);
    }

    public List<SentimentTweet> getTweets(String str) {
        List<SentimentTweet> sentimentTweetList = new ArrayList<>();

        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("textPost", java.util.regex.Pattern.compile("(" + str + ")"));
        FindIterable<Document> documents = tweets.find(basicDBObject);

        for (Document document : documents) {
            String textPost = (String) document.get("textPost");
            int sentimentResult = (int) document.get("sentimentTweet");
            sentimentTweetList.add(new SentimentTweet(textPost, sentimentResult));
            logger.info("textPost= " + textPost);
            logger.info("sentimentResult= " + sentimentResult);
        }

        return sentimentTweetList;
    }


}
