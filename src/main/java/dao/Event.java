package dao;

public class Event {

    private Type type;
    private String timestamp;
    private Object payload;

    public Event(Type type, String timestamp, Object payload) {
        this.type = type;
        this.timestamp = timestamp;
        this.payload = payload;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}
