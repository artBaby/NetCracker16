package netCrackerTestApp;


import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import netCrackerTestApp.objects.SentimentTweet;

import java.util.*;
import java.util.stream.Collectors;

public class SentimentAnalysis {
    StanfordCoreNLP pipeline;

    public SentimentAnalysis() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
        pipeline = new StanfordCoreNLP(props);
    }

    public int getSentimentTweet(String textPost) {
        int assessmentSentiment = -10;
        Annotation annotation = pipeline.process(textPost);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            assessmentSentiment = RNNCoreAnnotations.getPredictedClass(tree);
        }
        return assessmentSentiment;
    }

    public Map<Integer, Long> getSentimentResultOnTopic (List<SentimentTweet> sentimentTweetList){
        Map<Integer, Long> collect = sentimentTweetList.stream()
                .collect(Collectors.groupingBy(SentimentTweet::getSentimentResult, Collectors.counting()));
        sentimentTweetList.stream().collect(Collectors.groupingBy(SentimentTweet::getSentimentResult));
        return collect;
    }

    public Map<Integer, String>  getTweetsOnTopic (List<SentimentTweet> sentimentTweetList){
        Map<Integer, String> listTweets = new HashMap<>();

        Map<Integer, List<SentimentTweet>> collect = sentimentTweetList.stream().collect(Collectors.groupingBy(SentimentTweet::getSentimentResult));
        collect.forEach((sentimentResult, sentimentTweets) -> listTweets.put(sentimentResult,sentimentTweets.get(0).getTextPost()));
        return listTweets;
    }
}
