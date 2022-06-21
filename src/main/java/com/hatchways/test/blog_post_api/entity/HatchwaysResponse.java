package com.hatchways.test.blog_post_api.entity;

import lombok.Data;

import java.util.List;

@Data
public class HatchwaysResponse {
    private List<HatchwaysPost> posts;
}
