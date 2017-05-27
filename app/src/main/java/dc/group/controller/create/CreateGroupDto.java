package dc.group.controller.create;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class CreateGroupDto {
    @NotEmpty
    private String identifier;
    @NotEmpty
    private String name;
    @NotNull
    @Valid
    private Set<CreateGroupConditionDto> allRequired;
    @NotNull
    @Valid
    private Set<CreateGroupConditionDto> atLeastOneRequired;

    public CreateGroupDto() {
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

    public Set<CreateGroupConditionDto> getAllRequired() {
        return allRequired;
    }

    public Set<CreateGroupConditionDto> getAtLeastOneRequired() {
        return atLeastOneRequired;
    }
}
