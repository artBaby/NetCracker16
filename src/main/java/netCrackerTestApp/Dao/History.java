package netCrackerTestApp.Dao;


import com.mongodb.client.MongoCollection;
import netCrackerTestApp.objects.JsonSentimentResult;
import org.bson.Document;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class History {
    private final MongoDao mongoDao = MongoDao.getInstance();
    private final MongoCollection<Document> history = mongoDao.db.getCollection("history");


    public void saveHistoryOfRequestToDB(String ipAddress, String topic, List<JsonSentimentResult> ListJsonSentimentResultWithTweets){
        System.out.println("вошли в saveHistoryOfRequestToDB");

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

    public void getHistoryOfRequestFromDB(String ipAddress) {
    }
}

