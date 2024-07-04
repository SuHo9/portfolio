package suho.pofol.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import suho.pofol.domain.board.Post;
import suho.pofol.domain.member.Follow;
import suho.pofol.domain.member.User;

@Getter
@Setter
@Builder
public class FollowDTO {
    private int followId;
    private int followerId;
    private int followingId;


}