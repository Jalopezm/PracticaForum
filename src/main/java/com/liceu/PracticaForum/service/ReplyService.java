package com.liceu.PracticaForum.service;

import com.liceu.PracticaForum.form.ReplyForm;
import com.liceu.PracticaForum.model.Reply;
import com.liceu.PracticaForum.model.Topic;
import com.liceu.PracticaForum.model.User;
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
public class ReplyService {
    @Autowired
    ReplyRepo replyRepo;
    @Autowired
    TopicRepo topicRepo;
    @Autowired
    UserService userService;

    public List<Reply> getAllReplyByTopicID(String topicid) {
        return replyRepo.getAllReplyByTopicId(topicid);
    }

    public List<Object> createStartReplyMap(List<Reply> replysList, Topic topic) {
        List<Object> replyList = new ArrayList<>();
        for (Reply reply : replysList) {
            Map<String, Object> replyMap = new HashMap<>();
            Map<String, Object> userMap = new HashMap<>();
            Map<String, Object> permissionMap = userService.getRolePermission(topic.getUser().getRole());
            userMap = userService.createUserMap(topic.getUser(), permissionMap, userMap);
            replyMap.put("topic", topic.getId());
            replyMap.put("_id", reply.getId());
            replyMap.put("id", reply.getId());
            replyMap.put("content", reply.getContent());
            replyMap.put("user", userMap);
            replyMap.put("createdAt", reply.getCreatedAt());
            replyMap.put("modifiedAt", reply.getModifiedAt());
            replyList.add(replyMap);
        }
        return replyList;
    }

    public void newReply(Reply reply) {
        replyRepo.save(reply);
    }

    public Map<String, Object> createReplyMap(Topic topic, Reply reply, Map<String, Object> replyMap) {
        replyMap.put("views", 0);
        replyMap.put("__v", 0);
        replyMap.put("topicId",topic.getId());
        replyMap.put("_id", reply.getId());
        replyMap.put("content", reply.getContent());
        replyMap.put("reply", reply.getId());
        replyMap.put("user", reply.getUser());
        replyMap.put("message", "");
        replyMap.put("createdAt",reply.getCreatedAt());
        replyMap.put("modifiedAt", reply.getModifiedAt());
        replyMap.put("id", reply.getId());
        return  replyMap;
    }

    public void updateReply(String topicId, String content, String replyId) {
        Topic topic = topicRepo.getTopicById(topicId);
        Reply reply = replyRepo.getReplyById(replyId);
        reply.setModifiedAt(String.valueOf(Timestamp.from(Instant.now())));
        replyRepo.updateReply(topic,content,reply.getModifiedAt());
    }

    public Reply getReplyById(String replyId) {
        return replyRepo.getReplyById(replyId);
    }

    public boolean deleteReplies(String replyId, User user) {
        Reply reply = replyRepo.findById(Long.valueOf(replyId)).orElseGet(null);
        if (user.getRole().equals("admin") || user.getId().equals(reply.getUser().getId())) {
            try {
                replyRepo.deleteById(Long.valueOf(replyId));
                return true;
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        }
        return false;
    }

    public Reply createReply(ReplyForm replyForm, HttpServletRequest request, Topic topic) {
        Reply reply = new Reply();
        reply.setContent(replyForm.getContent());
        reply.setCreatedAt(String.valueOf(Timestamp.from(Instant.now())));
        reply.setModifiedAt(String.valueOf(Timestamp.from(Instant.now())));
        reply.setUser((User) userService.getUser(request));
        reply.setTopic(topic);
        return reply;
    }
}

