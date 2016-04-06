package netCrackerTestApp.Dao;


import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import netCrackerTestApp.objects.JsonSentimentResult;
import org.bson.Document;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class History {
    private final MongoDao mongoDao = MongoDao.getInstance();
    private final MongoCollection<Document> history = mongoDao.db.getCollection("history");


    public void saveHistoryOfRequest(String ipAddress, String topic, List<JsonSentimentResult> ListJsonSentimentResultWithTweets){
        System.out.println("saveHistoryOfRequestToDB");

        List<Document> listDocuments = new ArrayList<>();
        for (JsonSentimentResult jsonSentimentResultWithTweets : ListJsonSentimentResultWithTweets) {
            Document doc = new Document();
            doc.append("sentimentResult", jsonSentimentResultWithTweets.getSentimentResult())
            .append("numberOfTweets", jsonSentimentResultWithTweets.getNumberOfTweets())
            .append("tweets", jsonSentimentResultWithTweets.getTweets());
            listDocuments.add(doc);
        }

        Document document = new Document();
        document.append("ipAddress", ipAddress)
                        .append("topic", topic)
                        .append("createdAt", new Date())
                        .append("sentimentResultWithTweets", listDocuments);
        history.insertOne(document);
    }

    public ArrayList<String> getTopics (String ipAddress) {
        ArrayList<String> listTopics = new ArrayList<>();

        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("ipAddress",ipAddress);
        FindIterable<Document> documents = history.find(basicDBObject);
        for (Document document : documents) {
            String topic = (String) document.get("topic");
            listTopics.add(topic);
        }
        return listTopics;

    }

}

