package com.liceu.PracticaForum.controller;

import com.liceu.PracticaForum.form.TopicForm;
import com.liceu.PracticaForum.model.Category;
import com.liceu.PracticaForum.model.Reply;
import com.liceu.PracticaForum.model.Topic;
import com.liceu.PracticaForum.model.User;
import com.liceu.PracticaForum.repo.ReplyRepo;
import com.liceu.PracticaForum.service.ReplyService;
import com.liceu.PracticaForum.service.TopicService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class RepliesController {
    @Autowired
    ReplyService replyService;
    @Autowired
    TopicService topicService;


    @GetMapping("/topics/{topicId}/replies")
    @CrossOrigin(origins = "http://localhost:8080")
    public String topic (@PathVariable String topicId) {
        return "";
    }
    @PostMapping("/topics/{topicId}/replies")
    @CrossOrigin(origins = "http://localhost:8080")
    public List<Object> topicId(@PathVariable String topicId) {
        List<Reply> replyList = replyService.getAllReplyByTopicID(topicId);
        Topic topic = topicService.getTopicById(topicId);


        return replyService.createStartReplyMap(replyList, topic);
    }

}
