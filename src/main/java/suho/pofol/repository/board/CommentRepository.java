package suho.pofol.repository.board;

import org.springframework.data.jpa.repository.JpaRepository;
import suho.pofol.domain.board.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
