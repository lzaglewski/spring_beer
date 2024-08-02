package com.spring.beer.app.service;

import com.spring.beer.dom.Event;
import com.spring.beer.dom.repository.EventRepository;
import com.spring.beer.dom.repository.GroupRepository;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final GroupRepository groupRepository;
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository, GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
        this.eventRepository = eventRepository;
    }

    public void createEvent(Event event) {
        eventRepository.save(event);
    }

    public Event getEventDetails(Long groupId, Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new IllegalArgumentException("Invalid event ID"));
    }

    public void editEvent(Long groupId, Long eventId, Event event) {
        Event existingEvent = eventRepository.findById(eventId).orElseThrow();
        existingEvent.setName(event.getName());
        existingEvent.setDescription(event.getDescription());
        existingEvent.setDate(event.getDate());
        eventRepository.save(existingEvent);
    }

    public void deleteEvent(Long groupId, Long eventId) {
        eventRepository.deleteById(eventId);
    }

    public Object getAllEvents(Long groupId) {
        return eventRepository.findAllByGroupId(groupId);
    }
}
