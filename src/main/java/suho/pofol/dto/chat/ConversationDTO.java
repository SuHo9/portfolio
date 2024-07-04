package suho.pofol.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConversationDTO {
    private Long conversationId;
    private String participantNickname;
}
