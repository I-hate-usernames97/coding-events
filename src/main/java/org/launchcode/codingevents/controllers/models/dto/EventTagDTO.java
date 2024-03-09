package org.launchcode.codingevents.controllers.models.dto;

import org.launchcode.codingevents.controllers.models.Event;

import javax.validation.constraints.NotNull;

public class EventTagDTO {

    @NotNull
    private Event event;


    public EventTagDTO() {}

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }


}
