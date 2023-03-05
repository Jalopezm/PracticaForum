package com.liceu.PracticaForum.controller;

import com.liceu.PracticaForum.form.TopicForm;
import com.liceu.PracticaForum.model.Category;
import com.liceu.PracticaForum.model.Topic;
import com.liceu.PracticaForum.model.User;
import com.liceu.PracticaForum.service.CategoryService;
import com.liceu.PracticaForum.service.TopicService;
import com.liceu.PracticaForum.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TopicController {
    @Autowired
    TopicService topicService;
    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories/{categorySlug}/topics")
    @CrossOrigin(origins = "http://localhost:8080")
    public List<Object> categorySlug(@PathVariable String categorySlug) {
        List<Topic> topicsList = topicService.getAllTopicsByCategorySlug(categorySlug);
        Category category = categoryService.getCategoryBySlug(categorySlug);


        return topicService.createStartTopicMap(topicsList, category);
    }

    @PostMapping("/topics")
    @CrossOrigin(origins = "http://localhost:8080")
    public Map<String, Object> createTopic(@RequestBody TopicForm topicForm, HttpServletRequest request) {

        Category category = categoryService.getCategoryBySlug(topicForm.getCategory());

        Topic topic = topicService.newTopic(topicForm, category, request);

        topicService.saveTopic(topic);


        Map<String, Object> topicMap = new HashMap<>();
        return topicService.createTopicMap(topic, topicMap, String.valueOf(topic.getId()), category);
    }

    @GetMapping("/topics/{topicId}")
    @CrossOrigin(origins = "http://localhost:8080")
    public Map<String, Object> ShowTopic(@PathVariable String topicId) {
        Map<String, Object> topicMap = new HashMap<>();

        Topic topic = topicService.getTopicById(topicId);
        Category category = categoryService.getCategoryBySlug(topic.getCategory().getSlug());

        topicMap = topicService.createCompleteTopicMap(topicId, category, topicMap);

        return topicMap;
    }

    @PutMapping("/topics/{topicId}")
    @CrossOrigin(origins = "http://localhost:8080")
    public void updateTopic(@PathVariable String topicId, @RequestBody TopicForm topicForm) {
        topicService.updateTopic(topicId, topicForm);
    }

    @DeleteMapping("/topics/{topicId}")
    @CrossOrigin(origins = "http://localhost:8080")
    public Boolean deleteTopic(@PathVariable String topicId, HttpServletRequest request) {
        return topicService.deleteTopic(topicId, (User) userService.getUser(request));
    }
}
