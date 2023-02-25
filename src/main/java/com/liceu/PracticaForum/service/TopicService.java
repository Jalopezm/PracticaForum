package com.liceu.PracticaForum.service;

import com.liceu.PracticaForum.form.TopicForm;
import com.liceu.PracticaForum.model.Topic;
import com.liceu.PracticaForum.repo.TopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TopicService {
    @Autowired
    TopicRepo topicRepo;
    public List<Topic> getAllTopicsByCategorySlug(String categorySlug) {
        return new ArrayList<>();
    }

    public Map<String, Object> createTopicMap(TopicForm topicForm, Map<String, Object> topicMap, String topicId) {
        topicMap.put("views",0);
        topicMap.put("_id",topicId);
        topicMap.put("title",topicForm.getTitle());
        topicMap.put("content",topicForm.getContent());
        topicMap.put("category",topicForm.getCategory());
        topicMap.put("user","");
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
}
