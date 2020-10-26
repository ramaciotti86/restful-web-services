package com.in28minutes.rest.webservices.restfulwebservices.postUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostUserRepository extends JpaRepository<PostUser, Integer> {
}