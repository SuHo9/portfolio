package suho.pofol.repository.board;

import org.springframework.data.jpa.repository.JpaRepository;
import suho.pofol.domain.board.Likes;

public interface LikesRepository extends JpaRepository<Likes, Long> {
}
