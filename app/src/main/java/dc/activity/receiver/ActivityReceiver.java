package dc.activity.receiver;

import dc.activity.Activity;
import dc.group.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.function.Consumer;

public class ActivityReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityReceiver.class);

    @Autowired
    private GroupService groupService;

    @KafkaListener(topics = "${kafka.topic.fb}")
    public void receiveFbActivity(Activity activity) {
        receiveActivity(activity, activ -> groupService.newFbActivity(activ));
    }

    @KafkaListener(topics = "${kafka.topic.dummy}")
    public void receiveDummyActivity(Activity activity) {
        receiveActivity(activity, a -> {
        });
    }

    private void receiveActivity(Activity activity, Consumer<Activity> consumer) {
        if (ActivityValidator.isValid(activity)) {
            LOGGER.info("Received valid Activity");

            consumer.accept(activity);
        } else {
            LOGGER.error("Received invalid Activity");
        }

    }
}
