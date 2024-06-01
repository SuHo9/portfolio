package suho.pofol.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import suho.pofol.domain.member.User;

@Entity
@Setter
@Getter
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

    public Likes(){

    }

    public Likes(Long likeId, Post post, User user) {
        this.likeId = likeId;
        this.post = post;
        this.user = user;
    }
}