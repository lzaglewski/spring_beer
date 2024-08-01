package com.spring.beer.dom.repository;

import com.spring.beer.dom.GroupMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMembershipRepository extends JpaRepository<GroupMembership, Long> {
}
