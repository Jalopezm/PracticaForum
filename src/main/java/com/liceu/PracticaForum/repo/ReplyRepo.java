package com.liceu.PracticaForum.repo;

import com.liceu.PracticaForum.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepo extends JpaRepository<Reply,Long> {
    List<Reply> getAllReplyByTopicId(String topicid);
}
