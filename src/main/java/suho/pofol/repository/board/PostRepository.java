package suho.pofol.repository.board;

import org.springframework.data.jpa.repository.JpaRepository;
import suho.pofol.domain.board.Post;
import suho.pofol.domain.member.User;

public interface PostRepository extends JpaRepository<Post, Long> {

//    Post findByUsername(String username);
}