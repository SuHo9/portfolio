package suho.pofol.controller.setup;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import suho.pofol.dto.board.PostDTO;
import suho.pofol.dto.member.JoinDTO;
import suho.pofol.dto.security.CustomUserDetails;
import suho.pofol.dto.setup.ChangeInfoDTO;
import suho.pofol.service.member.JoinService;
import suho.pofol.service.setup.ChangeInfoService;

import java.io.IOException;

@Controller
@RequestMapping("/setup")
@Slf4j
public class ChangeInfoController {

    @Autowired
    private ChangeInfoService changeInfoService;

    @GetMapping("/changeInfo")
    public String changeInfoPage(Model model, @AuthenticationPrincipal CustomUserDetails principal){
        String username = principal.getUsername();
        ChangeInfoDTO changeInfoDTO = changeInfoService.getUserInfo(username);
        model.addAttribute("changeInfoDTO", changeInfoDTO);
        return "/setup/changeInfo";
    }

    @PostMapping("/changeInfoProc")
    public String changeInfoProcess( @Valid ChangeInfoDTO changeInfoDTO, BindingResult bindingResult
                                   , @AuthenticationPrincipal CustomUserDetails principal
                                   , Model model){

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                log.info("Error: {}", error.getDefaultMessage());
            });
            return "/setup/changeInfo";
        }

        try {
            String username = principal.getUsername();
            log.info("컨트롤러 까지는 들어가는가? try");

//            if (changeInfoDTO.getProfileImageFile() == null || changeInfoDTO.getProfileImageFile().isEmpty()) {
//                bindingResult.rejectValue("profileImageFile", "field-error", "이미지를 업로드 해 주세요.");   // field-error    error.file
//                return "/board/post";
////                throw new IllegalArgumentException("File is missing");
//            }


            changeInfoService.changeProcess(changeInfoDTO, username);

        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/setup/changeInfo";
        } catch (IOException e) {
            e.printStackTrace();
            return "/setup/changeInfo";
        }

        model.addAttribute("successMessage", "프로필 정보가 성공적으로 변경되었습니다."); // 성공 메시지 추가

        return "redirect:/setup/changeInfo"; // 변경 성공 후 설정 페이지로 리다이렉트
    }
}
