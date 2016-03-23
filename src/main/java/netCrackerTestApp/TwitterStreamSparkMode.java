package netCrackerTestApp;

import netCrackerTestApp.Dao.MongoDao;
import netCrackerTestApp.objects.Account;
import netCrackerTestApp.objects.StreamTask;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dasha on 23.03.16.
 */
public class TwitterStreamSparkMode {
    private final Logger logger = LoggerFactory.getLogger(TwitterStreamExtractor.class);
    private final MongoDao mongoDao = new MongoDao();

    final List<String> listTopics = Arrays.asList("sport"/*,"music","news","policy"*/);
    final List<Account> accounts = mongoDao.getAccounts();

    final JavaSparkContext sc = new JavaSparkContext(new SparkConf().setAppName("TwitterStreamExtractor").setMaster("local[4]"));

    private List<StreamTask> getStreamTasks() {
        List<StreamTask> streamTasks = new ArrayList<>();
        for (int i = 0; i < listTopics.size(); i++) {
            streamTasks.add(new StreamTask(listTopics.get(i), accounts.get(i)));
        }
        return streamTasks;
    }

    public void start() {

        JavaRDD<StreamTask> streamTasks = sc.parallelize(getStreamTasks());

        streamTasks.foreach(streamTask -> {

            TwitterTemplate twitter = new TwitterTemplate(streamTask.account.getConsumerKey(),
                    streamTask.account.getConsumerSecret(),
                    streamTask.account.getAccessToken(),
                    streamTask.account.getAccessTokenSecret()
            );

            twitter.streamingOperations().filter(streamTask.topic, Arrays.asList(new TwitterStreamListener()));
            Thread.sleep(100000000000l);
        });

    }

    public static void main(String[] args) {
        new TwitterStreamSparkMode().start();
    }
}
