package dc.group;

import dc.ProviderId;
import dc.activity.Activity;
import dc.group.controller.GroupDto;
import dc.group.model.Group;
import dc.group.model.GroupCondition;
import dc.group.model.GroupConditionKey;
import dc.group.model.GroupKey;
import dc.group.repository.GroupConditionRepository;
import dc.group.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupConditionRepository groupConditionRepository;

    public boolean groupExists(ProviderId providerId, String identifier) {
        return groupRepository.exists(new GroupKey(providerId, identifier));
    }

    public Optional<Group> getGroup(ProviderId providerId, String identifier) {
        return Optional.ofNullable(groupRepository.findOne(new GroupKey(providerId, identifier)));
    }

    public Group createGroup(ProviderId providerId, GroupDto group) {
        Set<GroupCondition> conditions = group.getConditions().stream().map(condition -> new GroupCondition(new GroupConditionKey(providerId, condition.getEqualsConditions(), condition.getContainsConditions()))).collect(Collectors.toSet());

        return saveGroup(new Group(new GroupKey(providerId, group.getIdentifier()), new Date(), group.getName(), conditions));
    }

    public Group saveGroup(Group group) {
        groupConditionRepository.save(group.getConditions());

        return groupRepository.save(group);
    }

    public void newFbActivity(Activity activity) {
        newActivity(getFbGroups(), activity);
    }

    private void newActivity(List<Group> groups, Activity activity) {
        Set<? extends Map.Entry<String, ?>> activityEntries = activity.getPayload().entrySet();

        groups.stream()
                .flatMap(group -> group.getConditions().stream())
                .filter(condition -> activityEntries.containsAll(condition.getKey().getEqualsConditions().entrySet()))
                .collect(Collectors.toSet())
                .forEach(condition -> {
                    condition.addUserId(activity.getUserId());
                    groupConditionRepository.save(condition);
                });
    }

    private List<Group> getFbGroups() {
        return groupRepository.findByKey_ProviderId(ProviderId.FB);
    }
}

