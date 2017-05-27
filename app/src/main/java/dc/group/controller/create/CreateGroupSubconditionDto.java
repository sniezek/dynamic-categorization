package dc.group.controller.create;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

public class CreateGroupSubconditionDto {
    @NotNull
    private Map<String, Set<?>> equal;
    @NotNull
    private Map<String, Set<?>> contain;

    public CreateGroupSubconditionDto() {
    }

    public Map<String, Set<?>> getEqual() {
        return equal;
    }

    public void setEqual(Map<String, Set<?>> equal) {
        this.equal = equal;
    }

    public Map<String, Set<?>> getContain() {
        return contain;
    }

    public void setContain(Map<String, Set<?>> contain) {
        this.contain = contain;
    }
}
