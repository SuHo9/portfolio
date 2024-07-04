package suho.pofol.service.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import suho.pofol.domain.board.Comment;
import suho.pofol.domain.member.User;
import suho.pofol.dto.board.CommentDTO;
import suho.pofol.repository.board.CommentRepository;
import suho.pofol.repository.board.PostRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    public void saveComment(CommentDTO commentDTO, User user) {
        Comment comment = Comment.builder()
                .post(postRepository.findById(commentDTO.getPostId()).orElseThrow())
                .content(commentDTO.getContent())
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
        commentRepository.save(comment);
    }

//    public void updateComment(Long id, String content) {
//        Optional<Comment> commentOptional = commentRepository.findById(id);
//        if (commentOptional.isPresent()) {
//            Comment comment = commentOptional.get();
//            comment.getContent();
//            commentRepository.save(comment);
//        }
//    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
