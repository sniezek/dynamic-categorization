package dc.group.controller;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class GroupConditionDto {
    @NotNull
    private Map<String, ?> equalsConditions;
    @NotNull
    private Map<String, ?> containsConditions;

    public GroupConditionDto() {
    }

    public Map<String, ?> getEqualsConditions() {
        return equalsConditions;
    }

    public void setEqualsConditions(Map<String, ?> equalsConditions) {
        this.equalsConditions = equalsConditions;
    }

    public Map<String, ?> getContainsConditions() {
        return containsConditions;
    }

    public void setContainsConditions(Map<String, ?> containsConditions) {
        this.containsConditions = containsConditions;
    }
}
