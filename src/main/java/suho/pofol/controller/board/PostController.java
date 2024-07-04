package suho.pofol.controller.board;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import suho.pofol.dto.board.PostDTO;
import suho.pofol.dto.security.CustomUserDetails;
import suho.pofol.service.board.PostService;

import java.io.IOException;

@Controller
@Slf4j
public class PostController {


    @Autowired
    private PostService postService;

    @GetMapping("/board/post")
    public String boardGO(Model model) {
        model.addAttribute("postDTO", new PostDTO());
        return "board/post";
    }

    /**
     * 게시글 업로드
     * @return
     */
    @PostMapping("/board/post")
    public String createPost(@Valid PostDTO postDTO, BindingResult result, Model model,
                             @AuthenticationPrincipal CustomUserDetails principal) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> {
                log.info("Error: {}", error.getDefaultMessage());
            });
            return "/board/post";
        }

        try {
            // 현재 로그인한 사용자 정보 설정
            String username = principal.getUsername();
//            log.info("con principal username : {}", username);
//            postDTO.setUsername(username);
            postDTO.setCaption(postDTO.getCaption());

            log.info("con username : {}", username);

            // Check if file is null or empty
            if (postDTO.getFile() == null || postDTO.getFile().isEmpty()) {
                result.rejectValue("file", "field-error", "이미지를 업로드 해 주세요.");   // field-error    error.file
                return "/board/post";
//                throw new IllegalArgumentException("File is missing");
            }


            postService.createPost(postDTO, username);
        } catch (IOException e) {
            e.printStackTrace();
            return "board/post";
        }

        return "redirect:/";
    }

}
