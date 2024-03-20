package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.controllers.models.Event;
import org.launchcode.codingevents.controllers.models.SearchForm;
import org.launchcode.codingevents.data.EventRepository;
import org.launchcode.codingevents.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("search")
public class SerachController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping
    public String displaySearchResults(@ModelAttribute @Valid SearchForm searchQuery, Model model) {
        model.addAttribute("title", "Search Results");

        if (StringUtils.isBlank(searchQuery.getQuery())) {
            model.addAttribute("title", "Invalid Search");
            return "search/index";
        }

        List<Event> events = eventRepository.findAllByQuery(searchQuery.getQuery());

        if (events.isEmpty()) {
            model.addAttribute("title", "No Results Found");
        } else {
            model.addAttribute("events", events);
        }

        return "search/index";
    }

}
