package com.spring.beer.dom.repository;

import com.spring.beer.dom.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findByHash(String hash);
}
