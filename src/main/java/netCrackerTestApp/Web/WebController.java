
package netCrackerTestApp.Web;
import netCrackerTestApp.Dao.History;
import netCrackerTestApp.Dao.MongoDao;
import netCrackerTestApp.SentimentAnalysis;
import netCrackerTestApp.objects.JsonSentimentResult;
import netCrackerTestApp.objects.SentimentTweet;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class WebController {
    private final MongoDao mongoDao = MongoDao.getInstance();
    private final SentimentAnalysis sentimentAnalysis = new SentimentAnalysis();
    private History history = new History();

    @RequestMapping("/")
    public String index(HttpServletRequest request, Model model) {
        String ipAddress  = request.getRemoteAddr(); //get client Ip Address
        ArrayList<String> topics = history.getTopics(ipAddress);

        ObjectMapper mapper = new ObjectMapper();
        String jsonIpAddress = "";
        try {
            jsonIpAddress = mapper.writeValueAsString(ipAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }

        model.addAttribute("jsonIpAddress", jsonIpAddress);
        model.addAttribute("topics", topics);
        return "startPage";
    }



    @RequestMapping(value = "/ajaxRequest", method = RequestMethod.POST)
    public @ResponseBody String getData(@RequestParam("topic") String topic,
                                        @RequestParam("firstDate") String firstDate,
                                        @RequestParam("lastDate") String lastDate,
                                        @RequestParam("ipAddress") String ipAddress) throws Exception {

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
        history.saveHistoryOfRequest(ipAddress, topic, jsonSentimentResultWithTweets);  //save history of the request to MongoDB collection 'history'

        return json;

    }
}
