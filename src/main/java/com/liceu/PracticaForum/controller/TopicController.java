package com.liceu.PracticaForum.controller;

import com.liceu.PracticaForum.model.Category;
import com.liceu.PracticaForum.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopicController {
    @Autowired
    TopicService topicService;

//    @GetMapping("/categories/{categorySlug}/topics")
//    @CrossOrigin(origins = "http://localhost:8080")
//    public Category categorySlug(@PathVariable String categorySlug){
//
//        return;
//    }
}
