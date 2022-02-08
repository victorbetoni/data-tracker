package net.victu.datatracker.api;

public class DataTrackingKey<DATA> {

    private String key;
    private DataTracker<DATA> tracker;
    private boolean isRankeable;
    private Class<DATA> dataType;

    public DataTrackingKey(String key, DataTracker<DATA> tracker, boolean isRankeable, Class<DATA> dataType) {
        this.key = key;
        this.tracker = tracker;
        this.dataType = dataType;
        if(isRankeable && !dataType.isAssignableFrom(Number.class)) {
            throw new IllegalArgumentException("Only numeric data types can be ranked.");
        }
        this.isRankeable = isRankeable;
    }

    public boolean isRankeable() {
        return isRankeable;
    }

    public Class<DATA> getDataType() {
        return dataType;
    }

    public String getKey() {
        return key;
    }

    public DataTracker<DATA> getTracker() {
        return tracker;
    }
}
