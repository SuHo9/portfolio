package suho.pofol.domain.chat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import suho.pofol.domain.member.User;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long messageId;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User user;

    private String content;
    private LocalDateTime sentAt;

    public Message(){

    }

    public Message(Long messageId, Conversation conversation, User user, String content, LocalDateTime sentAt) {
        this.messageId = messageId;
        this.conversation = conversation;
        this.user = user;
        this.content = content;
        this.sentAt = sentAt;
    }
}
