package suho.pofol.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import suho.pofol.domain.chat.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query("SELECT cp.conversation.conversationId " +
            "FROM ConversationParticipant cp " +
            "WHERE cp.user.userId = :userId1 " +
            "AND cp.conversation IN (" +
            "  SELECT cp2.conversation " +
            "  FROM ConversationParticipant cp2 " +
            "  WHERE cp2.user.userId = :userId2" +
            ")")
    Long findConversationIdByParticipants(@Param("userId1") int userId1, @Param("userId2") int userId2);
}
