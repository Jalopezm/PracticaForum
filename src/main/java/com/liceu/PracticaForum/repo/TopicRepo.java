package com.liceu.PracticaForum.repo;

import com.liceu.PracticaForum.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepo extends JpaRepository<Topic,Long> {
//    List<Topic> getAllTopicsByCategorySlug(String categorySlug);
}
