package suho.pofol.controller.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import suho.pofol.domain.member.User;
import suho.pofol.dto.member.FollowDTO;
import suho.pofol.dto.security.CustomUserDetails;
import suho.pofol.service.member.FollowService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/member")
public class FollowController {

    @Autowired
    private FollowService followService;

    @GetMapping("/follow")
    public String searchPage() {
        return "/member/follow";
    }

    @GetMapping("/searchUsers")
    @ResponseBody
    public List<User> searchUsers(@RequestParam("query") String query, @AuthenticationPrincipal UserDetails userDetails) {

        int loggedInUserId = followService.findUserIdByUsername(userDetails.getUsername());

        // 로그인한 사용자를 제외하고 검색 결과를 가져옴
        List<User> users = followService.findByNicknameContainingAndNicknameIsNotNull(query);
        users = users.stream()
                .filter(user -> user.getUserId() != loggedInUserId) // 로그인한 사용자 제외
                .filter(user -> !followService.isAlreadyFriend(loggedInUserId, user.getUserId())) // 이미 친구인 사용자 제외
                .collect(Collectors.toList());

        return users;
    }
//    public List<User> searchUsers(@RequestParam String query) {
//        return followService.searchUsers(query);
//    }

    /**
     * 친구 추가
     * @param followDTO
     * @param principal
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public String addFollow(@RequestBody FollowDTO followDTO, @AuthenticationPrincipal CustomUserDetails principal) {

        // 로그인된 사용자의 ID 가져오기
        int userId = principal.getUserId();

        // FollowDTO에 로그인한 사용자의 ID 설정
        followDTO.setFollowerId(userId);

        // followService에 전달
        followService.addFollow(followDTO);

//        followService.addFollow(followDTO);
        return "Success";
    }

    /**
     * 친구 수락
     * @param requestBody
     * @param principal
     * @return
     */
    @PostMapping("/accept")
    @ResponseBody
    public String acceptFollowRequest(@RequestBody Map<String, Integer> requestBody, @AuthenticationPrincipal CustomUserDetails principal) {
        int followerId = requestBody.get("followerId");
        int userId = principal.getUserId();

//        followService.acceptFollowRequest(userId, followerId);
//        return "Success";

        boolean success = followService.acceptFollowRequest(userId, followerId);
        return success ? "Success" : "Failure";

    }
}