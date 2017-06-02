package dc.group.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dc.ProviderId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Document
public class GroupCondition {
    @Id
    private GroupConditionKey key;
    @JsonIgnore
    private Map<String, Date> userIds;

    public GroupCondition(GroupConditionKey key) {
        this.key = key;
        this.userIds = new HashMap<>();
    }

    public GroupConditionKey getKey() {
        return key;
    }

    public ProviderId getProviderId() {
        return key.getProviderId();
    }

    public boolean isReCheck() {
        return key.isReCheck();
    }

    public Set<GroupSubcondition> getAnyOf() {
        return key.getAnyOf();
    }

    public Map<String, Date> getUserIds() {
        return userIds;
    }

    public void addUserId(String userId) {
        userIds.put(userId, new Date());
    }

    public void removeUserId(String userId) {
        userIds.remove(userId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof GroupCondition) {
            GroupCondition groupCondition = (GroupCondition) obj;

            return key.equals(groupCondition.getKey());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public String toString() {
        return "{" +
                "key=" + key +
                ", userIds=" + userIds +
                '}';
    }
}
