package dc.group.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Document
public class Group {
    @Id
    private GroupKey key;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date creationDate;
    private String name;
    @DBRef
    private Set<GroupCondition> allRequired;
    @DBRef
    private Set<GroupCondition> atLeastOneRequired;

    public Group(GroupKey key, Date creationDate, String name, Set<GroupCondition> allRequired, Set<GroupCondition> atLeastOneRequired) {
        this.key = key;
        this.creationDate = creationDate;
        this.name = name;
        this.allRequired = allRequired;
        this.atLeastOneRequired = atLeastOneRequired;
    }

    public GroupKey getKey() {
        return key;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getName() {
        return name;
    }

    public Set<GroupCondition> getAllRequired() {
        return allRequired;
    }

    public Set<GroupCondition> getAtLeastOneRequired() {
        return atLeastOneRequired;
    }

    @JsonIgnore
    public Set<GroupCondition> getConditions() {
        return Stream.of(allRequired, atLeastOneRequired).flatMap(Collection::stream).collect(Collectors.toSet());
    }

    public Set<String> getUserIds() {
        return allRequired.stream()
                .flatMap(condition -> condition.getUserIds().keySet().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() == allRequired.size())
                .map(Map.Entry::getKey)
                .filter(userId -> {
                    Set<String> userIdsOfAtLeastOneRequired = atLeastOneRequired.stream()
                            .flatMap(condition -> condition.getUserIds().keySet().stream()).collect(Collectors.toSet());

                    return userIdsOfAtLeastOneRequired.isEmpty() || userIdsOfAtLeastOneRequired.contains(userId);
                })
                .collect(Collectors.toSet());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Group) {
            Group group = (Group) obj;

            return key.equals(group.getKey());
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
                ", creationDate=" + creationDate +
                ", name='" + name + '\'' +
                ", allRequired=" + allRequired +
                ", atLeastOneRequired=" + atLeastOneRequired +
                '}';
    }
}
