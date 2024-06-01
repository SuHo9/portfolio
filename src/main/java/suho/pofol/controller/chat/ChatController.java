package suho.pofol.controller.chat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sidebar")
public class ChatController {

    @GetMapping("/chat")
    public String chat(){

        return "chat";
    }
}
