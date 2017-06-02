package dc.group.controller;

import dc.ProviderId;
import dc.group.GroupService;
import dc.group.controller.create.CreateGroupDto;
import dc.group.controller.info.InfoGroupDto;
import dc.group.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class GroupController {
    @Autowired
    private GroupService groupService;

    ResponseEntity<?> createGroup(ProviderId providerId, CreateGroupDto group) {
        return groupService.groupExists(providerId, group.getIdentifier()) ? conflictResponse() : createdResponse(groupService.createGroup(providerId, group));
    }

    ResponseEntity<List<InfoGroupDto>> getGroupsInformation(ProviderId providerId) {
        return okResponse(groupService.getGroups(providerId).stream()
                .map(group -> new InfoGroupDto(group.getIdentifier(), group.getUserIds()))
                .collect(Collectors.toList()));
    }

    ResponseEntity<List<Group>> getGroupsDetailedInformation(ProviderId providerId) {
        return okResponse(groupService.getGroups(providerId));
    }

    ResponseEntity<?> getGroupInformation(ProviderId providerId, String identifier) {
        return groupService.getGroup(providerId, identifier)
                .map(group -> new InfoGroupDto(group.getIdentifier(), group.getUserIds()))
                .<ResponseEntity<?>>map(GroupController::okResponse)
                .orElseGet(GroupController::notFoundResponse);
    }

    ResponseEntity<?> getGroupDetailedInformation(ProviderId providerId, String identifier) {
        return groupService.getGroup(providerId, identifier)
                .<ResponseEntity<?>>map(GroupController::okResponse)
                .orElseGet(GroupController::notFoundResponse);
    }

    ResponseEntity<?> deleteGroup(ProviderId providerId, String identifier) {
        if (groupService.groupExists(providerId, identifier)) {
            groupService.deleteGroup(providerId, identifier);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return notFoundResponse();
    }

    private static ResponseEntity<String> conflictResponse() {
        return new ResponseEntity<>("A group with such name already exists!", HttpStatus.CONFLICT);
    }

    private static ResponseEntity<Group> createdResponse(Group group) {
        return new ResponseEntity<>(group, HttpStatus.CREATED);
    }

    private static ResponseEntity<String> notFoundResponse() {
        return new ResponseEntity<>("Group not found!", HttpStatus.NOT_FOUND);
    }

    private static <T> ResponseEntity<T> okResponse(T entity) {
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }
}
