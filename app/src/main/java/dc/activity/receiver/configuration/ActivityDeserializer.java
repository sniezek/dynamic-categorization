package dc.activity.receiver.configuration;

import dc.activity.Activity;
import org.apache.kafka.common.errors.SerializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

class ActivityDeserializer extends JsonDeserializer<Activity> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityDeserializer.class);

    ActivityDeserializer() {
        super(Activity.class);
    }

    @Override
    public Activity deserialize(String topic, byte[] data) {
        try {
            return super.deserialize(topic, data);
        } catch (SerializationException e) {
            LOGGER.error(e.getMessage(), e);
            LOGGER.error("Returning null Activity after unsuccessful deserialization.");

            return null;
        }
    }
}
