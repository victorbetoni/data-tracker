package net.victu.datatracker.api.function;

import net.victu.datatracker.api.event.DataTrackEvent;

/**
 * Adapt a TrackableEvent to a specific data type
 *
 * @param <U> Data type which will be extracted from the event
 */
public interface Adapter<U> {
    U adapt(DataTrackEvent event);
}
