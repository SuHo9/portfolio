package suho.pofol.dto.chat;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageDTO {
    private Long conversationId;
    private int senderId;
    private String content;
    private LocalDateTime sentAt;
}

