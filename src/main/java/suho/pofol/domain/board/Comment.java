package suho.pofol.domain.board;

import jakarta.persistence.*;
import lombok.*;
import suho.pofol.domain.member.User;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String content;
    private LocalDateTime createdAt;

    public Comment(Post post, User user, String content, LocalDateTime createdAt) {
        this.post = post;
        this.user = user;
        this.content = content;
        this.createdAt = createdAt;
    }

}