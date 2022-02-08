package net.victu.datatracker;

import net.victu.datatracker.api.DataTracker;
import net.victu.datatracker.api.DataTrackingKey;

public class DefaultDataTrackingKeys {



    public static DataTrackingKey<Integer> DEATH_TRACKING = new DataTrackingKey<Integer>("deaths",
            new DataTracker<Integer>((event) -> 1, Integer::sum), false, Integer.class);

    public static DataTrackingKey<Integer> KILLS_TRACKING = new DataTrackingKey<Integer>("kills",
            new DataTracker<Integer>((event) -> 1, Integer::sum), false, Integer.class);


}
