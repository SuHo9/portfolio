package suho.pofol.domain.chat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conversation_id")
    private Long conversationId;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "conversation")
    private List<ConversationParticipant> participants = new ArrayList<>();;

    @OneToMany(mappedBy = "conversation")
    private List<Message> messages = new ArrayList<>();

    public Conversation(){

    }



    public Conversation(Long conversationId, LocalDateTime createdAt
//                       , List<ConversationParticipant> participants, List<Message> messages
                        ) {
        this.conversationId = conversationId;
        this.createdAt = createdAt;
//        this.participants = participants;
//        this.messages = messages;
    }
}
