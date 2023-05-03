package org.launchcode.codingevents.controllers.models;

import java.util.Objects;

public class Event {

    private int id;
    private static int nextId = 1;

    private String name;
    private String descriptions;

    public Event(String name, String descriptions) {
        this.name = name;
        this.descriptions = descriptions;
        this.id = nextId;
        nextId ++;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name + descriptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return getId() == event.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
