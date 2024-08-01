package com.spring.beer.dom;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Dashboard {
    private List<Group> userGroups;
    private List<Event> upcomingEvents;

    // Getters and Setters
    public List<Group> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(List<Group> userGroups) {
        this.userGroups = userGroups;
    }

    public List<Event> getUpcomingEvents() {
        return upcomingEvents;
    }

    public void setUpcomingEvents(List<Event> upcomingEvents) {
        this.upcomingEvents = upcomingEvents;
    }
}
