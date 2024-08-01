package com.spring.beer.app.service;

import com.spring.beer.dom.Dashboard;
import com.spring.beer.dom.Event;
import com.spring.beer.dom.Group;
import com.spring.beer.dom.repository.EventRepository;
import com.spring.beer.dom.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private final GroupRepository groupRepository;
    private final EventRepository eventRepository;

    public DashboardService(GroupRepository groupRepository, EventRepository eventRepository) {
        this.groupRepository = groupRepository;
        this.eventRepository = eventRepository;
    }

    public Dashboard getUserDashboard() {
        Dashboard dashboard = new Dashboard();
        List<Group> groups = groupRepository.findAll();
        List<Event> events = eventRepository.findAll();
        dashboard.setUserGroups(groups);
        dashboard.setUpcomingEvents(events);
        return dashboard;
    }
}
