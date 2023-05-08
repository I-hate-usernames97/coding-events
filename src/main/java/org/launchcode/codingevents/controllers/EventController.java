package org.launchcode.codingevents.controllers;



import org.launchcode.codingevents.controllers.models.Event;
import org.launchcode.codingevents.controllers.models.EventType;
import org.launchcode.codingevents.data.EventData;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;


@Controller
@RequestMapping("events")
public class EventController {


    @GetMapping
    public String dusplayAllEvents(Model model) {
        model.addAttribute("events", EventData.getAll());
        return "events/index.html";
    }

    @GetMapping("create")
    public String renderCreateEvent(Model model) {
        model.addAttribute("title", "Create Event");
        model.addAttribute("event", new Event());
        model.addAttribute("types", EventType.values());
        return "events/create";
    }

    @PostMapping("create")
    public String processCreateEventForm(@ModelAttribute @Valid Event newEvent,
                                         Errors errors, Model model) {
        if(errors.hasErrors()) {
            model.addAttribute("title", "Create Event");
            model.addAttribute("types", EventType.values());
            return "events/create";
        }

        EventData.addEvent(newEvent);
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
        String title = "Edit Event " + eventToEdit.getName() + " (ID=" + eventToEdit.getId() + ")";
        model.addAttribute("title", title );
        model.addAttribute("types", EventType.values());
        return "events/edit";
    }

    @PostMapping("edit")
    public String processEditForm(int eventId, @ModelAttribute Event nawEvent ) {
        Event eventToEdit = EventData.getById(eventId);
        eventToEdit.setName(nawEvent.getName());
        eventToEdit.setDescription(nawEvent.getDescription());
        eventToEdit.setContactEmail(nawEvent.getContactEmail());
        eventToEdit.setLocation(nawEvent.getLocation());
        eventToEdit.setNumberOfAttendees(nawEvent.getNumberOfAttendees());
        eventToEdit.setType(nawEvent.getType());
        return "redirect:";
    }
}

