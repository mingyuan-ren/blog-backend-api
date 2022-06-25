package com.hatchways.test.blog_post_api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HatchwaysResponse {
    private List<HatchwaysPost> posts;
}
