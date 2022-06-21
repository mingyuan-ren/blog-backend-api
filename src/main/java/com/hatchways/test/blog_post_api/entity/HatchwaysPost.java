package com.hatchways.test.blog_post_api.entity;

import lombok.Data;

import java.util.List;

@Data
public class HatchwaysPost {
    private long id;
    private String author;
    private long authorId;
    private long likes;
    private double popularity;
    private long reads;
    private List<String> tags;
}
