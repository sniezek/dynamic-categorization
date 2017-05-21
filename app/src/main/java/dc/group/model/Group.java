package dc.group.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Document
public class Group {
    @Id
    private GroupKey key;
    private Date creationDate;
    private String name;
    @DBRef
    private Set<GroupCondition> conditions;

    public Group(GroupKey key, Date creationDate, String name, Set<GroupCondition> conditions) {
        this.key = key;
        this.creationDate = creationDate;
        this.name = name;
        this.conditions = conditions;
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

    public Set<GroupCondition> getConditions() {
        return conditions;
    }

    public Set<String> getUserIds() {
        return conditions.stream()
                .flatMap(condition -> condition.getUserIds().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().filter(entry -> entry.getValue() == conditions.size()).map(Map.Entry::getKey)
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
}
