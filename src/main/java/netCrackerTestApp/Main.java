package netCrackerTestApp;

import netCrackerTestApp.Dao.MongoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;



public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final MongoDao mongoDao = new MongoDao();

    public static void main(String[] arg) throws IOException {
        mongoDao.getTweets("Renault Sport");

    }
}
