package net.victu.datatracker.api.function;

/**
 * Merge the currently tracked data with the incoming adapted data
 *
 * @param <T> Type of the data that will be merged
 */
public interface Joiner<T> {
    T join(T currentData, T incoming);
}
