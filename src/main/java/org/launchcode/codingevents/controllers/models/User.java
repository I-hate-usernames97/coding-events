package org.launchcode.codingevents.controllers.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends AbstractEntity {


    @NotNull
    private String username;

    @NotNull
    private String pwHash;

    @OneToMany(mappedBy = "user")
    private final List<Event> events = new ArrayList<>();

    @ManyToMany(mappedBy = "attendees")
    private List<Event> eventsAttending;


    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User(String username, String password) {
        this.username = username;
        this.pwHash = encoder.encode(password);
    }

    public User() {}

    public List<Event> getEvents() {
        return events;
    }

    public String getUsername() {
        return username;
    }

    public List<Event> getEventsAttending() {
        return eventsAttending;
    }

    public void setEventsAttending(List<Event> eventsAttending) {
        this.eventsAttending = eventsAttending;
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }


}