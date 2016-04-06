package netCrackerTestApp.Dao;


import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import netCrackerTestApp.objects.JsonSentimentResult;
import org.bson.Document;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static java.util.Arrays.asList;

public class History {
    private final MongoDao mongoDao = MongoDao.getInstance();
    private final MongoCollection<Document> history = mongoDao.db.getCollection("history");


    public void saveHistoryOfRequestToDB(String ipAddress, String topic, List<JsonSentimentResult> LiistJsonSentimentResultWithTweets){
        System.out.println("вошли в saveHistoryOfRequestToDB");

        List<Document> listDocuments = new ArrayList<>();
        for (JsonSentimentResult jsonSentimentResultWithTweets : LiistJsonSentimentResultWithTweets) {
            Document doc = new Document();
            doc.append("sentimentResult", jsonSentimentResultWithTweets.getSentimentResult())
            .append("numberOfTweets", jsonSentimentResultWithTweets.getNumberOfTweets())
            .append("tweets", jsonSentimentResultWithTweets.getTweets());
            listDocuments.add(doc);
        }
        UpdateOptions options = new UpdateOptions().upsert(true); //creates a new document if no document matches the filterQuery.

        BasicDBObject filterQuery = new BasicDBObject();
        filterQuery.append("_ipAddress", ipAddress);
        BasicDBObject query = new BasicDBObject();
        query.put("history", asList(
                new Document()
                        .append("topic", topic)
                        .append("createdAt", new Date().getTime())
                        .append("sentimentResultWithTweets", listDocuments)
        ));
        history.updateOne(eq("_ipAddress", ipAddress), new BasicDBObject("$push", query), options);
    }

    public void getHistoryOfRequestFromDB(String ipAddress) {
    }
}

