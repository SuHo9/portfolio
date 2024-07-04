package suho.pofol.domain.member;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
//@Builder
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private int followId;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User followerId;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private User followingId;

    @Column(name = "accepted")
    private boolean accepted;

    public Follow(){

    }

    @Builder
    public Follow(int followId, User followerId, User followingId, boolean accepted) {
        this.followId = followId;
        this.followerId = followerId;
        this.followingId = followingId;
        this.accepted = accepted;
    }
}
