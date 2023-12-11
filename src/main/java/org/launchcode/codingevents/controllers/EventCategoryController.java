package org.launchcode.codingevents.controllers;


import org.launchcode.codingevents.controllers.models.EventCategory;
import org.launchcode.codingevents.controllers.models.SearchForm;
import org.launchcode.codingevents.data.EventCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("eventCategories")
public class EventCategoryController {

    @Autowired
    private EventCategoryRepository eventCategoryRepository;


    @GetMapping
    public String displayAllEventsCategory(Model model) {

        model.addAttribute("title", "All Categories");
        model.addAttribute("categories",eventCategoryRepository.findAll());
        model.addAttribute("searchForm", new SearchForm());


        return "eventCategories/index";
    }

    @GetMapping("create")
    public String renderCreateEventCategoryForm(Model model){

        model.addAttribute("title", "All Categories");
        model.addAttribute("eventCategory", new EventCategory());
        model.addAttribute("searchForm", new SearchForm());

        return "eventCategories/create";
    }

    @PostMapping("create")
    public String processCreateEventCategoryForm(@ModelAttribute @Valid EventCategory newEventeCategorie,
                                                 Errors errors,
                                                 Model model) {
        model.addAttribute("searchForm", new SearchForm());

        if (errors.hasErrors()){
            model.addAttribute("title", "All Categories");
            return "eventCategories/create";
        }
        eventCategoryRepository.save(newEventeCategorie);

        return "redirect:.";
    }

    @GetMapping("delete")
    public String displayDeleteEventCategoryForm(Model model) {

        model.addAttribute("title", "Delete Events");
        model.addAttribute("searchForm", new SearchForm());

        model.addAttribute("categories", eventCategoryRepository.findAll());
        return "eventCategories/delete";
    }

    @PostMapping("delete")
    public String processDeleteEventCategory(@RequestParam(required = false) int[] categoryIds) {


        if (categoryIds != null) {
            for (int id : categoryIds) {
                eventCategoryRepository.deleteById(id);
            }
        }
        return "redirect:";
    }

}
