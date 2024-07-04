package suho.pofol.controller.board;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import suho.pofol.domain.member.User;
import suho.pofol.dto.board.CommentDTO;
import suho.pofol.dto.security.CustomUserDetails;
import suho.pofol.repository.member.UserRepository;
import suho.pofol.service.board.CommentService;

import java.time.LocalDateTime;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserRepository userRepository;

    /**
     * 댓글 추가
     */
    @PostMapping("/addComment")
    public String addComment(@Valid CommentDTO commentDTO, BindingResult bindingResult, @AuthenticationPrincipal CustomUserDetails userDetails, Model model) {

        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }

        try {
            User user = userRepository.findByUsername(userDetails.getUsername());
            commentService.saveComment(commentDTO, user);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }


//        User user = userRepository.findByUsername(userDetails.getUsername());
//        commentService.saveComment(commentDTO, user);
        return "redirect:/";
    }

//    @PostMapping("/updateComment")
//    public String updateComment(@RequestParam Long id, @RequestParam String content) {
//        commentService.updateComment(id, content);
//        return "redirect:/posts";
//    }

    @PostMapping("/deleteComment")
    public String deleteComment(@RequestParam Long commentId) {
        commentService.deleteComment(commentId);
        return "redirect:/";
    }
}
