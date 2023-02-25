package com.liceu.PracticaForum.controller;

import com.liceu.PracticaForum.form.TopicForm;
import com.liceu.PracticaForum.model.Topic;
import com.liceu.PracticaForum.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TopicController {
    @Autowired
    TopicService topicService;

    @GetMapping("/categories/{categorySlug}/topics")
    @CrossOrigin(origins = "http://localhost:8080")
    public List<Topic> categorySlug(@PathVariable String categorySlug){
        List<Topic> topicsList = topicService.getAllTopicsByCategorySlug(categorySlug);
        return topicsList;
    }
    @PostMapping("/topics")
    @CrossOrigin(origins = "http://localhost:8080")
    public Topic createTopic(@RequestBody TopicForm topicForm){
        Topic topic = new Topic(topicForm.getTitle(),topicForm.getCategory(),topicForm.getContent());
        topicService.saveTopic(topic);
        return topic;
    }
    @GetMapping("/topics/{topicId}")
    @CrossOrigin(origins = "http://localhost:8080")
    public Map<String, Object> categorySlug(@RequestBody TopicForm topicForm,@PathVariable String topicId){
        Map<String,Object> topicMap = new HashMap<>();
        topicMap = topicService.createTopicMap(topicForm,topicMap,topicId);

        return topicMap;
    }
}
