package com.spring.beer.dom.repository;

import com.spring.beer.dom.EventResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventResponseRepository extends JpaRepository<EventResponse, Long> {

}
