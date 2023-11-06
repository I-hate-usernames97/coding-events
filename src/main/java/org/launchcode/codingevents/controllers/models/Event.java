package org.launchcode.codingevents.controllers.models;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Event extends AbstractEntity {



    @NotBlank(message = "Name is required.")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String eventName;

    @NotBlank(message = "location is required")
    private String location;

    @Positive(message = "Number of attendees must be one or more.")
    private int numberOfAttendees;

    @ManyToOne
    @NotNull(message = "Category is required")
    private EventCategory eventCategory;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    private final List<Tag> tags = new ArrayList<>();

    @Valid
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private EventDetails eventDetails;



    public Event(String eventName, String location, int numberOfAttendees, EventCategory eventCategory, User user) {
        this.eventName = eventName;
        this.location = location;
        this.numberOfAttendees = numberOfAttendees;
        this.eventCategory = eventCategory;
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

    public EventCategory getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }

    public EventDetails getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(EventDetails eventDetails) {
        this.eventDetails = eventDetails;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return eventName ;
    }


}
