package dc.activity;

import java.util.Date;
import java.util.Map;

public class Activity {
    private Date date;
    private String userId;
    private Map<String, ?> payload;

    public Activity() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, ?> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, ?> payload) {
        this.payload = payload;
    }
}
