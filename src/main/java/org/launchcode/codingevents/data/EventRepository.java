package org.launchcode.codingevents.data;

import org.launchcode.codingevents.controllers.models.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {


}
