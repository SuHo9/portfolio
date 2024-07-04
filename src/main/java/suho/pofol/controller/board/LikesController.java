package suho.pofol.controller.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import suho.pofol.domain.board.Likes;
import suho.pofol.domain.member.User;
import suho.pofol.dto.board.LikeDTO;
import suho.pofol.dto.security.CustomUserDetails;
import suho.pofol.repository.member.UserRepository;
import suho.pofol.service.board.LikesService;
import suho.pofol.service.board.PostService;

@Controller
public class LikesController {
    @Autowired
    private LikesService likesService;


    @PostMapping("/addLike")
    public String addLike(LikeDTO likeDTO, @AuthenticationPrincipal CustomUserDetails principal) {
        int userId = principal.getUserId();
        likesService.saveLike(likeDTO, userId);
        return "redirect:/";
    }
}
