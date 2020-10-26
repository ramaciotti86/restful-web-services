package com.in28minutes.rest.webservices.restfulwebservices.user;

import com.in28minutes.rest.webservices.restfulwebservices.post.Post;
import com.in28minutes.rest.webservices.restfulwebservices.post.PostResource;
import com.in28minutes.rest.webservices.restfulwebservices.postUser.PostUser;
import com.in28minutes.rest.webservices.restfulwebservices.postUser.PostUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJPAResource {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostUserRepository postUserRepository;

    @GetMapping(path = "/jpa/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/jpa/users/{id}")
    public Optional<User> retrieveOneUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()){
            throw new UserNotFoundException("id: " + id);
        }
        return user;
    }

    @GetMapping(path = "/jpa/users-post/{id}")
    public EntityModel<User> retrieveUserAndPosts(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()){
            throw new UserNotFoundException("id: " + id);
        }

        //HATEOAS
        EntityModel<User> resource = EntityModel.of(user.get());
        WebMvcLinkBuilder linkTo = linkTo(methodOn(PostResource.class).retrieveAllPostsFromUser(id));
        resource.add(linkTo.withRel("all-posts"));

        return resource;
    }

    @PostMapping(path = "/jpa/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        if (StringUtils.isEmpty(user.getName()) || StringUtils.isEmpty(user.getBirthDate())){
            throw new UserNameBirthEmptyException("Name or Birthday Date cannot be empty");
        }
        User savedUsed = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUsed.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(path = "/jpa/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<PostUser> retrieveAllPostsFromUser(@PathVariable int id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(!userOptional.isPresent())
            throw new UserNotFoundException("id: " + id);

        return userOptional.get().getPosts();
    }

    @PostMapping(path = "/jpa/users/{id}/posts")
    public ResponseEntity<PostUser> createPostUser(@PathVariable int id, @RequestBody PostUser postUser) {
        Optional<User> userOptional = userRepository.findById(id);
        if(!userOptional.isPresent())
            throw new UserNotFoundException("id: " + id);

        User user = userOptional.get();
        postUser.setUser(user);

        PostUser savedPostUser = postUserRepository.save(postUser);

        //This location does not exist as I did not create it
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPostUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

}
