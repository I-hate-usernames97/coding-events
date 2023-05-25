package org.launchcode.codingevents.controllers;



import org.launchcode.codingevents.controllers.models.Event;

import org.launchcode.codingevents.controllers.models.EventCategory;
import org.launchcode.codingevents.data.EventCategoryRepository;
import org.launchcode.codingevents.data.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @GetMapping
    public String displayEvents(@RequestParam(required = false) Integer categoryId, Model model) {

        if (categoryId == null) {
            model.addAttribute("title", "All Events");
            model.addAttribute("events", eventRepository.findAll());
        } else {
            Optional<EventCategory> result = eventCategoryRepository.findById(categoryId);
            if (result.isEmpty()) {
                model.addAttribute("title", "Invalid Category ID: " + categoryId);
            } else {
                EventCategory category = result.get();
                model.addAttribute("title", "Events in category: " + category.getName());
                model.addAttribute("events", category.getEvents());
            }
        }
        return "events/index";
    }

    @GetMapping("create")
    public String renderCreateEvent(Model model) {
        model.addAttribute("title", "Create Event");
        model.addAttribute(new Event());
        model.addAttribute("categories", eventCategoryRepository.findAll());
        return "events/create";
    }

    @PostMapping("create")
    public String processCreateEventForm(@ModelAttribute @Valid Event newEvent,
                                         Errors errors, Model model) {
        if(errors.hasErrors()) {
            model.addAttribute("title", "Create Event");
            model.addAttribute("categories", eventCategoryRepository.findAll());
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
    public String displayEditForm(Model model, @PathVariable int eventId) {


        Optional optEvent = eventRepository.findById(eventId);
        if (optEvent.isPresent()) {
//            Event event = (Event) optEvent.get();
            model.addAttribute("event", eventRepository.findById(eventId));
            model.addAttribute("categories", eventCategoryRepository.findAll());

            return "events/edit";
        }

        return "redirect:";
    }

    @PostMapping("edit")
    public String processEditForm( @ModelAttribute Event newEvent ) {
//       @RequestParam Integer id,
//        Optional<Event> optEvent = eventRepository.findById(id);
//        if (optEvent.isPresent()) {
//            eventRepository.save(newEvent);
//        }
        eventRepository.save(newEvent);
        return "redirect:";
    }

    @GetMapping("detail")
    public String displayEventDetail(@RequestParam Integer eventId, Model model) {

        Optional<Event> optEvent = eventRepository.findById(eventId);
        if(optEvent.isEmpty()) {
            model.addAttribute("title", "Invalid Event ID");
        } else {
            Event event =optEvent.get();
            model.addAttribute("title", event.getName() + " Detail");
            model.addAttribute("event", event);
        }

        return "events/detail";
    }

}

