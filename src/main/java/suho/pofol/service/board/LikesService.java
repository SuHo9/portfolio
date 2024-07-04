package suho.pofol.service.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import suho.pofol.domain.board.Likes;
import suho.pofol.domain.member.User;
import suho.pofol.dto.board.LikeDTO;
import suho.pofol.repository.board.LikesRepository;
import suho.pofol.repository.board.PostRepository;
import suho.pofol.repository.member.UserRepository;

@Service
public class LikesService {

    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public void saveLike(LikeDTO likeDTO, int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("invalid user ID: " + userId));

        Likes like = Likes.builder()
                .post(postRepository.findById(likeDTO.getPostId()).orElseThrow())
                .user(user)
                .build();
        likesRepository.save(like);
    }
}
