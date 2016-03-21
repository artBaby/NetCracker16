package netCrackerTestApp;

import netCrackerTestApp.objects.Account;

import java.io.Serializable;

public class StreamTask implements Serializable {
    String topic;
    Account account;

    public StreamTask(String topic, Account account) {
        this.topic = topic;
        this.account = account;
    }
}
