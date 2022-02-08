package net.victu.datatracker.api;

import net.victu.datatracker.api.function.Adapter;
import net.victu.datatracker.api.function.Joiner;

public class DataTracker<DATA> {
    private Adapter<DATA> adapter;
    private Joiner<DATA> joiner;

    public DataTracker(Adapter<DATA> adapter, Joiner<DATA> joiner) {
        this.adapter = adapter;
        this.joiner = joiner;
    }

    @SuppressWarnings("unchecked")
    public DATA forceJoin(Object current, Object incoming) {
        return joiner.join((DATA) current, (DATA) incoming);
    }

    public Adapter<DATA> getAdapter() {
        return adapter;
    }

    public Joiner<DATA> getJoiner() {
        return joiner;
    }

}
