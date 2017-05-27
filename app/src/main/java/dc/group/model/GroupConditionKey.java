package dc.group.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dc.ProviderId;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class GroupConditionKey implements Serializable {
    @JsonIgnore
    private final ProviderId providerId;
    private final boolean reCheck;
    private final Set<GroupSubcondition> anyOf;

    public GroupConditionKey(ProviderId providerId, boolean reCheck, Set<GroupSubcondition> anyOf) {
        this.providerId = providerId;
        this.reCheck = reCheck;
        this.anyOf = anyOf;
    }

    public ProviderId getProviderId() {
        return providerId;
    }

    public boolean isReCheck() {
        return reCheck;
    }

    public Set<GroupSubcondition> getAnyOf() {
        return anyOf;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof GroupConditionKey) {
            GroupConditionKey key = (GroupConditionKey) obj;

            return Objects.equals(providerId, key.getProviderId()) && Objects.equals(reCheck, key.isReCheck()) && Objects.equals(anyOf, key.getAnyOf());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(providerId, reCheck, anyOf);
    }

    @Override
    public String toString() {
        return "{" +
                "providerId=" + providerId +
                ", reCheck=" + reCheck +
                ", anyOf=" + anyOf +
                '}';
    }
}
