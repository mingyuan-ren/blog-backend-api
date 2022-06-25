package com.hatchways.test.blog_post_api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HatchwaysPost {
    private long id;
    private String author;
    private long authorId;
    private long likes;
    private double popularity;
    private long reads;
    private List<String> tags;
}
