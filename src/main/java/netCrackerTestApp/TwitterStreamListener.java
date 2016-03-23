package netCrackerTestApp;

import netCrackerTestApp.Dao.MongoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.*;
import java.io.Serializable;


public class TwitterStreamListener implements StreamListener, Serializable {

    private final Logger logger = LoggerFactory.getLogger(TwitterStreamListener.class);
    private final SentimentAnalysis sentimentAnalysis = new SentimentAnalysis();
    private final MongoDao mongoDao = new MongoDao();


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
