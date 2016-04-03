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
import netCrackerTestApp.Dao.MongoDao;
import netCrackerTestApp.SentimentAnalysis;
import netCrackerTestApp.objects.JsonTweet;
import netCrackerTestApp.objects.SentimentTweet;
import org.apache.taglibs.standard.tag.common.fmt.BundleSupport;
import org.springframework.http.MediaType;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class WebController {
    private final MongoDao mongoDao = MongoDao.getInstance();
    private final SentimentAnalysis sentimentAnalysis = new SentimentAnalysis();

    @RequestMapping("/")
    public String index() {
        return "startPage";
    }

    @RequestMapping(value = "/getTopic", method = RequestMethod.POST)
    public String getTopic(@RequestParam("topic") String topic, HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException {
        System.out.println("topic = " + topic);
        model.addAttribute("topic",topic);
        return "hello";
    }

    @RequestMapping(value = "/ajaxTest", method = RequestMethod.POST)
    public @ResponseBody String getCharNum(@RequestParam("topic") String topic) throws Exception {
        System.out.println("this is from controller: " + topic);
        return topic;
    }

    @RequestMapping(value = "/ajaxRequest", method = RequestMethod.POST)
    public @ResponseBody String getData(@RequestParam("topic") String topic, @RequestParam("firstDate") String firstDate, @RequestParam("lastDate") String lastDate) throws Exception {
        System.out.println("this is from controller: " + topic + "  " +firstDate + "  " + lastDate);
        List<JsonTweet> jsonTweets = new ArrayList<>();
        List<SentimentTweet> tweetsFromDB = mongoDao.getTweets(topic,firstDate,lastDate);
        Map<Integer, Long> sentimentResultOnTopic = sentimentAnalysis.getSentimentResultOnTopic(tweetsFromDB);
        Map<Integer, String> tweetsOnTopic = sentimentAnalysis.getTweetsOnTopic(tweetsFromDB);

        for (Integer keySentiment : sentimentResultOnTopic.keySet()) {
            for (Integer keyTweet : tweetsOnTopic.keySet()) {
                if(keySentiment.equals(keyTweet)){
                    if(keyTweet.equals(0)) jsonTweets.add(new JsonTweet("Negative", sentimentResultOnTopic.get(keySentiment), tweetsOnTopic.get(keyTweet)));
                    if(keyTweet.equals(1)) jsonTweets.add(new JsonTweet("Somewhat negative", sentimentResultOnTopic.get(keySentiment), tweetsOnTopic.get(keyTweet)));
                    if(keyTweet.equals(2)) jsonTweets.add(new JsonTweet("Neutral", sentimentResultOnTopic.get(keySentiment), tweetsOnTopic.get(keyTweet)));
                    if(keyTweet.equals(3)) jsonTweets.add(new JsonTweet("Somewhat positive", sentimentResultOnTopic.get(keySentiment), tweetsOnTopic.get(keyTweet)));
                    if(keyTweet.equals(4)) jsonTweets.add(new JsonTweet("Positive", sentimentResultOnTopic.get(keySentiment), tweetsOnTopic.get(keyTweet)));
                }
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(jsonTweets);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("jsonTweets: " + jsonTweets);
        return json;

    }
}
