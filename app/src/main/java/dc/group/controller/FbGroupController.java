package dc.group.controller;

import dc.ProviderId;
import dc.group.controller.create.CreateGroupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class FbGroupController {
    @Autowired
    private GroupController controller;

    @PostMapping("/fb")
    public ResponseEntity<?> createFbGroup(@Valid @RequestBody CreateGroupDto group) {
        return controller.createGroup(ProviderId.FB, group);
    }

    @GetMapping("/fb")
    public ResponseEntity<?> getFbGroupsInformation() {
        return controller.getGroupsInformation(ProviderId.FB);
    }

    @GetMapping("/fb/detailed")
    public ResponseEntity<?> getFbGroupsDetailedInformation() {
        return controller.getGroupsDetailedInformation(ProviderId.FB);
    }

    @GetMapping("/fb/{identifier}")
    public ResponseEntity<?> getFbGroupInformation(@PathVariable String identifier) {
        return controller.getGroupInformation(ProviderId.FB, identifier);
    }

    @GetMapping("/fb/{identifier}/detailed")
    public ResponseEntity<?> getFbGroupDetailedInformation(@PathVariable String identifier) {
        return controller.getGroupDetailedInformation(ProviderId.FB, identifier);
    }

    @DeleteMapping("/fb/{identifier}")
    public ResponseEntity<?> deleteFbGroup(@PathVariable String identifier) {
        return controller.deleteGroup(ProviderId.FB, identifier);
    }
}
