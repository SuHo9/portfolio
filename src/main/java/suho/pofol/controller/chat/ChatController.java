package suho.pofol.controller.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import suho.pofol.domain.member.User;
import suho.pofol.dto.chat.ChatListDTO;
import suho.pofol.dto.chat.ConversationDTO;
import suho.pofol.dto.chat.MessageDTO;
import suho.pofol.dto.notice.NoticeDTO;
import suho.pofol.dto.security.CustomUserDetails;
import suho.pofol.service.chat.ChatService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/chat")
    public String getChatPage(@AuthenticationPrincipal CustomUserDetails principal, Model model){
        int userId = principal.getUserId();


        List<ChatListDTO> chatLists = chatService.chatListSelect(userId); // 변경된 메서드 호출
        model.addAttribute("chatLists", chatLists);
        model.addAttribute("userId", userId);
        return "chat/chat";
    }

    @GetMapping("/messages/{conversationId}")
    @ResponseBody
    public List<MessageDTO> getMessages(@PathVariable Long conversationId) {
        return chatService.getMessages(conversationId);
    }

    @PostMapping("/messages/{conversationId}")
    @ResponseBody
    public MessageDTO sendMessage(@PathVariable Long conversationId, @RequestBody MessageDTO messageDTO, @AuthenticationPrincipal CustomUserDetails principal) {
        int userId = principal.getUserId();
        return chatService.sendMessage(conversationId, userId, messageDTO.getContent());
    }
}
