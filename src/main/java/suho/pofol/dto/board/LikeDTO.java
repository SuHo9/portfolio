package suho.pofol.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class LikeDTO {
    private final Long likeId;
    private final Long postId;
    private final Long userId;

    @Builder
    public LikeDTO(Long likeId, Long postId, Long userId) {
        this.likeId = likeId;
        this.postId = postId;
        this.userId = userId;
    }
}