package suho.pofol.controller.notice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import suho.pofol.domain.member.User;
import suho.pofol.domain.notice.Notice;
import suho.pofol.dto.notice.NoticeDTO;
import suho.pofol.dto.security.CustomUserDetails;
import suho.pofol.service.notice.NoticeService;

import java.util.List;

@Controller
@Slf4j
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/notice")
    public String noticePage(@AuthenticationPrincipal CustomUserDetails principal, Model model) {
        int userId = principal.getUserId();

//       String userId = String.valueOf(principal.getUserId());

        log.info("userId : " + userId);
        // 로그에 타입과 값을 출력
        log.info("userId (type: {}, value: {})", ((Object) userId).getClass().getSimpleName(), userId);
//        List<Notice> notices = noticeService.getNoticesWithFollowerName();
        List<NoticeDTO> notices = noticeService.getNoticesWithFollowerName(userId); // 변경된 메서드 호출

//        List<Notice> notices = noticeService.getUnreadNotices(userId);
        model.addAttribute("notices", notices);
        model.addAttribute("userId", userId);
        return "/notice/notice"; // This should match the name of your HTML file without the .html extension
    }

    @GetMapping("/notices")
    @ResponseBody
    public SseEmitter subscribe(@AuthenticationPrincipal CustomUserDetails principal) {
        int userId = principal.getUserId();
        return noticeService.subscribe(userId);
    }

    @GetMapping("/notices/read/{noticeId}")
    @ResponseBody
    public void markAsRead(@PathVariable int noticeId) {
        noticeService.markAsRead(noticeId);
    }
}