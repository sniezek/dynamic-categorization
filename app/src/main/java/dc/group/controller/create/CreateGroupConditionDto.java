package dc.group.controller.create;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class CreateGroupConditionDto {
    @NotNull
    private boolean reCheck;
    @NotNull
    @Valid
    private Set<CreateGroupSubconditionDto> anyOf;

    public CreateGroupConditionDto() {
    }

    public boolean isReCheck() {
        return reCheck;
    }

    public void setReCheck(boolean reCheck) {
        this.reCheck = reCheck;
    }

    public Set<CreateGroupSubconditionDto> getAnyOf() {
        return anyOf;
    }

    public void setAnyOf(Set<CreateGroupSubconditionDto> anyOf) {
        this.anyOf = anyOf;
    }
}
