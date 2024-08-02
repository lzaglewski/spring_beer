package com.spring.beer.app.controller;

import com.spring.beer.app.service.GroupService;
import com.spring.beer.app.service.UserService;
import com.spring.beer.dom.Group;
import com.spring.beer.dom.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/groups")
public class GroupController {

    private final GroupService groupService;
    private final UserService userService;

    public GroupController(GroupService groupService, UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }

    @GetMapping("/new")
    public String showCreateGroupForm(Model model) {
        model.addAttribute("group", new Group());
        return "groups/createGroup";
    }

    @PostMapping
    public String createGroup(@ModelAttribute Group group) {
        User currentUser = getCurrentUser();
        groupService.createGroup(group, currentUser);
        return "redirect:/dashboard";
    }

    @GetMapping("/{groupId}/edit")
    public String showEditGroupForm(@PathVariable Long groupId, Model model) {
        Group group = groupService.getGroupDetails(groupId);
        model.addAttribute("group", group);
        return "groups/editGroup";
    }

    @PostMapping("/{groupId}/edit")
    public String editGroup(@PathVariable Long groupId, @ModelAttribute Group group) {
        groupService.editGroup(groupId, group);
        return "redirect:/dashboard";
    }

    @PostMapping("/{groupId}/delete")
    public String deleteGroup(@PathVariable Long groupId) {
        groupService.deleteGroup(groupId);
        return "redirect:/dashboard";
    }

    @GetMapping("/{groupId}")
    public String getGroupDetails(@PathVariable Long groupId, Model model) {
        Group group = groupService.getGroupDetails(groupId);
        model.addAttribute("group", group);
        return "groups/groupDetails";
    }

    @GetMapping("/list")
    public String listGroups(Model model) {
        model.addAttribute("groups", groupService.getAllGroups());
        return "groups/groups";
    }

    @GetMapping("/{groupId}/generateHash")
    public String generateGroupHash(@PathVariable Long groupId) {
        groupService.generateGroupHash(groupId);
        return "redirect:/groups/" + groupId;
    }

    @GetMapping("/join/{hash}")
    public String joinGroupByHash(@PathVariable String hash, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        User user = userService.findByUsername(userDetails.getUsername());
        Group group = groupService.getGroupByHash(hash);
        if (group != null) {
            groupService.addUserToGroup(user, group);
            return "redirect:/groups/" + group.getId();
        }
        model.addAttribute("error", "Invalid hash");
        return "error";
    }


    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userService.findByUsername(userDetails.getUsername());
    }

}
