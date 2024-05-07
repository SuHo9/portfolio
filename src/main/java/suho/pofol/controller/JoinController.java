package suho.pofol.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import suho.pofol.dto.JoinDTO;
import suho.pofol.service.JoinService;

@Controller
@Slf4j
public class JoinController {



    @Autowired
    private JoinService joinService;

    @GetMapping("/join")
    public String joinP(){
        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProcess(JoinDTO joinDTO){

        log.info("옴?");

        System.out.println(joinDTO.getUsername());
        joinService.joinProcess(joinDTO);


        return "redirect:/login";
    }
}