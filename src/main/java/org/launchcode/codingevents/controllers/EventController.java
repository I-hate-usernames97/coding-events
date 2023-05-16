package org.launchcode.codingevents.controllers;



import org.launchcode.codingevents.controllers.models.Event;
import org.launchcode.codingevents.controllers.models.EventType;

import org.launchcode.codingevents.data.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping
    public String displayAllEvents(Model model) {
        model.addAttribute("title", "All Events");

        model.addAttribute("events", eventRepository.findAll());
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

        eventRepository.save(newEvent);
        return "redirect:";
    }

    @GetMapping("delete")
    public String displayDeleteEventForm(Model model) {

        model.addAttribute("title", "Delete Events");
        model.addAttribute("events", eventRepository.findAll());
        return "events/delete";
    }

    @PostMapping("delete")
    public String deleteEvent(@RequestParam(required = false) int[] eventIds) {

        if (eventIds != null) {
            for (int id : eventIds) {
                eventRepository.deleteById(id);
            }
        }
        return "redirect:";
    }

    @GetMapping("edit/{eventId}")
    public String displayEditForm(Model model, @PathVariable int id) {
        Optional<Event> event = eventRepository.findById(id);
        model.addAttribute("event", event);
        String title = "Edit Event " + event;
        model.addAttribute("title", title );
        model.addAttribute("types", EventType.values());
        return "events/edit";
    }

    @PostMapping("edit")
    public String processEditForm(int eventId, @ModelAttribute Event nawEvent ) {
       eventRepository.findById(eventId);

//        eventToEdit.setName(nawEvent.getName());
//        eventToEdit.setDescription(nawEvent.getDescription());
//        eventToEdit.setContactEmail(nawEvent.getContactEmail());
//        eventToEdit.setLocation(nawEvent.getLocation());
//        eventToEdit.setNumberOfAttendees(nawEvent.getNumberOfAttendees());
//        eventToEdit.setType(nawEvent.getType());
        return "redirect:";
    }
}

