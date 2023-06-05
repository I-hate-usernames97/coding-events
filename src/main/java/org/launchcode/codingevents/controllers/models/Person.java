package org.launchcode.codingevents.controllers.models;
//
//import javax.persistence.Entity;
//import javax.persistence.ManyToMany;
//import javax.persistence.OneToMany;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//public class Person extends AbstractEntity {
//
//    private String firstName;
//
//    private String lastName;
//
//    private String email;
//
//    private String password;
//
//    @ManyToMany(mappedBy = "people")
//    private final List<Event> eventsAttending = new ArrayList<>();
//
//    @OneToMany(mappedBy = "person")
//    private final List<Event> eventsOwned = new ArrayList<>();
//
//
//    public Person(String firstName, String lastName, String email, String password) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//        this.password = password;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public List<Event> getEventsAttending() {
//        return eventsAttending;
//    }
//
//    public List<Event> getEventsOwned() {
//        return eventsOwned;
//    }
//
//    public void addEventsAttending(Event event) {
//        this.eventsAttending.add(event);
//    }
//
//    public void addEventsOwned(Event event) {
//        this.eventsOwned.add(event);
//    }
//
//}