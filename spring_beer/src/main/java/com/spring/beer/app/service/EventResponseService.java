package com.spring.beer.app.service;

import com.spring.beer.dom.Event;
import com.spring.beer.dom.EventResponse;
import com.spring.beer.dom.repository.EventRepository;
import com.spring.beer.dom.repository.EventResponseRepository;
import org.springframework.stereotype.Service;

@Service
public class EventResponseService {

    private final EventResponseRepository eventResponseRepository;
    private final EventRepository eventRepository;

    public EventResponseService(EventResponseRepository eventResponseRepository, EventRepository eventRepository) {
        this.eventResponseRepository = eventResponseRepository;
        this.eventRepository = eventRepository;
    }

    public void respondToEvent(Long eventId, EventResponse response) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        response.setEvent(event);
        eventResponseRepository.save(response);
    }

    public EventResponse getResponseDetails(Long eventId, Long responseId) {
        return eventResponseRepository.findById(responseId).orElseThrow();
    }

    public void editResponse(Long eventId, Long responseId, EventResponse response) {
        EventResponse existingResponse = eventResponseRepository.findById(responseId).orElseThrow();
        existingResponse.setResponse(response.getResponse());
        eventResponseRepository.save(existingResponse);
    }

    public void deleteResponse(Long eventId, Long responseId) {
        eventResponseRepository.deleteById(responseId);
    }
}
