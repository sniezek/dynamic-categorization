package dc.group.controller.info;

import java.util.Set;

public class InfoGroupDto {
    private final String identifier;
    private final Set<String> userIds;

    public InfoGroupDto(String identifier, Set<String> userIds) {
        this.identifier = identifier;
        this.userIds = userIds;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Set<String> getUserIds() {
        return userIds;
    }
}
