package suho.pofol.service.oauth2;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import suho.pofol.domain.member.User;
import suho.pofol.dto.oauth2.CustomOAuth2User;
import suho.pofol.dto.oauth2.GoogleResponse;
import suho.pofol.dto.oauth2.NaverResponse;
import suho.pofol.dto.oauth2.OAuth2Response;
import suho.pofol.dto.security.CustomUserDetails;
import suho.pofol.repository.member.UserRepository;

@Service
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    //DefaultOAuth2UserService OAuth2UserService의 구현체
    private final UserRepository userRepository;

    //test
    //@Autowired
    //private EntityManager entityManager;

    public CustomOAuth2UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    //@Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("OAuth2User attributes: {}", oAuth2User.getAttributes());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        log.info("Registration ID: {}", registrationId);

        OAuth2Response oAuth2Response = null;

        if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            log.error("Unsupported registration ID: {}", registrationId);
            return null;
        }

        log.info("OAuth2Response: {}", oAuth2Response);

        //DB
        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        User existData = userRepository.findByUsername(username);

        String role = null;

        if (existData == null) {

            log.info("User not found, creating new user.");
            User user = new User();
            user.setUsername(username);
            user.setEmail(oAuth2Response.getEmail());
            user.setRole("ROLE_USER");

            // 계산된 닉네임 설정
            String email = oAuth2Response.getEmail();
            String nickname = email.split("@")[0]; // 이메일에서 @ 기준으로 분리하여 첫 번째 부분을 닉네임으로 설정
            user.setNickname(nickname);

//            userRepository.save(user);
            log.info("User saved: {}", user);

            try {
                userRepository.save(user);
                //entityManager.flush(); // 강제로 플러시
                log.info("User saved: {}", user);
            } catch (Exception e) {
                log.error("Error saving user", e);
            }
            existData = user;   //ksh - Oauth2User 추가 소스
        }
        else {
            log.info("User found, updating user info.");
            existData.setUsername(username);
            existData.setEmail(oAuth2Response.getEmail());

            role = existData.getRole();

            userRepository.save(existData);
        }

        log.info("existData : {}", existData);
//        return  new CustomUserDetails(existData, oAuth2User.getAttributes());   //ksh - Oauth2User 추가 소스
        //return  new CustomUserDetails(oAuth2Response, role);   //ksh - Oauth2User 추가 소스
        return new CustomUserDetails(existData != null ? existData : new User(), oAuth2Response, role);

        //return new CustomOAuth2User(oAuth2Response, role);
        // new CustomUserDetails(existData);
        //CustomOAuth2User > CustomUserDetails 바꾸라고함 .
    }

}
