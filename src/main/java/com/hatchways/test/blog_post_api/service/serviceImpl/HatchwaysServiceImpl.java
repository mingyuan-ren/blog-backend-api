package com.hatchways.test.blog_post_api.service.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hatchways.test.blog_post_api.entity.HatchwaysResponse;
import com.hatchways.test.blog_post_api.service.HatchwaysService;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class HatchwaysServiceImpl implements HatchwaysService {
    @Override
    public HatchwaysResponse getPosts(String tag) throws IOException {

        OkHttpClient okHttpClient = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.hatchways.io/assessment/blog/posts").newBuilder();
        urlBuilder.addQueryParameter("tag",tag);
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        ResponseBody responseBody = call.execute().body();

        ObjectMapper objectMapper = new ObjectMapper();
        HatchwaysResponse hatchwaysResponse = objectMapper.readValue(responseBody.string(), HatchwaysResponse.class);

        return hatchwaysResponse;
    }


}
