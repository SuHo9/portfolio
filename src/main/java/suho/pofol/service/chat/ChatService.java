package suho.pofol.service.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import suho.pofol.domain.chat.Conversation;
import suho.pofol.domain.chat.Message;
import suho.pofol.domain.member.User;
import suho.pofol.dto.chat.ChatListDTO;
import suho.pofol.dto.chat.ConversationDTO;
import suho.pofol.dto.chat.MessageDTO;
import suho.pofol.dto.notice.NoticeDTO;
import suho.pofol.mappers.chat.ChatMapper;
import suho.pofol.mappers.notice.NoticeMapper;
import suho.pofol.repository.chat.ConversationRepository;
import suho.pofol.repository.chat.MessageRepository;
import suho.pofol.repository.member.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ChatService {
    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatMapper chatMapper; // NoticeMapper 주입

    /**
     * 친구 목록 조회
     * @param userId
     * @return
     */
    @Transactional
    public List<ChatListDTO> chatListSelect(int userId) {
        List<ChatListDTO> chatLists = null;
        try {
            chatLists = chatMapper.FollowerSelect(userId);

            if (chatLists != null) {
                for (ChatListDTO chatList : chatLists) {
                    Long conversationId = conversationRepository.findConversationIdByParticipants(userId, chatList.getFollowing_id());
                    chatList.setConversationId(conversationId);
                }
            } else {
                log.info("No notices found for userId: {}", userId);
            }
        } catch (Exception e) {
            log.error("Error fetching notices for userId {}: {}", userId, e.getMessage());
            // 예외 스택 트레이스를 로그에 출력
            log.error("Exception stack trace:", e);
        }


        return chatLists;
    }

    @Transactional
    public List<MessageDTO> getMessages(Long conversationId) {
        List<Message> messages = messageRepository.findByConversationConversationId(conversationId);
        return messages.stream()
                .map(message -> {
                    MessageDTO dto = new MessageDTO();
                    dto.setConversationId(message.getConversation().getConversationId());
                    dto.setSenderId(message.getUser().getUserId());
                    dto.setContent(message.getContent());
                    dto.setSentAt(message.getSentAt());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public MessageDTO sendMessage(Long conversationId, int senderId, String content) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));
        User sender = userRepository.findById(Math.toIntExact(senderId))
                .orElseThrow(() -> new RuntimeException("User not found"));

        Message message = new Message();
        message.setConversation(conversation);
        message.setUser(sender);
        message.setContent(content);
        message.setSentAt(LocalDateTime.now());

        messageRepository.save(message);

        MessageDTO dto = new MessageDTO();
        dto.setConversationId(conversationId);
        dto.setSenderId(senderId);
        dto.setContent(content);
        dto.setSentAt(message.getSentAt());

        return dto;
    }

    // 추가된 메서드
    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }
}
