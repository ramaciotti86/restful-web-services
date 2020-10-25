package com.in28minutes.rest.webservices.restfulwebservices.user;

import com.in28minutes.rest.webservices.restfulwebservices.post.PostResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserResource {

    @Autowired
    private UserDaoService service;

    @GetMapping(path = "/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    @GetMapping(path = "/users/{id}")
    public User retrieveOneUser(@PathVariable int id) {
        User user = service.findOne(id);
        if (user==null){
            throw new UserNotFoundException("id: " + id);
        }
        return user;
    }

    @GetMapping(path = "/users-post/{id}")
    public EntityModel<User> retrieveUserAndPosts(@PathVariable int id) {
        User user = service.findOne(id);
        if (user==null){
            throw new UserNotFoundException("id: " + id);
        }

        //HATEOAS
        EntityModel<User> resource = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(PostResource.class).retrieveAllPostsFromUser(id));
        resource.add(linkTo.withRel("all-posts"));

        return resource;
    }

    @PostMapping(path = "/users")
    public ResponseEntity createUser(@Valid @RequestBody User user) {
        if (user.getName() == "" || user.getBirthDate() == null){
            throw new UserNameBirthEmptyException("Name or Birthday Date cannot be empty");
        }
        User savedUsed = service.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUsed.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(path = "/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = service.deleteByOne(id);
        if (user==null){
            throw new UserNotFoundException("id: " + id);
        }
    }
}
