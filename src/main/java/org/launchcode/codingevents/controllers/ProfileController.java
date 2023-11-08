package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.controllers.models.User;
import org.launchcode.codingevents.data.EventRepository;
import org.launchcode.codingevents.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("profile")
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private EventRepository eventRepository;

    private User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return authenticationController.getUserFromSession(session);
    }

    @GetMapping
    public String displayUserEvents(HttpServletRequest request, Model model) {

        User user = getCurrentUser(request);

        model.addAttribute("title", user.getUsername());
        model.addAttribute("user", userRepository.findById(user.getId()));
        model.addAttribute("events", eventRepository.findAllByUserId(user.getId()));

        return "profile/index";
    }


}
