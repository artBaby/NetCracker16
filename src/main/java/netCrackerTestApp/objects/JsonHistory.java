package netCrackerTestApp.objects;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonHistory {
    private Date createdDate;
    private String topic;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss.SSS");


    @JsonCreator
    public JsonHistory(@JsonProperty("createdDate") Date createdDate, @JsonProperty("topic") String topic) {
        this.createdDate = createdDate;
        this.topic = topic;
    }


    public String getCreatedDate() {
        return simpleDateFormat.format(createdDate);
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
