package dc.activity.receiver;

import dc.activity.Activity;

class ActivityValidator {
    static boolean isValid(Activity activity) {
        return activity.getDate() != null && activity.getUserId() != null && activity.getPayload() != null;
    }
}
