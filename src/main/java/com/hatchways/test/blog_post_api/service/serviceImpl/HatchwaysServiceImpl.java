package com.hatchways.test.blog_post_api.service.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.hatchways.test.blog_post_api.entity.HatchwaysResponse;
import com.hatchways.test.blog_post_api.service.HatchwaysService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class HatchwaysServiceImpl implements HatchwaysService {

    private static final String URL = "https://api.hatchways.io/assessment/blog/posts";
    private final Cache<String, String> cache;

    // Reuse the same client for every request
    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;

    @Override
    public HatchwaysResponse getPosts(String tag) throws IOException {
        String jsonResponse = cache.getIfPresent(tag);//TODO: cache
        // cache miss
        if (jsonResponse == null) {
            Request request = makeRequest(tag);
            Call call = okHttpClient.newCall(request);
            jsonResponse = call.execute().body().string();
            if (Strings.isNotBlank(jsonResponse))   // only cache response when it's not blank/empty
                cache.put(tag, jsonResponse);   // put it in cache, next time we can retrieve it from cache directly
        }

        HatchwaysResponse hatchwaysResponse = objectMapper.readValue(jsonResponse, HatchwaysResponse.class);
        return hatchwaysResponse;
    }

    private Request makeRequest(String tag) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(URL).newBuilder();
        urlBuilder.addQueryParameter("tag", tag);
        String url = urlBuilder.build().toString();
        return new Request.Builder().url(url).build();
    }


}
