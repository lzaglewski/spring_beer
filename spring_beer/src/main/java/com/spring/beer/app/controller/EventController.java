package com.spring.beer.app.controller;

import com.spring.beer.app.service.EventService;
import com.spring.beer.app.service.GroupService;
import com.spring.beer.dom.Event;
import com.spring.beer.dom.Group;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    private final GroupService groupService;

    EventController(EventService eventService, GroupService groupService) {
        this.eventService = eventService;
        this.groupService = groupService;
    }

    @GetMapping("/new")
    public String showCreateEventForm( Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("groups", groupService.getAllGroups());
        return "events/createEvent";
    }

    @PostMapping
    public String createEvent(@ModelAttribute Event event, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("groups", groupService.getAllGroups());
            return "events/createEvent";
        }
        Group group = groupService.getGroupById(event.getGroup().getId());
        event.setGroup(group);
        eventService.createEvent(event);
        return "redirect:/events?groupId=" + group.getId();
    }

    @GetMapping
    public String listEvents(@RequestParam(required = false) Long groupId, Model model) {
        model.addAttribute("groups", groupService.getAllGroups());
        if (groupId != null) {
            model.addAttribute("events", eventService.getAllEvents(groupId));
        }
        return "events/events";
    }

    @GetMapping("/{eventId}/edit")
    public String showEditEventForm(@RequestParam Long groupId, @PathVariable Long eventId, Model model) {
        Event event = eventService.getEventDetails(groupId, eventId);
        model.addAttribute("event", event);
        model.addAttribute("groupId", groupId);
        return "events/editEvent";
    }

    @PostMapping("/{eventId}/edit")
    public String editEvent(@RequestParam Long groupId, @PathVariable Long eventId, @ModelAttribute Event event) {
        eventService.editEvent(groupId, eventId, event);
        return "redirect:/events?groupId=" + groupId;
    }

    @PostMapping("/{eventId}/delete")
    public String deleteEvent(@RequestParam Long groupId, @PathVariable Long eventId) {
        eventService.deleteEvent(groupId, eventId);
        return "redirect:/events?groupId=" + groupId;
    }

    @GetMapping("/{eventId}")
    public String getEventDetails(@RequestParam Long groupId, @PathVariable Long eventId, Model model) {
        Event event = eventService.getEventDetails(groupId, eventId);
        model.addAttribute("event", event);
        model.addAttribute("groupId", groupId);
        return "events/eventDetails";
    }
}
