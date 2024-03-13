package org.launchcode.codingevents.data;

import org.launchcode.codingevents.controllers.models.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {
    List<Event> findAllByUserId(int userId);
    @Query("SELECT e FROM Event e JOIN e.user u WHERE LOWER(e.eventName) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(e.location) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(u.username) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Event> findAllByQuery(@Param("query") String query);

}
