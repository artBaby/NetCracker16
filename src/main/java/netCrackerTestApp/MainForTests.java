package netCrackerTestApp;

import netCrackerTestApp.Dao.MongoDao;
import java.io.IOException;



public class MainForTests {

    public static void main(String[] arg) throws IOException {
        MongoDao mongoDao = MongoDao.getInstance();
        mongoDao.getTweets("Renault Sport");

    }
}
