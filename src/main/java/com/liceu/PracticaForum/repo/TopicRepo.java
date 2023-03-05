package com.liceu.PracticaForum.repo;

import com.liceu.PracticaForum.model.Category;
import com.liceu.PracticaForum.model.Topic;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface TopicRepo extends JpaRepository<Topic,Long> {

    List<Topic> getAllTopicsByCategorySlug(String categorySlug);
    Topic getTopicById(String topicId);

    @Modifying
    @Transactional
    @Query("UPDATE Topic t SET t.views=:views WHERE t.id=:id")
    void updateViews( int views, long id);
    @Modifying
    @Transactional
    @Query("UPDATE Topic t SET t.category=:category,t.title=:title,t.content=:content,t.modifiedAt=:modifiedAt WHERE t.id=:id")
    void updateTopic(Category category, String title, String content, long id, String modifiedAt);
}
