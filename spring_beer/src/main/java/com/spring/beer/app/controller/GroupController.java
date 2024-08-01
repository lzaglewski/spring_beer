package com.spring.beer.app.controller;

import com.spring.beer.app.service.GroupService;
import com.spring.beer.app.service.UserService;
import com.spring.beer.dom.Group;
import com.spring.beer.dom.User;
import org.springframework.security.core.Authentication;
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
        return "createGroup";
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
        return "editGroup";
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
        return "groupDetails";
    }

    @GetMapping("/list")
    public String listGroups(Model model) {
        model.addAttribute("groups", groupService.getAllGroups());
        return "groups";
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userService.findByUsername(userDetails.getUsername());
    }

}
