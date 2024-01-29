package org.launchcode.codingevents.data;

import org.launchcode.codingevents.controllers.models.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {
    List<Event> findAllByUserId(int userId);
    List<Event> findAllByEventName(String eventName);
    List<Event> findAllByLocation(String location);

}
