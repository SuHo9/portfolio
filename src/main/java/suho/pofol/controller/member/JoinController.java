package suho.pofol.controller.member;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import suho.pofol.dto.member.JoinDTO;
import suho.pofol.service.member.JoinService;

@Controller
@Slf4j
@RequestMapping("/member")
public class JoinController {

    @Autowired
    private JoinService joinService;

    @GetMapping("/join")
    public String joinP(Model model){
        model.addAttribute("joinDTO", new JoinDTO().toUser());
        return "/member/join";
    }

    @PostMapping("/joinProc")
    public String joinProcess(@Valid JoinDTO joinDTO, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()) {
            return "/member/join";
        }

        try {
            joinService.joinProcess(joinDTO);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/member/join";
        }

        return "redirect:/login";
    }

}
