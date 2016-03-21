package netCrackerTestApp;

import netCrackerTestApp.Dao.MongoDao;
import org.slf4j.Logger;
import org.springframework.social.twitter.api.*;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import java.io.Serializable;
import java.util.List;


public class TwitterStreamListener implements StreamListener, Serializable {

    private final Logger logger;


    public TwitterStreamListener(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void onTweet(Tweet tweet) {
        logger.info("tweet = " + tweet);
        MongoDao.saveTweet(tweet);

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
