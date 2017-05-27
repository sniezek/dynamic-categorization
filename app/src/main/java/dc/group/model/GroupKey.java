package dc.group.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dc.ProviderId;

import java.io.Serializable;
import java.util.Objects;

public class GroupKey implements Serializable {
    @JsonIgnore
    private final ProviderId providerId;
    private final String identifier;

    public GroupKey(ProviderId providerId, String identifier) {
        this.providerId = providerId;
        this.identifier = identifier;
    }

    public ProviderId getProviderId() {
        return providerId;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof GroupKey) {
            GroupKey key = (GroupKey) obj;

            return Objects.equals(providerId, key.getProviderId()) && Objects.equals(identifier, key.getIdentifier());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(providerId, identifier);
    }

    @Override
    public String toString() {
        return "{" +
                "providerId=" + providerId +
                ", identifier='" + identifier + '\'' +
                '}';
    }
}
