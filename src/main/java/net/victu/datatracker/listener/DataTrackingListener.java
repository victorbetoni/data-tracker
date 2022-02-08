package net.victu.datatracker.listener;

import net.victu.datatracker.api.DataTracker;
import net.victu.datatracker.api.DataTrackingKey;
import net.victu.datatracker.api.event.DataTrackEvent;
import net.victu.datatracker.controller.SingleDataTrackerController;
import net.victu.datatracker.controller.SingleProfileController;
import net.victu.datatracker.model.Profile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class DataTrackingListener implements Listener {

    @EventHandler
    public void handle(DataTrackEvent event) {
        DataTrackingKey<?> tracker = SingleDataTrackerController.INSTANCE.getTrackers().get(event.getTrackedInfo());
        if(tracker == null) {
            throw new IllegalArgumentException("Unknown data tracking key: " + event.getTrackedInfo());
        }
        DataTracker<?> dataTracker = tracker.getTracker();
        SingleProfileController controller = SingleProfileController.INSTANCE;
        Object data = tracker.getTracker().getAdapter().adapt(event);
        Profile profile = controller.getProfile(event.getAffected());
        Object current = profile.getStat(event.getTrackedInfo());
        Object newData = dataTracker.forceJoin(current, data);
        profile.updateData(event.getTrackedInfo(), newData);
    }

}
