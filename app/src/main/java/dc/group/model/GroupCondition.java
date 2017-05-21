package dc.group.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document
public class GroupCondition {
    @Id
    private GroupConditionKey key;
    private Set<String> userIds;

    public GroupCondition(GroupConditionKey key) {
        this.key = key;
        this.userIds = new HashSet<>();
    }

    public GroupConditionKey getKey() {
        return key;
    }

    public Set<String> getUserIds() {
        return userIds;
    }

    public void addUserId(String userId) {
        userIds.add(userId);
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
}
