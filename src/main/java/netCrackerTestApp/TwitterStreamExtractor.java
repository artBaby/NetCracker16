package netCrackerTestApp;

import ch.qos.logback.core.status.Status;
import netCrackerTestApp.Dao.MongoDao;
import netCrackerTestApp.objects.Account;
import org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TwitterStreamExtractor {
    final Logger logger = LoggerFactory.getLogger(TwitterStreamExtractor.class);

    final JavaSparkContext sc = new JavaSparkContext(new SparkConf().setAppName("TwitterStreamExtractor").setMaster("local[4]"));
    final List<Account> accounts = MongoDao.getAccounts();

    String[] topics = {"sport"/*,"music","news","policy"*/};
    List<String> listTopics = Arrays.asList(topics);

    private List<StreamTask> getStreamTasks(){
        List<StreamTask> streamTasks= new ArrayList<>();
        for(int i=0; i<listTopics.size(); i++){
            streamTasks.add(new StreamTask(listTopics.get(i),accounts.get(i)));
        }
        return streamTasks;
    }

    public void start(){

        JavaRDD<StreamTask> streamTasks = sc.parallelize(getStreamTasks());

        streamTasks.foreach(streamTask -> {

            TwitterTemplate twitter = new TwitterTemplate(streamTask.account.getConsumerKey(),
                                                                streamTask.account.getConsumerSecret(),
                                                                streamTask.account.getAccessToken(),
                                                                streamTask.account.getAccessTokenSecret()
                                                                );

            TwitterStreamListener twitterStreamListener = new TwitterStreamListener(LoggerFactory.getLogger(TwitterStreamExtractor.class));
            twitter.streamingOperations().filter(streamTask.topic, Arrays.asList(twitterStreamListener));
            Thread.sleep(100000000000l);
        });

    }

    public static void main(String[] args) {
        new TwitterStreamExtractor().start();
    }
}
