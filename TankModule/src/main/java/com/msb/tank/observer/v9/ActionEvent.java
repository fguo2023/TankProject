package com.msb.tank.observer.v9;

public class ActionEvent {
    long timestamp;
    Object source;

    public ActionEvent(long timestamp, Object source) {
        this.timestamp = timestamp;
        this.source = source;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Object getSource() {
        return source;
    }
}
