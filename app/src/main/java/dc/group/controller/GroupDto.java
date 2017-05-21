package dc.group.controller;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.Set;

public class GroupDto {
    @NotEmpty
    private String identifier;
    @NotEmpty
    private String name;
    @NotEmpty
    @Valid
    private Set<GroupConditionDto> conditions;

    public GroupDto() {
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<GroupConditionDto> getConditions() {
        return conditions;
    }

    public void setConditions(Set<GroupConditionDto> conditions) {
        this.conditions = conditions;
    }
}
