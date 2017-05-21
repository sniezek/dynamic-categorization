package dc.activity.receiver;

import dc.activity.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ActivityValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityValidator.class);

    static boolean isValid(Activity activity) {
        if (activity.getDate() == null || activity.getUserId() == null && activity.getPayload() == null) {
            LOGGER.error("Received invalid Activity");
            return false;
        }

        LOGGER.info("Received valid Activity");
        return true;
    }
}
