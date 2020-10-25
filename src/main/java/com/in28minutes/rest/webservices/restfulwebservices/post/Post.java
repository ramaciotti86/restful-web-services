package com.in28minutes.rest.webservices.restfulwebservices.post;

public class Post {

    private Integer id;
    private Integer userId;
    private String post;

    public Post(Integer id, int userId, String post) {
        this.id = id;
        this.userId = userId;
        this.post = post;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", userId=" + userId +
                ", post='" + post + '\'' +
                '}';
    }
}
