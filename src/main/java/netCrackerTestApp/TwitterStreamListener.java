package netCrackerTestApp;

import netCrackerTestApp.Dao.MongoDao;
import org.slf4j.Logger;
import org.springframework.social.twitter.api.*;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import java.io.Serializable;
import java.util.List;


public class TwitterStreamListener implements StreamListener, Serializable {

    private final Logger logger;
    SentimentAnalysis sentimentAnalysis = new SentimentAnalysis();
    MongoDao mongoDao = new MongoDao();


    public TwitterStreamListener(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void onTweet(Tweet tweet) {
        if(tweet.getLanguageCode().equals("en")){
            logger.info("tweet = " + tweet);
            int sentimentTweet = sentimentAnalysis.getSentimentTweet(tweet.getText());

            mongoDao.saveTweet(tweet, sentimentTweet);
        }


    }

    @Override
    public void onDelete(StreamDeleteEvent streamDeleteEvent) {

    }

    @Override
    public void onLimit(int i) {

    }

    @Override
    public void onWarning(StreamWarningEvent streamWarningEvent) {

    }

}
