package com.spring.beer.app.controller;

import com.spring.beer.app.service.EventResponseService;
import com.spring.beer.dom.EventResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/events/{eventId}/responses")
public class EventResponseController {

    @Autowired
    private EventResponseService eventResponseService;

    @GetMapping("/new")
    public String showResponseForm(@PathVariable Long eventId, Model model) {
        model.addAttribute("response", new EventResponse());
        model.addAttribute("eventId", eventId);
        return "createResponse";
    }

    @PostMapping
    public String respondToEvent(@PathVariable Long eventId, @ModelAttribute EventResponse response) {
        eventResponseService.respondToEvent(eventId, response);
        return "redirect:/events/" + eventId;
    }

    @GetMapping("/{responseId}/edit")
    public String showEditResponseForm(@PathVariable Long eventId, @PathVariable Long responseId, Model model) {
        EventResponse response = eventResponseService.getResponseDetails(eventId, responseId);
        model.addAttribute("response", response);
        model.addAttribute("eventId", eventId);
        return "editResponse";
    }

    @PostMapping("/{responseId}/edit")
    public String editResponse(@PathVariable Long eventId, @PathVariable Long responseId, @ModelAttribute EventResponse response) {
        eventResponseService.editResponse(eventId, responseId, response);
        return "redirect:/events/" + eventId;
    }

    @PostMapping("/{responseId}/delete")
    public String deleteResponse(@PathVariable Long eventId, @PathVariable Long responseId) {
        eventResponseService.deleteResponse(eventId, responseId);
        return "redirect:/events/" + eventId;
    }
}
