package netCrackerTestApp.objects;

import netCrackerTestApp.objects.Account;
import java.io.Serializable;

public class StreamTask implements Serializable {
    public String topic;
    public Account account;

    public StreamTask(String topic, Account account) {
        this.topic = topic;
        this.account = account;
    }
}
