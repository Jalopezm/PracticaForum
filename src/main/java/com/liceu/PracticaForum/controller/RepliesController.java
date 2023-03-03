package com.liceu.PracticaForum.controller;

import com.liceu.PracticaForum.form.ReplyForm;
import com.liceu.PracticaForum.model.Reply;
import com.liceu.PracticaForum.model.Topic;
import com.liceu.PracticaForum.model.User;
import com.liceu.PracticaForum.service.ReplyService;
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
public class RepliesController {
    @Autowired
    ReplyService replyService;
    @Autowired
    TopicService topicService;
    @Autowired
    UserService userService;

    @GetMapping("/topics/{topicId}/replies")
    @CrossOrigin(origins = "http://localhost:8080")
    public List<Object> getReplies(@PathVariable String topicId) {
        List<Reply> replyList = replyService.getAllReplyByTopicID(topicId);
        Topic topic = topicService.getTopicById(topicId);


        return replyService.createStartReplyMap(replyList, topic);
    }

    @PostMapping("/topics/{topicId}/replies")
    @CrossOrigin(origins = "http://localhost:8080")
    public Map<String, Object> setReplies(@PathVariable String topicId, @RequestBody ReplyForm replyForm, HttpServletRequest request) {
        Topic topic = topicService.getTopicById(topicId);
        Reply reply = new Reply();
        reply.setContent(replyForm.getContent());
        reply.setCreatedAt(String.valueOf(Timestamp.from(Instant.now())));
        reply.setModifiedAt(String.valueOf(Timestamp.from(Instant.now())));
        reply.setUser((User) userService.getUser(request));
        reply.setTopic(topic);

        replyService.saveReply(reply);
        Map<String, Object> replyMap = new HashMap<>();
        return  replyService.createReplyMap(topic,reply,replyMap);

    }

}
