package org.launchcode.codingevents.controllers;


import org.launchcode.codingevents.controllers.models.*;
import org.launchcode.codingevents.data.EventRepository;
import org.launchcode.codingevents.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    UserRepository userRepository;

    private User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return authenticationController.getUserFromSession(session);
    }


    @GetMapping
    public String displayEvents(Model model) {


        model.addAttribute("searchForm", new SearchForm());

            model.addAttribute("title", "Events");
            model.addAttribute("events", eventRepository.findAll());

        return "events/index";
    }

    @GetMapping("create")
    public String renderCreateEvent(HttpServletRequest request, Model model) {

        User user = getCurrentUser(request);

        model.addAttribute("title", "Create Event");
        model.addAttribute(new Event());
        model.addAttribute("searchForm", new SearchForm());
        model.addAttribute("types",EventType.values());
        model.addAttribute("user", userRepository.findById(user.getId()));
        return "events/create";
    }

    @PostMapping("create")
    public String processCreateEventForm(@Valid Event newEvent, Errors errors, Model model, HttpServletRequest request)  {

        User user = getCurrentUser(request);
        model.addAttribute("searchForm", new SearchForm());

        if(errors.hasErrors()) {
            model.addAttribute("title", "Create Event");
            model.addAttribute("user", userRepository.findById(user.getId()));
            return "events/create";
        }


        newEvent.setUser(user);

        eventRepository.save(newEvent);
        return "redirect:";
    }

    @GetMapping("delete")
    public String displayDeleteEventForm(Model model, HttpServletRequest request) {

        User user = getCurrentUser(request);
        model.addAttribute("searchForm", new SearchForm());

        model.addAttribute("title", "Delete Events");
        model.addAttribute("events", eventRepository.findAllByUserId(user.getId()));
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

    @GetMapping("detail")
    public String displayEventDetail(@RequestParam Integer eventId, Model model, HttpServletRequest request) {

        User user = getCurrentUser(request);
        model.addAttribute("searchForm", new SearchForm());

        Optional<Event> result = eventRepository.findById(eventId);
        if(result.isEmpty()) {
            model.addAttribute("title", "Invalid Event ID");
        } else {
            Event event = result.get();
            if (event.getUser().getId() == user.getId())
            model.addAttribute("title", event.getEventName() + " Detail");
            model.addAttribute("user", userRepository.findById(user.getId()));
            model.addAttribute("event", event);
        }

        return "events/detail";
    }


    @PostMapping("edit")
    public String processEditForm(@ModelAttribute @Valid Event eventToBeEdited, Model model) {
        // Retrieve the existing event from the database
        Optional<Event> existingEventOptional = eventRepository.findById(eventToBeEdited.getId());
        model.addAttribute("searchForm", new SearchForm());

        if (existingEventOptional.isPresent()) {
            Event existingEvent = existingEventOptional.get();

            // Update the properties of the existing event with the edited values
            existingEvent.setEventName(eventToBeEdited.getEventName());
            existingEvent.setLocation(eventToBeEdited.getLocation());
            existingEvent.setNumberOfAttendees(eventToBeEdited.getNumberOfAttendees());
            existingEvent.setType(eventToBeEdited.getType());
            // Update other properties as needed

            // Access the existing EventDetails
            EventDetails existingEventDetails = existingEvent.getEventDetails();

            // Update the properties of the existing EventDetails with the edited values
            existingEventDetails.setDescription(eventToBeEdited.getEventDetails().getDescription());
            existingEventDetails.setContactEmail(eventToBeEdited.getEventDetails().getContactEmail());

            // Save the updated event back to the database
            eventRepository.save(existingEvent);

            // Redirect to the detail page of the edited event
            return "redirect:/events/detail?eventId=" + existingEvent.getId();
        }

        return "redirect:";
    }


    @GetMapping("edit/{eventId}")
    public String displayEditForm(Model model, @PathVariable int eventId, HttpServletRequest request ) {

        User user = getCurrentUser(request);
        model.addAttribute("searchForm", new SearchForm());

        Optional<Event> result = eventRepository.findById(eventId);

        if (result.isPresent()) {

            Event event = result.get();
            if(event.getUser().getId() == user.getId()) {
            model.addAttribute("event", event);
            model.addAttribute("user", userRepository.findById(user.getId()));
            model.addAttribute("types",EventType.values());
                return "events/edit";
            } else {
                return "/";
            }
        }
        return "redirect:";
    }

    @GetMapping("join/{eventId}")
    public String joinEvent(Model model, @PathVariable int eventId, HttpServletRequest request ){

        System.out.println(eventId);
        return "redirect:";
    }



}

