package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.controllers.models.Tag;
import org.launchcode.codingevents.data.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("tags")
public class TagController {

    @Autowired
    TagRepository tagRepository;


    @GetMapping
    public String displayAllTags(Model model) {

        model.addAttribute("title", "All Tags");
        model.addAttribute("tags",tagRepository.findAll());

        return "tags/index";
    }

    @GetMapping("create")
    public String renderCreateEventCategoryForm(Model model){

        model.addAttribute("title", "All Tags");
        model.addAttribute("tag", new Tag());

        return "tags/create";
    }

    @PostMapping("create")
    public String processCreateEventCategoryForm(@ModelAttribute @Valid Tag newTag,
                                                 Errors errors,
                                                 Model model) {
        if (errors.hasErrors()){
            model.addAttribute("title", "All Tags");
            return "tags/create";
        }
        tagRepository.save(newTag);

        return "redirect:.";
    }

    @GetMapping("delete")
    public String displayDeleteEventCategoryForm(Model model) {

        model.addAttribute("title", "Delete Events");
        model.addAttribute("tags", tagRepository.findAll());
        return "tags/delete";
    }

    @PostMapping("delete")
    public String processDeleteEventCategory(@RequestParam(required = false) int[] tagIds) {

        if (tagIds != null) {
            for (int id : tagIds) {
                tagRepository.deleteById(id);
            }
        }
        return "redirect:";
    }
}
