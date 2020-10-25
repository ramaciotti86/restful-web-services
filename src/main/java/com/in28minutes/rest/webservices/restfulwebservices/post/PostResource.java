package com.in28minutes.rest.webservices.restfulwebservices.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.List;

@RestController
public class PostResource {

    @Autowired
    private PostDaoService service;

    @GetMapping("/users/{userId}/posts")
    public List<Post> retrieveAllPostsFromUser(@PathVariable int userId) {
        return service.findAllPostsFromUser(userId);
    }

    @GetMapping("/users/{userId}/posts/{postId}")
    public Post retrieveOnePost(@PathVariable int userId, @PathVariable int postId) {
        Post post = service.findPost(userId, postId);
        if (post == null) {
            throw new PostNotFoundException(String.format("User id %s and/or PostId %s", userId, postId));
        }
        return post;
    }

    @PostMapping("/users/{userId}/posts")
    public ResponseEntity createPost(@PathVariable int userId, @RequestBody Post post) throws NoSuchMethodException {
        if (post.getUserId() == null || post.getPost() == null) {
            throw new UserIdPostEmptyException("User Id and/or post cannot be empty!");
        }
        Post savedPost = service.save(post);

        //TODO How to get the URI from another method without hard coding it?
        String methodName = "retrieveOnePost";
        Class[] clazz = new Class[2];
        clazz[0] = int.class;
        clazz[1] = int.class;
        String getPathLocation = getPathLocation(methodName, clazz);

        //Worked: URI location = ServletUriComponentsBuilder.fromUriString("/users/{userId}/posts/{postId}").buildAndExpand(post.getUserId(), post.getId()).toUri();

        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        URI location = ServletUriComponentsBuilder.fromUriString(baseUrl + getPathLocation).buildAndExpand(post.getUserId(), post.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    private String getPathLocation(String methodName, Class[] clazz) throws NoSuchMethodException {
        Method method = PostResource.class.getDeclaredMethod(methodName, clazz);
        Annotation annotation = AnnotationUtils.getAnnotation(method, method.getDeclaredAnnotations()[0].annotationType());
        GetMapping getMapping = (GetMapping) annotation;
        return getMapping.path()[0];
    }
}
