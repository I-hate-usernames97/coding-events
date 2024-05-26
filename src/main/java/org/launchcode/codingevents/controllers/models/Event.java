package org.launchcode.codingevents.controllers.models;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Event extends AbstractEntity {



    @NotBlank(message = "Name is required.")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String eventName;

    @NotBlank(message = "location is required")
    private String location;


    private int numberOfAttendees;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    private EventType type;

    @Valid
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private EventDetails eventDetails;

    @ManyToMany
    @JoinTable(
            name = "event_attendees",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> attendees = new ArrayList<>();

    public Event(String eventName, String location, EventType type, int numberOfAttendees, User user) {
        this.eventName = eventName;
        this.location = location;
        this.numberOfAttendees = numberOfAttendees;
        this.type = type;
        this.user = user;
    }

    public Event() {
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String name) {
        this.eventName = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getNumberOfAttendees() {
        return numberOfAttendees;
    }

    public void setNumberOfAttendees(int numberOfAttendees) {
        this.numberOfAttendees = numberOfAttendees;
    }


    public EventDetails getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(EventDetails eventDetails) {
        this.eventDetails = eventDetails;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<User> attendees) {
        this.attendees = attendees;
    }

    @Override
    public String toString() {
        return eventName ;
    }


}
