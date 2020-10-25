package com.in28minutes.rest.webservices.restfulwebservices.post;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class PostDaoService {

    private static List<Post> posts = new ArrayList<>();

    private static int countPost = 6;

    static {
        posts.add(new Post(1, 1, "Post 1.1"));
        posts.add(new Post(2, 1, "Post 1.2"));
        posts.add(new Post(3, 2, "Post 2.1"));
        posts.add(new Post(4, 2, "Post 2.2"));
        posts.add(new Post(5, 3, "Post 3.1"));
        posts.add(new Post(6, 3, "Post 3.2"));
    }

    public List<Post> findAllPostsFromUser(int userId){
        Predicate<Post> byUserId = post ->  post.getUserId() == userId;
        List resultList = posts.stream().filter(byUserId).collect(Collectors.toList());

        return resultList;
    }

    public Post findPost(int userId, int postId){
        for (Post post: posts) {
            if(post.getUserId() == userId && post.getId() == postId){
                return post;
            }
        }
        return null;
    }

    public Post save(Post post){
        if (post.getId() == null){
            post.setId(++countPost);
        }
        posts.add(post);
        return post;
    }

}
