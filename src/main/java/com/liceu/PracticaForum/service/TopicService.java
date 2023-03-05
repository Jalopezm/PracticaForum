package com.liceu.PracticaForum.service;

import com.liceu.PracticaForum.form.TopicForm;
import com.liceu.PracticaForum.model.Category;
import com.liceu.PracticaForum.model.Reply;
import com.liceu.PracticaForum.model.Topic;
import com.liceu.PracticaForum.model.User;
import com.liceu.PracticaForum.repo.CategoryRepo;
import com.liceu.PracticaForum.repo.ReplyRepo;
import com.liceu.PracticaForum.repo.TopicRepo;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    UserService userService;
    @Autowired
    ReplyRepo replyRepo;

    public List<Topic> getAllTopicsByCategorySlug(String categorySlug) {
        return topicRepo.getAllTopicsByCategorySlug(categorySlug);
    }

    public Map<String, Object> createTopicMap(Topic topic, Map<String, Object> topicMap, String topicId, Category category) {
        List<Reply> replyList = replyRepo.getAllReplyByTopicId(topicId);
        topicMap.put("views", topic.getViews());
        topicMap.put("__v", 0);
        topicMap.put("_id", topicId);
        topicMap.put("title", topic.getTitle());
        topicMap.put("content", topic.getContent());
        topicMap.put("category", category.getId());
        topicMap.put("user", topic.getUser());
        topicMap.put("message", "");
        topicMap.put("createdAt", topic.getCreatedAt());
        topicMap.put("modifiedAt", topic.getModifiedAt());
        topicMap.put("replies", replyList);
        topicMap.put("numberOfReplies", replyList.size());
        topicMap.put("id", topicId);
        return topicMap;
    }

    public void saveTopic(Topic topic) {
        topicRepo.save(topic);
    }

    public Map<String, Object> createCompleteTopicMap(String topicId, Category category, Map<String, Object> topicMap) {
        Topic topic = topicRepo.getTopicById(topicId);
        Map<String, Object> userMap = new HashMap<>();
        Map<String, Object> permissionMap = userService.getRolePermission(topic.getUser().getRole());
        userMap = userService.createUserMap(topic.getUser(), permissionMap, userMap);
        topicRepo.updateViews(topic.getViews() + 1, topic.getId());
        topicMap.put("user", userMap);
        List<Reply> replyList = replyRepo.getAllReplyByTopicId(topicId);
        Map<String, Object> categoryMap = new HashMap<>();
        categoryMap.put("description", category.getDescription());
        categoryMap.put("title", category.getTitle());
        categoryMap.put("color", category.getColor());
        categoryMap.put("moderators", new ArrayList<>());
        categoryMap.put("slug", category.getSlug());
        categoryMap.put("__v", 0);
        categoryMap.put("_id", category.getId());

        topicMap.put("createdAt", Timestamp.from(Instant.now()));
        topicMap.put("modifiedAt", Timestamp.from(Instant.now()));
        topicMap.put("replies", replyList);
        topicMap.put("numberOfReplies", replyList.size());
        topicMap.put("content", topic.getContent());
        topicMap.put("title", topic.getTitle());
        topicMap.put("id", topicId);
        topicMap.put("_id", topicId);
        topicMap.put("category", categoryMap);
        return topicMap;
    }

    public Topic getTopicById(String topicId) {
        return topicRepo.getTopicById(topicId);
    }

    public List<Object> createStartTopicMap(List<Topic> topicsList, Category category) {
        List<Object> topicList = new ArrayList<>();

        for (Topic topic : topicsList) {
            List<Reply> replyList = replyRepo.getAllReplyByTopicId(String.valueOf(topic.getId()));
            Map<String, Object> topicMap = new HashMap<>();
            Map<String, Object> userMap = new HashMap<>();
            int views = topic.getViews();
            Map<String, Object> permissionMap = userService.getRolePermission(topic.getUser().getRole());
            userMap = userService.createUserMap(topic.getUser(), permissionMap, userMap);
            topicMap.put("category", category.getId());
            topicMap.put("views", views);
            topic.setViews(views);
            topicMap.put("_id", topic.getId());
            topicMap.put("id", topic.getId());
            topicMap.put("content", topic.getContent());
            topicMap.put("title", topic.getTitle());
            topicMap.put("replies", replyList);
            topicMap.put("numberOfReplies", replyList.size());
            topicMap.put("user", userMap);
            topicMap.put("createdAt", topic.getCreatedAt());
            topicMap.put("modifiedAt", topic.getModifiedAt());
            topicList.add(topicMap);

        }
        return topicList;
    }

    public void updateTopic(String topicId, TopicForm topicForm) {
        Category category = categoryRepo.getCategoryBySlug(topicForm.getCategory());
        Topic topic = getTopicById(topicId);
        topic.setModifiedAt(String.valueOf(Timestamp.from(Instant.now())));
        topicRepo.updateTopic(category, topicForm.getTitle(), topicForm.getContent(), Long.parseLong(topicId), topic.getModifiedAt());
    }

    public Boolean deleteTopic(String topicId, User user) {
        Topic topic = topicRepo.findById(Long.valueOf(topicId)).orElseGet(null);
        if (user.getRole().equals("admin") || user.getId().equals(topic.getUser().getId())) {
            try {
                topicRepo.deleteById(Long.valueOf(topicId));
                return true;
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        }
        return false;
    }

    public Topic newTopic(TopicForm topicForm, Category category, HttpServletRequest request) {
        Topic topic = new Topic();
        topic.setTitle(topicForm.getTitle());
        topic.setContent(topicForm.getContent());
        topic.setCreatedAt(String.valueOf(Timestamp.from(Instant.now())));
        topic.setModifiedAt(String.valueOf(Timestamp.from(Instant.now())));
        topic.setUser((User) userService.getUser(request));
        topic.setCategory(category);
        return topic;
    }
}
