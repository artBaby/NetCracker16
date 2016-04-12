package netCrackerTestApp;

import netCrackerTestApp.Dao.History;
import java.io.IOException;



public class MainForTests {

    public static void main(String[] arg) throws IOException {

        History history = new History();
        history.getSentimentResultByTopicAndDate("0:0:0:0:0:0:0:1","sd", 1460386968930L);
    }
}
