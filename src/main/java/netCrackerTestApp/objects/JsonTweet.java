package netCrackerTestApp.objects;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonTweet {
    private String sentimentResult;
    private Long countTweets;
    private String TextTweet;

    @JsonCreator
    public JsonTweet(@JsonProperty("sentimentResult") String sentimentResult, @JsonProperty("countTweets") Long countTweets, @JsonProperty("textTweet") String textTweet) {
        this.sentimentResult = sentimentResult;
        this.countTweets = countTweets;
        TextTweet = textTweet;
    }

    public String getSentimentResult() {
        return sentimentResult;
    }

    public void setSentimentResult(String sentimentResult) {
        this.sentimentResult = sentimentResult;
    }

    public Long getCountTweets() {
        return countTweets;
    }

    public void setCountTweets(Long countTweets) {
        this.countTweets = countTweets;
    }

    public String getTextTweet() {
        return TextTweet;
    }

    public void setTextTweet(String textTweet) {
        TextTweet = textTweet;
    }
}
