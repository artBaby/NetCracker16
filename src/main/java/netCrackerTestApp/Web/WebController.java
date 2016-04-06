//package netCrackerTestApp.Web;
//
//import org.apache.log4j.Logger;
//import org.springframework.boot.context.web.SpringBootServletInitializer;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Controller
////@EnableAutoConfiguration
//public class WebController extends SpringBootServletInitializer {
//
////    @RequestMapping(value = "index")
//////    @ResponseBody
////    public String home() {
////        return "index";
////    }
//
//    static Logger logger = Logger.getLogger(WebController.class);
//
//    @RequestMapping("/lol")
//    public ModelAndView defaultAdminRequest(final HttpServletRequest request) {
//        logger.info("lol request");
//        return  new ModelAndView("jsp_test");
//    }
//}
package netCrackerTestApp.Web;
import netCrackerTestApp.Dao.History;
import netCrackerTestApp.Dao.MongoDao;
import netCrackerTestApp.SentimentAnalysis;
import netCrackerTestApp.objects.JsonHistory;
import netCrackerTestApp.objects.JsonSentimentResult;
import netCrackerTestApp.objects.SentimentTweet;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class WebController {
    private final MongoDao mongoDao = MongoDao.getInstance();
    private final SentimentAnalysis sentimentAnalysis = new SentimentAnalysis();
    String ipAddress = "";

    @RequestMapping("/")
    public String index(HttpServletRequest request) {
        ipAddress  = request.getRemoteAddr(); //get client Ip Address
        return "startPage";
    }



    @RequestMapping(value = "/ajaxRequest", method = RequestMethod.POST)
    public @ResponseBody String getData(@RequestParam("topic") String topic, @RequestParam("firstDate") String firstDate, @RequestParam("lastDate") String lastDate) throws Exception {
        System.out.println("this is from controller: " + topic + "  " +firstDate + "  " + lastDate);
        List<JsonSentimentResult> jsonSentimentResultWithTweets = new ArrayList<>();
        List<SentimentTweet> tweetsFromDB = mongoDao.getTweets(topic,firstDate,lastDate);
        Map<Integer, Long> sentimentResultOnTopic = sentimentAnalysis.getSentimentResultByTopic(tweetsFromDB);

        for (Integer sentimentResult : sentimentResultOnTopic.keySet()) {
            List<String> tweets = tweetsFromDB.stream().filter(sentimentTweet -> sentimentTweet.getSentimentResult() == sentimentResult).distinct().limit(2).map(sentimentTweet -> sentimentTweet.getTextPost()).collect(Collectors.toList());
            if(sentimentResult.equals(0)) jsonSentimentResultWithTweets.add(new JsonSentimentResult("Negative", sentimentResultOnTopic.get(sentimentResult), tweets));
            if(sentimentResult.equals(1)) jsonSentimentResultWithTweets.add(new JsonSentimentResult("Somewhat negative", sentimentResultOnTopic.get(sentimentResult), tweets));
            if(sentimentResult.equals(2)) jsonSentimentResultWithTweets.add(new JsonSentimentResult("Neutral", sentimentResultOnTopic.get(sentimentResult), tweets));
            if(sentimentResult.equals(3)) jsonSentimentResultWithTweets.add(new JsonSentimentResult("Somewhat positive", sentimentResultOnTopic.get(sentimentResult), tweets));
            if(sentimentResult.equals(4)) jsonSentimentResultWithTweets.add(new JsonSentimentResult("Positive", sentimentResultOnTopic.get(sentimentResult), tweets));
        }


        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(jsonSentimentResultWithTweets);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        History history = new History();
        history.saveHistoryOfRequestToDB(ipAddress, topic, jsonSentimentResultWithTweets);

        return json;

    }
}
