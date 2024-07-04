package suho.pofol.domain.board;

import jakarta.persistence.*;
import lombok.*;
import suho.pofol.domain.member.User;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Likes {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long likeId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Likes(Post post, User user) {
        this.post = post;
        this.user = user;
    }
}