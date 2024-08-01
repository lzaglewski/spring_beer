package com.spring.beer.app.controller;

import com.spring.beer.app.service.GroupMembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/groups/{groupId}")
public class GroupMembershipController {

    @Autowired
    private GroupMembershipService groupMembershipService;

    @PostMapping("/join")
    public String joinGroup(@PathVariable Long groupId) {
        groupMembershipService.joinGroup(groupId);
        return "redirect:/groups/" + groupId;
    }

    @PostMapping("/leave")
    public String leaveGroup(@PathVariable Long groupId) {
        groupMembershipService.leaveGroup(groupId);
        return "redirect:/dashboard";
    }
}
