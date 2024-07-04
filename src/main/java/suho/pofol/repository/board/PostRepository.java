package suho.pofol.repository.board;

import org.springframework.data.jpa.repository.JpaRepository;
import suho.pofol.domain.board.Post;
import suho.pofol.domain.member.User;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser(User user);
    //    Post findByUsername(String username);
}