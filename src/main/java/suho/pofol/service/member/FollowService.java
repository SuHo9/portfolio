package suho.pofol.service.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import suho.pofol.domain.member.Follow;
import suho.pofol.domain.member.User;
import suho.pofol.dto.member.FollowDTO;
import suho.pofol.repository.member.FollowRepository;
import suho.pofol.repository.member.UserRepository;
import suho.pofol.service.notice.NoticeService;

import java.util.List;

@Service
public class FollowService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private NoticeService noticeService;



    //친구 찾기
    // 예시: 현재 로그인한 사용자의 ID를 받아와 사용자 조회
//    public List<User> findAllUsersExceptLoggedInUser(int loggedInUserId, String username) {
//        return userRepository.findAllExceptLoggedInUser(loggedInUserId, username);
//    }

    /**
     * 닉네임 is not null 인 닉네임 리스트 뿌리기
     * @param username
     * @return
     */
    public List<User> findByNicknameContainingAndNicknameIsNotNull(String username) {
        return userRepository.findByNicknameContainingAndNicknameIsNotNull(username);
    }

    //username 조회
//    public List<User> findUsersByUsernameContaining(String username) {
//        return userRepository.findByUsernameContaining(username);
//    }
//    public List<User> searchUsers(String query) {
//        return userRepository.findByUsernameContaining(query);
//    }

    //로그인한 사용자인지
    public int findUserIdByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user != null ? user.getUserId() : -1;
    }

    // FollowRepository를 통해 두 사용자 사이의 친구 관계를 확인
    public boolean isAlreadyFriend(int followerId, int followingId) {
        Follow follow = followRepository.findByFollowerIdAndFollowingId(followerId, followingId);
        return follow != null; // 이미 친구인 경우 true, 아닌 경우 false 반환
    }

    /**
     * 친구 추가
     * @param followDTO
     */
    public void addFollow(FollowDTO followDTO) {
        User followerId =  userRepository.findById(followDTO.getFollowerId()).orElseThrow();
        User followingId = userRepository.findById(followDTO.getFollowingId()).orElseThrow();

        // Follow 객체 생성 및 설정
        Follow follow = Follow.builder()
                .followerId(followerId)
                .followingId(followingId)
                .build();

        // followRepository에 저장
        followRepository.save(follow);

        // 알림 생성
        String message = "친구추가 요청이 왔습니다.";
        noticeService.createNotice(followingId.getUserId() , message);
    }

    /**
     * 친구 수락
     */

    @Transactional
    public boolean acceptFollowRequest(int userId, int followerId) {
        // followerId와 followingId를 이용해 User 객체를 가져옴
        User follower = userRepository.findById(followerId).orElseThrow(() -> new RuntimeException("Follower not found"));
        User following = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // follow 테이블에 새로운 레코드 추가 (followerId와 userId를 반대로 저장)
        Follow follow = new Follow();
        follow.setFollowerId(following);
        follow.setFollowingId(follower);

        Follow follow2 = followRepository.findByFollowerIdAndFollowingId(follower.getUserId(), following.getUserId());

        if (follow2 != null) {
            follow.setAccepted(true);
            followRepository.save(follow);

            // 알림 메시지 업데이트
            String updatedMessage = "친구 수락을 완료했습니다.";
            noticeService.updateNoticeMessage(userId, updatedMessage);
            return true;
        }
        return false;

    }
}
