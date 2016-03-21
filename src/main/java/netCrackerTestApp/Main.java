package netCrackerTestApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] arg) throws IOException {

        logger.error("Message logged at ERROR level");
        logger.warn("Message logged at WARN level");
        logger.info("Message logged at INFO level");

        List<String> search_topics = new ArrayList<>();
        search_topics.add("Hello from java");
        search_topics.add("NetCracker");
        search_topics.add("Putin");
        search_topics.add("Hockey");
        search_topics.add("Tiesto");

        // Rate API limit is not processed yet
//        search_topics.add("Toyota");
//        search_topics.add("I'm");
//        search_topics.add("Cool");
//        search_topics.add("Monday");

        // The factory instance is re-useable and thread safe.
//        TwitterFactory factory = new TwitterFactory();
//        AccessToken accessToken = loadAccessToken(/*Integer.parseInt(args[0])*/);
//        TwitterStreamExtractor twitter = factory.getInstance();
//        twitter.setOAuthConsumer("7uiwQuVG8QLz3M5JQp3l4o1iI", "ED1AB9iGny8oLXgvOY6YmpZ6t12ZeboGJiNLPTvrfChzIjXChR");
//        twitter.setOAuthAccessToken(accessToken);

//      Just for fun
//      Status status = twitter.updateStatus("Hello from java!");
//      System.out.println("Successfully updated the status to [" + status.getText() + "].");

//        for (String req : search_topics) {
//            try {
//                System.out.println("_______________________\n" +
//                        "Searching for " + "\"" + req + "\"...");
//                Query query = new Query(req);
//                QueryResult result;
//                do {
//                    result = twitter.search(query);
//                    List<Status> tweets = result.getTweets();
//                    for (Status tweet : tweets) {
//                        System.out.println("@" + tweet.getUser().getScreenName() +
//                                " - " + tweet.getText());
//                    }
//                } while ((query = result.nextQuery()) != null);
//
//            } catch (TwitterException te) {
//                te.printStackTrace();
//                System.out.println("Failed to search tweets: " + te.getMessage());
//                System.exit(-1);
//            }
//        }
//        System.exit(0);
//    }
//
//    private static AccessToken loadAccessToken(/*int useId*/){
//        // load from a persistent store
//        String token = "610645225-BuwTUm8Xu8fKLr1DrlrvTlwfnSQ1l33VJMcdHtlg";
//        // load from a persistent store
//        String tokenSecret = "oKp2InIskje40bnMWR4dBY09J13AOviFnVHzoPXLB7o2V";
//        return new AccessToken(token, tokenSecret);
//    }
    }
}
