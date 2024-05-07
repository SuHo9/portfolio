package suho.pofol.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.client.JdbcOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

/**
 * (client_registration_id, principal_name)가 겹치는 경우가 존재하면 테이블에 값이 오버 라이팅되는 현상이 발생하는데 운영 환경에서 발생하는 문제가 없을지 의문점이 든다.
 *
 * **예시**
 *
 * ```
 * user : username / client_registration_id / principal_name
 * user1 : bbbbbbbb / naver / 개발자유미
 * user2 : aaaaaaaaa / naver / 개발자유미
 * ```
 *
 * 위와 같이 client_registration_id와 principal_name이 겹칠 경우 새로운 row가 생성되지 않고 기존 데이터 위에 덮어 씌어짐
 *
 * 만약 해당 경우가 동시에 로그인을 진행한다면 로그인이 실패할 수 있을거 같다. (Access 토큰을 발급 받고 추후에 사용하지는 않지만, 동시에 로그인을 진행한다면 오류가 발생할 수 있지 않을까.)
 *
 * 따라서 스프링 시큐리티 공식 깃허브에 Issue로 질문을 했고, 개발자측에서 OAuth2AuthorizedClientService를 알아서 구현해서 사용하라고 답변이 왔습니다.
 */

@Configuration
public class CustomOAuth2AuthorizedClientService {

    @Bean
    public OAuth2AuthorizedClientService oAuth2AuthorizedClientService(JdbcTemplate jdbcTemplate, ClientRegistrationRepository clientRegistrationRepository) {

        return new JdbcOAuth2AuthorizedClientService(jdbcTemplate ,clientRegistrationRepository);
    }
}
