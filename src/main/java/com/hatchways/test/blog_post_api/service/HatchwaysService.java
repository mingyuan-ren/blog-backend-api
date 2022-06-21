package com.hatchways.test.blog_post_api.service;

import com.hatchways.test.blog_post_api.entity.HatchwaysResponse;

import java.io.IOException;

public interface HatchwaysService {
    HatchwaysResponse getPosts(String tag) throws IOException;
}
