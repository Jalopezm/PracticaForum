package com.liceu.PracticaForum.service;

import com.liceu.PracticaForum.model.Category;
import com.liceu.PracticaForum.model.Topic;
import com.liceu.PracticaForum.repo.TopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TopicService {
    @Autowired
    TopicRepo topicRepo;
    public List<Topic> getAllTopicsByCategorySlug(String categorySlug) {
        return topicRepo.getAllTopicsByCategorySlug(categorySlug);
    }

    public Map<String, Object> createTopicMap(Topic topic, Map<String, Object> topicMap, String topicId, Category category) {

        topicMap.put("views",0);
        topicMap.put("__v",0);
        topicMap.put("_id",topicId);
        topicMap.put("title",topic.getTitle());
        topicMap.put("content",topic.getContent());
        topicMap.put("category",category.getId());
        topicMap.put("user","Test");
        topicMap.put("message","");
        topicMap.put("createdAt", Timestamp.from(Instant.now()));
        topicMap.put("modifiedAt",Timestamp.from(Instant.now()));
        topicMap.put("replies",null);
        topicMap.put("numberOfReplies",null);
        topicMap.put("id",topicId);
        return topicMap;
    }

    public void saveTopic(Topic topic) {
        topicRepo.save(topic);
    }

    public Map<String, Object> createCompleteTopicMap(String topicId, Category category, Map<String, Object> userMap, Map<String, Object> topicMap) {
        topicMap.put("user",userMap);
        Topic topic  = topicRepo.getTopicById(topicId);

        Map<String,Object> categoryMap = new HashMap<>();
        categoryMap.put("description",category.getDescription());
        categoryMap.put("title",category.getTitle());
        categoryMap.put("category",category.getId());
        categoryMap.put("color",category.getColor());
        categoryMap.put("moderators", new ArrayList<>());
        categoryMap.put("slug",category.getSlug());
        categoryMap.put("__v",0);
        categoryMap.put("_id",category.getId());

        topicMap.put("createdAt", Timestamp.from(Instant.now()));
        topicMap.put("modifiedAt",Timestamp.from(Instant.now()));
        topicMap.put("replies",new ArrayList<>());
        topicMap.put("numberOfReplies",null);
        topicMap.put("content",topic.getContent());
        topicMap.put("title",topic.getTitle());
        topicMap.put("id",topicId);
        topicMap.put("category",categoryMap);
        return topicMap;
    }

    public Topic getTopicById(String topicId) {
        return topicRepo.getTopicById(topicId);
    }
}
