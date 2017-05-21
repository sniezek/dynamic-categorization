package dc.group.controller;

import dc.ProviderId;
import dc.group.model.Group;
import dc.group.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class GroupController {
    @Autowired
    private GroupService groupService;

    @PostMapping("/fb")
    public ResponseEntity<?> createGroup(@Valid @RequestBody GroupDto group) {
        return groupService.groupExists(ProviderId.FB, group.getIdentifier()) ? conflictResponse() : createdResponse(groupService.createGroup(ProviderId.FB, group));
    }

    @GetMapping("/fb/{identifier}")
    public ResponseEntity<?> getGroup(@PathVariable String identifier) {
        return groupService.getGroup(ProviderId.FB, identifier).<ResponseEntity<?>>map(GroupController::okResponse).orElseGet(GroupController::notFoundResponse);
    }

    private static ResponseEntity<String> conflictResponse() {
        return new ResponseEntity<>("A group with such name already exists!", HttpStatus.CONFLICT);
    }

    private static <T> ResponseEntity<T> createdResponse(T entity) {
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    private static ResponseEntity<String> notFoundResponse() {
        return new ResponseEntity<>("Group not found!", HttpStatus.NOT_FOUND);
    }

    private static ResponseEntity<Group> okResponse(Group entity) {
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }
}
