package com.spring.beer.app.service;

import com.spring.beer.dom.Group;
import com.spring.beer.dom.GroupMembership;
import com.spring.beer.dom.User;
import com.spring.beer.dom.repository.GroupMembershipRepository;
import com.spring.beer.dom.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GroupService {

    private GroupRepository groupRepository;
    private final GroupMembershipRepository groupMembershipRepository;

    public GroupService(GroupRepository groupRepository, GroupMembershipRepository groupMembershipRepository) {
        this.groupRepository = groupRepository;
        this.groupMembershipRepository = groupMembershipRepository;
    }

    public void createGroup(Group group, User admin) {
        group.setAdmin(admin);
        groupRepository.save(group);
    }

    public Group getGroupDetails(Long groupId) {
        return groupRepository.findById(groupId).orElseThrow();
    }

    public void editGroup(Long groupId, Group group) {
        Group existingGroup = groupRepository.findById(groupId).orElseThrow();
        existingGroup.setName(group.getName());
        existingGroup.setDescription(group.getDescription());
        groupRepository.save(existingGroup);
    }

    public void deleteGroup(Long groupId) {
        groupRepository.deleteById(groupId);
    }

    public Object getAllGroups() {
        return groupRepository.findAll();
    }

    public Group getGroupById(Long id) {
        return groupRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid group ID"));
    }

    public void generateGroupHash(Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new IllegalArgumentException("Invalid group ID"));
        group.setHash(UUID.randomUUID().toString());
        groupRepository.save(group);
    }

    public Group getGroupByHash(String hash) {
        return groupRepository.findByHash(hash);
    }

    public void addUserToGroup(User user, Group group) {
        boolean isMember = group.getGroupMemberships().stream()
                .anyMatch(membership -> membership.getUser().getId().equals(user.getId()));

        if (!isMember) {
            GroupMembership groupMembership = new GroupMembership(user, group);
            groupMembershipRepository.save(groupMembership);
            group.getGroupMemberships().add(groupMembership);
            groupRepository.save(group);
        }
    }
}
