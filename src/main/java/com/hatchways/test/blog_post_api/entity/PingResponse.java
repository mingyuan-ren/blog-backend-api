package com.hatchways.test.blog_post_api.entity;

import lombok.Data;

@Data
public class PingResponse {
    private boolean success;

    public PingResponse() {
        this.success = true;
    }
}
