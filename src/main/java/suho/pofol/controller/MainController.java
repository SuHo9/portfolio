package suho.pofol.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import suho.pofol.domain.board.Post;
import suho.pofol.dto.board.PostDTO;
import suho.pofol.dto.member.JoinDTO;
import suho.pofol.service.board.PostService;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * mainP : security 권한 필요시 적용
 *       : 메인에 게시글 리스트 뿌리기
 *       :
 */
@Controller
@Slf4j
public class MainController {

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String mainP(Model model){

        //원래는 service 단 하나 만들어서 구현해줘야하지만 간단하게 하기위해서 여기에 작성

        String id = SecurityContextHolder.getContext().getAuthentication().getName();;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        /* 메인에 게시글 리스트 뿌리기 */
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);

        model.addAttribute("id", id);
        model.addAttribute("role", role);

        return "main";

    }

}
