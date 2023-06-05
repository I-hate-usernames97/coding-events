package org.launchcode.codingevents.controllers;



import org.launchcode.codingevents.controllers.models.Event;

import org.launchcode.codingevents.controllers.models.EventCategory;
import org.launchcode.codingevents.controllers.models.Tag;
import org.launchcode.codingevents.controllers.models.dto.EventTagDTO;
import org.launchcode.codingevents.data.EventCategoryRepository;
import org.launchcode.codingevents.data.EventRepository;
import org.launchcode.codingevents.data.TagRepository;
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

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping
    public String displayEvents(@RequestParam(required = false) Integer categoryId, Integer tagId, Model model) {

        if (categoryId == null && tagId == null) {
            model.addAttribute("title", "All Events");
            model.addAttribute("events", eventRepository.findAll());
        } else if (categoryId != null)  {
            Optional<EventCategory> result = eventCategoryRepository.findById(categoryId);
            if (result.isEmpty()) {
                model.addAttribute("title", "Invalid Category ID: " + categoryId);
            } else {
                EventCategory category = result.get();
                model.addAttribute("title", "Events in category: " + category.getName());
                model.addAttribute("events", category.getEvents());
            }
        } else {
            Optional<Tag> result = tagRepository.findById(tagId);
            if(result.isEmpty()) {
                model.addAttribute("title", "Invalid Tag ID ");
            }else {
                Tag tag = result.get();
                model.addAttribute("title", "Events in tag: " + tag.getName());
                model.addAttribute("events", tag.getEvents());
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

    @GetMapping("detail")
    public String displayEventDetail(@RequestParam Integer eventId, Model model) {

        Optional<Event> result = eventRepository.findById(eventId);
        if(result.isEmpty()) {
            model.addAttribute("title", "Invalid Event ID");
        } else {
            Event event = result.get();
            model.addAttribute("title", event.getName() + " Detail");
            model.addAttribute("event", event);
            model.addAttribute("tags", event.getTags());
        }

        return "events/detail";
    }

    @GetMapping("add-tag")
    public String displayAddTagForm(@RequestParam Integer eventId, Model model){
        Optional<Event> result = eventRepository.findById(eventId);
        if(result.isPresent()) {
            Event event = result.get();
            model.addAttribute("title", "Add Tag to: " + event.getName());
            model.addAttribute("tags", tagRepository.findAll());
            EventTagDTO eventTag = new EventTagDTO();
            eventTag.setEvent(event);
            model.addAttribute("eventTag", eventTag);
        }
        return "events/add-tag.html";
    }

    @PostMapping("add-tag")
    public String processAddTagForm(@ModelAttribute @Valid EventTagDTO eventTag,
                                    Errors errors,
                                    Model model){

        if (!errors.hasErrors()) {
            Event event = eventTag.getEvent();
            Tag tag = eventTag.getTag();
            if (!event.getTags().contains(tag)){
                event.addTag(tag);
                eventRepository.save(event);
            }
            return "redirect:detail?eventId=" + event.getId();
        }

        return "redirect:add-tag";
    }

//    @PostMapping("edit")
//    public String processEditForm(@ModelAttribute @Valid Event eventToBeEdited, EventTagDTO eventTagDTO,  Model model) {
//
//        model.addAttribute("title", eventToBeEdited.getName() + " Detail");
//        eventRepository.save(eventToBeEdited);
//
//        return "events/detail";
//    }
//
//
//    @GetMapping("edit/{eventId}")
//    public String displayEditForm(Model model, @PathVariable int eventId ) {
//
//
//        Optional<Event> result = eventRepository.findById(eventId);
//
//        if (result.isPresent()) {
//
//            Event event = result.get();
//            model.addAttribute("event", event);
//            model.addAttribute("categories", eventCategoryRepository.findAll());
//            return "events/edit";
//        }
//
//        return "redirect:";
//    }


}

