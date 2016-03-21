//package netCrackerTestApp;
//
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaPairRDD;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.api.java.function.FilterFunction;
//import org.apache.spark.api.java.function.FlatMapFunction;
//import org.apache.spark.api.java.function.Function2;
//import org.apache.spark.api.java.function.PairFunction;
//import org.springframework.social.RateLimitExceededException;
//import org.springframework.social.twitter.api.Tweet;
//import org.springframework.social.twitter.api.TwitterProfile;
//import org.springframework.social.twitter.api.impl.TwitterTemplate;
//import scala.Tuple2;
//
//import java.io.File;
//
//import java.io.Serializable;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collector;
//import java.util.stream.Collectors;
//
///**
// * Created by Artemiy on 21.03.2016.
// */
//public class TestTwitter {
//
//
//    final static SparkConf conf = new SparkConf().setAppName("Test");
//    final static JavaSparkContext sc = new JavaSparkContext(conf);
//
//    public static void main(String[] args) {
//
//        TwitterAccount twitterAccount = new TwitterAccount("FE3oc3mDHf945MpCn77QC6TOO","L7H9FsWqY8ATMJPoRrjbTUbTVr49uaXMSklvhd3C8Q6cW4jFgo","697749814147207171-uxaBONI0t95pJAhza6XOgyjHFNVpIg3","SNsfIVo5pMXdF3sS1UQ4gtTLQrjEmuYEAeHmrLkEOdUNY");
//        final TwitterTemplate twitter = new TwitterTemplate(twitterAccount.getConsumer_Key(),twitterAccount.getConsumer_Secret(),twitterAccount.getAccessToken(),twitterAccount.getAccessTokenSecret());
//
//        List<Long> userIds = twitter.searchOperations().search("nba").getTweets().stream().map(Tweet::getFromUserId).collect(Collectors.toList());
//
//        JavaRDD<Long> userIdsRdd = sc.parallelize(userIds);
//
//        userIdsRdd.foreach(userId -> {
//            final TwitterTemplate localTwitter = new TwitterTemplate("FE3oc3mDHf945MpCn77QC6TOO","L7H9FsWqY8ATMJPoRrjbTUbTVr49uaXMSklvhd3C8Q6cW4jFgo","697749814147207171-uxaBONI0t95pJAhza6XOgyjHFNVpIg3","SNsfIVo5pMXdF3sS1UQ4gtTLQrjEmuYEAeHmrLkEOdUNY");
//
//            try {
//                TwitterProfile userProfile = localTwitter.userOperations().getUserProfile(userId);
//                saveToDb(userProfile);
//            }
//            catch (RateLimitExceededException e) {
//                System.out.println("The rate limit has been exceeded");
//            }
//
//        });
//
//    }
//
//    private static void saveToDb(TwitterProfile userProfile) {
//        System.out.println("userProfile = " + userProfile.getProfileUrl());
//    }
//}
