package suho.pofol.domain.chat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import suho.pofol.domain.member.User;

@Entity
@Setter
@Getter
public class ConversationParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conversationParticipant_id")
    private Long conversationParticipantId;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public ConversationParticipant(){

    }

    public ConversationParticipant(Long conversationParticipantId, Conversation conversation, User user) {
        this.conversationParticipantId = conversationParticipantId;
        this.conversation = conversation;
        this.user = user;
    }
}
