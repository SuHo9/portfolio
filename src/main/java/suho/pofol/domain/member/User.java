package suho.pofol.domain.member;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import suho.pofol.domain.board.Comment;
import suho.pofol.domain.board.Likes;
import suho.pofol.domain.board.Post;
import suho.pofol.domain.chat.ConversationParticipant;
import suho.pofol.domain.chat.Message;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(unique = true)
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String role;
    private String profileImageUrl;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "following")
    private List<Follow> followings = new ArrayList<>();

    @OneToMany(mappedBy = "follower")
    private List<Follow> followers = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ConversationParticipant> conversationParticipants = new ArrayList<>();


    public User(){

    }

    @Builder
    public User(String username, String password, String nickname, String email, String role, String profileImageUrl) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
        this.profileImageUrl = profileImageUrl;
    }

}
