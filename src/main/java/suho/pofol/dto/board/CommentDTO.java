package suho.pofol.dto.board;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class CommentDTO {

    private Long commentId;
    private Long postId;
    private Long userId;
    @NotBlank(message = "댓글에 내용을 입력해주세요.")
    private String content;
    private LocalDateTime createdAt;

    @Builder
    public CommentDTO() {
        this.commentId = commentId;
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
    }

}
