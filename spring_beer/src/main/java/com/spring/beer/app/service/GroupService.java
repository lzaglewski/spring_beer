package com.spring.beer.app.service;

import com.spring.beer.dom.Group;
import com.spring.beer.dom.User;
import com.spring.beer.dom.repository.GroupRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    private GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
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
}
