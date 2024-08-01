package com.spring.beer.app.controller;

import com.spring.beer.app.service.EventService;
import com.spring.beer.dom.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/groups/{groupId}/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/new")
    public String showCreateEventForm(@PathVariable Long groupId, Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("groupId", groupId);
        return "createEvent";
    }

    @PostMapping
    public String createEvent(@PathVariable Long groupId, @ModelAttribute Event event) {
        eventService.createEvent(groupId, event);
        return "redirect:/groups/" + groupId;
    }

    @GetMapping("/{eventId}/edit")
    public String showEditEventForm(@PathVariable Long groupId, @PathVariable Long eventId, Model model) {
        Event event = eventService.getEventDetails(groupId, eventId);
        model.addAttribute("event", event);
        model.addAttribute("groupId", groupId);
        return "editEvent";
    }

    @PostMapping("/{eventId}/edit")
    public String editEvent(@PathVariable Long groupId, @PathVariable Long eventId, @ModelAttribute Event event) {
        eventService.editEvent(groupId, eventId, event);
        return "redirect:/groups/" + groupId;
    }

    @PostMapping("/{eventId}/delete")
    public String deleteEvent(@PathVariable Long groupId, @PathVariable Long eventId) {
        eventService.deleteEvent(groupId, eventId);
        return "redirect:/groups/" + groupId;
    }

    @GetMapping("/{eventId}")
    public String getEventDetails(@PathVariable Long groupId, @PathVariable Long eventId, Model model) {
        Event event = eventService.getEventDetails(groupId, eventId);
        model.addAttribute("event", event);
        model.addAttribute("groupId", groupId);
        return "eventDetails";
    }
}
