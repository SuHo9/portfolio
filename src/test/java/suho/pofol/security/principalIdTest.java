package suho.pofol.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import suho.pofol.controller.member.FollowController;

@SpringBootTest
public class principalIdTest {
    @Autowired
    private FollowController followController; // 테스트할 컨트롤러 클래스로 변경해야 함

    @Test
    @WithMockUser(username = "testUser", password = "password", roles = "USER") // 가짜 사용자 정보를 생성하여 테스트
    public void testGetLoggedInUserId() throws Exception {
        // 가짜 사용자 인증 객체 생성
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 가짜 사용자 인증 객체를 사용하여 컨트롤러 메소드 호출
        // 이 예시에서는 principal.getUsername()을 사용하여 사용자 아이디를 가져올 것을 가정
        // 실제로는 컨트롤러 메소드에서 principal 객체를 통해 사용자 정보를 가져오는 방식을 테스트
//        String result = followController.getLoggedInUserId(userDetails);

        // 테스트 결과 확인
        // 예상한 결과와 실제 결과를 비교하여 테스트를 수행
        // 예를 들어, 리턴 값이 올바른 사용자 아이디인지 확인할 수 있음
        // assertEquals(expectedResult, result);
    }
}
