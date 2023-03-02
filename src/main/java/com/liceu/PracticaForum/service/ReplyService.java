package com.liceu.PracticaForum.service;

import com.liceu.PracticaForum.model.Reply;
import com.liceu.PracticaForum.model.Topic;
import com.liceu.PracticaForum.repo.ReplyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReplyService {
    @Autowired
    ReplyRepo replyRepo;
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
            replyMap.put("createdAt", topic.getCreatedAt());
            replyMap.put("modifiedAt", topic.getModifiedAt());
            replyList.add(replyMap);
        }
        return replyList;
    }
}

