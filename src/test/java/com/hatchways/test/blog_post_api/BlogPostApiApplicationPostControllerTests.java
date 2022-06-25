package com.hatchways.test.blog_post_api;

import com.hatchways.test.blog_post_api.controller.PostController;
import com.hatchways.test.blog_post_api.entity.HatchwaysPost;
import com.hatchways.test.blog_post_api.entity.HatchwaysResponse;
import com.hatchways.test.blog_post_api.service.HatchwaysService;
import com.hatchways.test.blog_post_api.utils.DirectionEnum;
import com.hatchways.test.blog_post_api.utils.SortByEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@SpringBootTest
class BlogPostApiApplicationPostControllerTests {

    @MockBean
    private HatchwaysService mockHatchwaysService;

    private PostController postController;

    @BeforeEach
    private void setup() throws IOException {

        postController = new PostController(mockHatchwaysService);

        HatchwaysPost scienceAndHealthPost0 = new HatchwaysPost(7, "Test Author 1", 7, 499, 0.93, 90000, new ArrayList<>(Arrays.asList("science", "health")));
        HatchwaysPost sciencePost1 = new HatchwaysPost(19, "Test Author 2", 8, 799, 0.91, 50000, new ArrayList<>(Arrays.asList("science")));
        HatchwaysPost sciencePost2 = new HatchwaysPost(21, "Test Author 3", 1, 399, 0.81, 80000, new ArrayList<>(Arrays.asList("science", "startups")));
        HatchwaysPost healthPost1 = new HatchwaysPost(34, "Test Author 4", 11, 699, 0.24, 60000, new ArrayList<>(Arrays.asList("health")));
        HatchwaysPost healthPost2 = new HatchwaysPost(39, "Test Author 5", 12, 299, 0.55, 30000, new ArrayList<>(Arrays.asList("health", "tech")));

        List<HatchwaysPost> sciencePostList = new ArrayList<>();
        sciencePostList.add(scienceAndHealthPost0);
        sciencePostList.add(sciencePost1);
        sciencePostList.add(sciencePost2);

        List<HatchwaysPost> healthPostList = new ArrayList<>();
        healthPostList.add(scienceAndHealthPost0);
        healthPostList.add(healthPost1);
        healthPostList.add(healthPost2);

        when(mockHatchwaysService.getPosts("science")).thenReturn(new HatchwaysResponse(sciencePostList));
        when(mockHatchwaysService.getPosts("health")).thenReturn(new HatchwaysResponse(healthPostList));
    }

    @Test
    void contextLoads() {
        Assertions.assertNotNull(postController);
    }

    @Test
    public void removeDuplicatedPostsTest() throws Exception {

        //removed duplication test
        List<HatchwaysPost> sciencePosts = this.postController.getPosts("science", SortByEnum.LIKES, DirectionEnum.ASC).getPosts();
        List<HatchwaysPost> healthPosts = this.postController.getPosts("health", SortByEnum.LIKES, DirectionEnum.ASC).getPosts();
        List<HatchwaysPost> scienceAndHealthPosts = this.postController.getPosts("health,science", SortByEnum.LIKES, DirectionEnum.ASC).getPosts();
        Set<HatchwaysPost> set = new HashSet<>();
        set.addAll(sciencePosts);
        set.addAll(healthPosts);

        Assertions.assertEquals(set.size(), scienceAndHealthPosts.size());
    }

    @Test
    public void sortByTest() throws Exception {
        //sort by test
        List<HatchwaysPost> posts = this.postController.getPosts("health", SortByEnum.LIKES, DirectionEnum.ASC).getPosts();
        List<Long> rawLikes = posts.stream().map(x -> x.getLikes()).collect(Collectors.toList());
        List<Long> likes = posts.stream().map(x -> x.getLikes()).collect(Collectors.toList());
        likes.sort(Comparator.naturalOrder());
        Assertions.assertIterableEquals(likes, rawLikes);
    }

    @Test
    public void directionTest() throws Exception {
        //direction test
        List<HatchwaysPost> postsDesc = this.postController.getPosts("science", SortByEnum.POPULARITY, DirectionEnum.DESC).getPosts();
        List<HatchwaysPost> postsAsc = this.postController.getPosts("science", SortByEnum.POPULARITY, DirectionEnum.ASC).getPosts();
        Collections.reverse(postsDesc);
        Assertions.assertIterableEquals(postsAsc, postsDesc);
    }
}
