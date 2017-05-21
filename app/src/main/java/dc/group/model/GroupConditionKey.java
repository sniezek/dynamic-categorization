package dc.group.model;

import dc.ProviderId;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class GroupConditionKey implements Serializable {
    private final ProviderId providerId;
    private final Map<String, ?> equalsConditions;
    private final Map<String, ?> containsConditions;

    public GroupConditionKey(ProviderId providerId, Map<String, ?> equalsConditions, Map<String, ?> containsConditions) {
        this.providerId = providerId;
        this.equalsConditions = equalsConditions;
        this.containsConditions = containsConditions;
    }

    public ProviderId getProviderId() {
        return providerId;
    }

    public Map<String, ?> getEqualsConditions() {
        return equalsConditions;
    }

    public Map<String, ?> getContainsConditions() {
        return containsConditions;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof GroupConditionKey) {
            GroupConditionKey key = (GroupConditionKey) obj;

            return Objects.equals(providerId, key.getProviderId()) && Objects.equals(equalsConditions, key.getEqualsConditions()) && Objects.equals(containsConditions, key.getContainsConditions());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(providerId, equalsConditions, containsConditions);
    }
}
