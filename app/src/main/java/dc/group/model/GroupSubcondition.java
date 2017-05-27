package dc.group.model;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class GroupSubcondition {
    private final Map<String, Set<?>> equal;
    private final Map<String, Set<?>> contain;

    public GroupSubcondition(Map<String, Set<?>> equal, Map<String, Set<?>> contain) {
        this.equal = equal;
        this.contain = contain;
    }

    public Map<String, Set<?>> getEqual() {
        return equal;
    }

    public Map<String, Set<?>> getContain() {
        return contain;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof GroupSubcondition) {
            GroupSubcondition subcondition = (GroupSubcondition) obj;

            return Objects.equals(equal, subcondition.getEqual()) && Objects.equals(contain, subcondition.getContain());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(equal, contain);
    }

    @Override
    public String toString() {
        return "{" +
                "equal=" + equal +
                ", contain=" + contain +
                '}';
    }
}
