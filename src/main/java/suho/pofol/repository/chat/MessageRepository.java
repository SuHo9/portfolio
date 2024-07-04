package suho.pofol.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import suho.pofol.domain.chat.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
//    List<Message> findByConversationId(Long conversationId);
    List<Message> findByConversationConversationId(Long conversationId);
}
