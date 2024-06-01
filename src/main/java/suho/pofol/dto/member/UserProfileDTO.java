package suho.pofol.dto.member;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import suho.pofol.domain.board.Post;
import suho.pofol.domain.member.User;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {

    private String username;
    private String nickname;
    private String profileImageUrl;
    private List<Post> imageUrl;

    public UserProfileDTO EntityToDto(User user) {
        return UserProfileDTO.builder()
                .username(username)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .imageUrl(imageUrl)
                .build();

    }
}