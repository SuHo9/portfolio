package suho.pofol.dto.chat;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

@Getter
@Setter
public class ChatListDTO {

    private int follower_id;
    private int following_id;
    private String nickname;
    //생성자에 대화방 id 추가
    private Long conversationId;

    public ChatListDTO() {
        // 기본 생성자 추가
    }

    public ChatListDTO(int follower_id, int following_id, String nickname, Long conversationId){

        this.follower_id = follower_id;
        this.following_id = following_id;
        this.nickname = nickname;
        this.conversationId = conversationId;
    }
}
