package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.controllers.models.Event;
import org.launchcode.codingevents.data.EventData;

import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("events")
public class EventController {


    @GetMapping
    public String dusplayAllEvents(Model model) {
        model.addAttribute("events", EventData.getAll());
        return "events/index.html";
    }

    @GetMapping("create")
    public String renderCreateEvent() {
        return "events/create";
    }

    @PostMapping("create")
    public String createEvent(@RequestParam String eventName, String eventDescription) {
        EventData.addEvent((new Event(eventName, eventDescription)));
        return "redirect:";
    }

    @GetMapping("delete")
    public String dusplayDeleteEventForm(Model model) {

        model.addAttribute("title", "Delete Events");
        model.addAttribute("events", EventData.getAll());

        return "events/delete";
    }

    @PostMapping("delete")
    public String deleteEvent(@RequestParam(required = false) int[] eventIds) {

        if (eventIds != null) {
            for (int id : eventIds) {
                EventData.remove(id);
            }
        }
        return "redirect:";
    }

    @GetMapping("edit/{eventId}")
    public String displayEditForm(Model model, @PathVariable int eventId) {
        Event eventToEdit = EventData.getById(eventId);
        model.addAttribute("event", eventToEdit);
        String title = "Edit Event " + eventToEdit.getName() + " (id=" + eventToEdit.getId() + ")";
        model.addAttribute("title", title );
        return "events/edit";
    }

    @PostMapping("edit")
    public String processEditForm(int eventId, String name, String description) {
        Event eventToEdit = EventData.getById(eventId);
        eventToEdit.setName(name);
        eventToEdit.setDescription(description);
        return "redirect:";
    }
}
