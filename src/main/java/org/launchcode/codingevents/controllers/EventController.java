package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.controllers.models.Event;
import org.launchcode.codingevents.data.EventData;

import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String createEvent(@RequestParam String eventName, String eventDescriptions) {
        EventData.addEvent((new Event(eventName, eventDescriptions)));
        return "redirect:";
    }

    @GetMapping("delete")
    public String dusplayDeleteEventForm(Model model) {

        model.addAttribute("title", "Detele Events");
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
}
