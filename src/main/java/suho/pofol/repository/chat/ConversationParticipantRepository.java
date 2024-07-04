package suho.pofol.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import suho.pofol.domain.chat.ConversationParticipant;

public interface ConversationParticipantRepository extends JpaRepository<ConversationParticipant, Long> {
}
