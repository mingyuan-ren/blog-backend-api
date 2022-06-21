package com.hatchways.test.blog_post_api.controller;

import com.hatchways.test.blog_post_api.entity.HatchwaysPost;
import com.hatchways.test.blog_post_api.entity.PostResponse;
import com.hatchways.test.blog_post_api.service.HatchwaysService;
import com.hatchways.test.blog_post_api.utils.DirectionEnum;
import com.hatchways.test.blog_post_api.utils.SortByEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class PostController {

    private HatchwaysService hatchwaysService;

    public PostController(HatchwaysService hatchwaysService) {
        this.hatchwaysService = hatchwaysService;
    }

    @GetMapping({"/ping"})
    public ResponseEntity<String> pingService() {
        // TODO: change it to a Ping success object
        return new ResponseEntity<>("\"success\": true", HttpStatus.OK);
    }

    @GetMapping("/posts")
    public PostResponse getPosts(
            @RequestParam(value = "tags", required = true) String tags,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) SortByEnum sortBy,
            @RequestParam(value = "direction", defaultValue = "asc", required = false) DirectionEnum direction) throws IOException {
        //convert String tags to List<String> tags
        List<String> tagList = Arrays.asList(tags.split(","));

        //get posts back with each tag and put them together, remove duplication by a set
        Set<HatchwaysPost> allPosts = new HashSet<>();
        for (String oneTag : tagList) {
            allPosts.addAll(hatchwaysService.getPosts(oneTag).getPosts());
        }

        //put all posts in the set to a list, prepare for sorting
        List<HatchwaysPost> rawPosts = new ArrayList<>();
        rawPosts.addAll(allPosts);

        List<HatchwaysPost> sortedPosts = sortPosts(rawPosts, sortBy, direction);

        return new PostResponse(sortedPosts);
    }

    private List<HatchwaysPost> sortPosts(List<HatchwaysPost> rawPosts, SortByEnum sortByField, DirectionEnum direction) {
        Comparator<? super HatchwaysPost> comparator = null;

        switch (sortByField) {
            case ID:
                comparator = Comparator.comparingLong(HatchwaysPost::getId);
                break;
            case LIKES:
                comparator = Comparator.comparingLong(HatchwaysPost::getLikes);
                break;
            case READS:
                comparator = Comparator.comparingLong(HatchwaysPost::getReads);
                break;
            case POPULARITY:
                comparator = Comparator.comparingDouble(HatchwaysPost::getPopularity);
                break;
        }

        if (direction == DirectionEnum.DESC) comparator = comparator.reversed();

        rawPosts.sort(comparator);

        return rawPosts;
    }
}
