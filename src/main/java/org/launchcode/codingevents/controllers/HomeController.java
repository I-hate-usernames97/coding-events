package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.controllers.models.SearchForm;
import org.launchcode.codingevents.data.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Chris Bay
 */
@Controller
public class HomeController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping
    public String index(Model model) {

        model.addAttribute("title", "Code Events");
        model.addAttribute("events", eventRepository.findAll());
        model.addAttribute("searchForm", new SearchForm());

        return "index";
    }

}
