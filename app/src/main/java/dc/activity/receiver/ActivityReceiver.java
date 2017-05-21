package dc.activity.receiver;

import dc.activity.Activity;
import dc.group.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

public class ActivityReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityReceiver.class);

    @Autowired
    private GroupService groupService;

    @KafkaListener(topics = "${kafka.topic.fb}")
    public void receiveFbActivity(Activity activity) {
        if (ActivityValidator.isValid(activity)) {
            groupService.newFbActivity(activity);
        }
    }

    @KafkaListener(topics = "${kafka.topic.dummy}")
    public void receiveDummyActivity(Activity activity) {
        if (ActivityValidator.isValid(activity)) {

        }
    }
}
