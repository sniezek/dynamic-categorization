package dc.group;

import dc.ProviderId;
import dc.activity.Activity;
import dc.group.controller.create.CreateGroupConditionDto;
import dc.group.controller.create.CreateGroupDto;
import dc.group.model.*;
import dc.group.repository.GroupConditionRepository;
import dc.group.repository.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroupService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupService.class);

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

    public List<Group> getGroups(ProviderId providerId) {
        return groupRepository.findByKey_ProviderId(providerId);
    }

    public Group createGroup(ProviderId providerId, CreateGroupDto group) {
        GroupKey groupKey = new GroupKey(providerId, group.getIdentifier());
        Set<GroupCondition> allRequired = groupConditionsDtoToModel(providerId, group.getAllRequired());
        Set<GroupCondition> atLeastOneRequired = groupConditionsDtoToModel(providerId, group.getAtLeastOneRequired());

        return saveGroup(new Group(groupKey, new Date(), group.getName(), allRequired, atLeastOneRequired));
    }

    public void deleteGroup(ProviderId providerId, String identifier) {
        Optional<Group> groupToDelete = getGroup(providerId, identifier);

        if (groupToDelete.isPresent()) {
            LOGGER.info("Deleting group {}", groupToDelete.get());

            groupRepository.delete(new GroupKey(providerId, identifier));
        } else {
            throw new IllegalArgumentException("Attempt to delete an non-existent group!");
        }
    }

    public void newFbActivity(Activity activity) {
        newActivity(ProviderId.FB, activity);
    }

    private Group saveGroup(Group group) {
        LOGGER.info("Saving group {}", group);

        groupConditionRepository.save(group.getAllRequired());
        groupConditionRepository.save(group.getAtLeastOneRequired());

        return groupRepository.save(group);
    }

    private void newActivity(ProviderId providerId, Activity activity) {
        List<Group> groups = getGroups(providerId);
        String activityUserId = activity.getUserId();
        Map<String, ?> activityPayload = activity.getPayload();

        LOGGER.info("Checking {} activity {} against {} groups", providerId, activity, groups.size());

        groups.forEach(group -> {
            LOGGER.info("Checking group {} conditions", group.getKey());

            group.getConditions().forEach(condition -> {
                GroupConditionKey groupConditionKey = condition.getKey();

                LOGGER.info("Checking group condition {}", groupConditionKey);

                if (GroupConditionAnalyzer.shouldGroupConditionBeCheckedForActivity(groupConditionKey, activityPayload)) {
                    boolean activityUserIdInGroupConditionUserIds = condition.getUserIds().keySet().contains(activityUserId);

                    if (GroupConditionAnalyzer.isGroupConditionMetForActivity(groupConditionKey, activityPayload)) {
                        LOGGER.info("Group condition met");

                        saveUserIdForGroupCondition(activityUserIdInGroupConditionUserIds, condition, activityUserId);
                    } else if (groupConditionKey.isReCheck() && activityUserIdInGroupConditionUserIds) {
                        LOGGER.info("reCheck group condition not met, userId was found in the group condition and removed from it");

                        removeUserIdForGroupCondition(condition, activityUserId);
                    }
                }
            });
        });
    }

    private void saveUserIdForGroupCondition(boolean activityUserIdInGroupConditionUserIds, GroupCondition condition, String activityUserId) {
        if (!activityUserIdInGroupConditionUserIds) {
            LOGGER.info("Saving userId");

            condition.addUserId(activityUserId);
            groupConditionRepository.save(condition);
        } else {
            LOGGER.info("userId already in group condition");
        }
    }

    private void removeUserIdForGroupCondition(GroupCondition condition, String activityUserId) {
        condition.removeUserId(activityUserId);
        groupConditionRepository.save(condition);
    }

    private static Set<GroupCondition> groupConditionsDtoToModel(ProviderId providerId, Set<CreateGroupConditionDto> set) {
        return set.stream()
                .map(condition -> new GroupCondition(new GroupConditionKey(providerId, condition.isReCheck(), condition.getAnyOf().stream()
                        .map(subcondition -> new GroupSubcondition(subcondition.getEqual(), subcondition.getContain())).collect(Collectors.toSet()))))
                .collect(Collectors.toSet());
    }
}

