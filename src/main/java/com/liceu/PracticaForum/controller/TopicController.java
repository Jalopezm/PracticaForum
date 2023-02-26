package com.liceu.PracticaForum.controller;

import com.google.gson.Gson;
import com.liceu.PracticaForum.form.TopicForm;
import com.liceu.PracticaForum.model.Category;
import com.liceu.PracticaForum.model.Topic;
import com.liceu.PracticaForum.service.CategoryService;
import com.liceu.PracticaForum.service.TopicService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TopicController {
    @Autowired
    TopicService topicService;
    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories/{categorySlug}/topics")
    @CrossOrigin(origins = "http://localhost:8080")
    public List<Topic> categorySlug(@PathVariable String categorySlug) {
        List<Topic> topicsList = topicService.getAllTopicsByCategorySlug(categorySlug);
        return topicsList;
    }

    @PostMapping("/topics")
    @CrossOrigin(origins = "http://localhost:8080")
    public Map<String, Object> createTopic(@RequestBody TopicForm topicForm, HttpServletRequest request) {

        Category category = categoryService.getCategoryBySlug(topicForm.getCategory());

        Topic topic = new Topic();
        topic.setTitle(topicForm.getTitle());
        topic.setContent(topicForm.getContent());


        topic.setCategory(category);

        topicService.saveTopic(topic);


        Map<String, Object> topicMap = new HashMap<>();
        topicMap = topicService.createTopicMap(topic, topicMap, String.valueOf(topic.getId()), category);

        return topicMap;
    }

    @GetMapping("/topics/{topicId}")
    @CrossOrigin(origins = "http://localhost:8080")
    public Map<String, Object> ShowTopic(@PathVariable String topicId, HttpServletRequest request) {
        Map<String, Object> topicMap = new HashMap<>();
        String payload = (String) request.getAttribute("payload");
        Gson gson = new Gson();
        Map<String, Object> userMap = gson.fromJson(payload, Map.class);
        Topic topic = topicService.getTopicById(topicId);
        Category category = categoryService.getCategoryBySlug(topic.getCategory().getSlug());

        topicMap = topicService.createCompleteTopicMap(topicId, category, userMap, topicMap);

        return topicMap;
    }

}
