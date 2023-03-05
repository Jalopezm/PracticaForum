package com.liceu.PracticaForum.repo;

import com.liceu.PracticaForum.model.Reply;
import com.liceu.PracticaForum.model.Topic;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepo extends JpaRepository<Reply,Long> {
    List<Reply> getAllReplyByTopicId(String topicid);
    @Modifying
    @Transactional
    @Query("UPDATE Reply r SET r.content=:content,r.modifiedAt=:modifiedAt WHERE r.topic=:topic")
    void updateReply(Topic topic, String content, String modifiedAt);

    Reply getReplyById(String replyId);
}
