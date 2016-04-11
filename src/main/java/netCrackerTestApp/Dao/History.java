package netCrackerTestApp.Dao;


import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import netCrackerTestApp.objects.JsonSentimentResult;
import org.bson.Document;
import java.util.*;

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

    public HashMap<Date, String> getTopicsAndDate(String ipAddress) {
        HashMap<Date, String> listTopicsWithDate = new LinkedHashMap<>();
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("ipAddress",ipAddress);
        FindIterable<Document> documents = history.find(basicDBObject).sort(new BasicDBObject("createdAt",1));
        for (Document document : documents) {
            String topic = (String) document.get("topic");
            Date createdAt = (Date) document.get("createdAt");
            System.out.println("createdAt = " +  createdAt.toString());
            listTopicsWithDate.put(createdAt,topic);
        }
        return listTopicsWithDate;

    }

}

