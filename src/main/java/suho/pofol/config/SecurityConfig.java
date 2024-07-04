package suho.pofol.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import suho.pofol.oauth2.CustomClientRegistrationRepo;
import suho.pofol.oauth2.CustomOAuth2AuthorizedClientService;
import suho.pofol.security.CustomAuthFailureHandler;
import suho.pofol.service.oauth2.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomClientRegistrationRepo customClientRegistrationRepo;
    private final CustomOAuth2AuthorizedClientService customOAuth2AuthorizedClientService;
    private final JdbcTemplate jdbcTemplate;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService, CustomClientRegistrationRepo customClientRegistrationRepo,
                          CustomOAuth2AuthorizedClientService customOAuth2AuthorizedClientService, JdbcTemplate jdbcTemplate){
        this.customOAuth2UserService = customOAuth2UserService;
        this.customClientRegistrationRepo = customClientRegistrationRepo;
        this.customOAuth2AuthorizedClientService = customOAuth2AuthorizedClientService;
        this.jdbcTemplate = jdbcTemplate;
    }



    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    /**
     * 계층 권한
     * @return
     */
    @Bean
    public RoleHierarchy roleHierarchy() {

        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();

        hierarchy.setHierarchy("ROLE_C > ROLE_B\n" + "ROLE_B > ROLE_A");

        return hierarchy;
    }

    @Bean
    AuthenticationFailureHandler customAuthFailureHandler(){

//        log.info("AuthenticationFailureHandler!!!");
        return new CustomAuthFailureHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

//        log.info("SecurityFilterChain!!!");

        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");


        /*네이버 로그인 OAuth2*/
        http
                .oauth2Login((oauth2) -> oauth2
                        .loginPage("/login") //커스텀 로그인 페이지
                        .clientRegistrationRepository(customClientRegistrationRepo.clientRegistrationRepository()) //SocialClientRegistration
                        .authorizedClientService(customOAuth2AuthorizedClientService.oAuth2AuthorizedClientService(jdbcTemplate, customClientRegistrationRepo.clientRegistrationRepository()))
                        .userInfoEndpoint((userInfoEndpointConfig) ->
                                userInfoEndpointConfig.userService(customOAuth2UserService)));  //Customizer.withDefaults() > oauth2 람다식 적용


        http
                .csrf(csrf -> csrf
                        .csrfTokenRequestHandler(requestHandler)
                        //CsrfTokenRequestAttributeHandler 인터페이스를 구현하면 추가 작업을 수행하거나 어플리케이션에서 토큰을 사용할 수 있는 방법을 커스텀할 수 있습니다.
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        //앵귤러나 리엑트와 함께 사용하면 withHttpOnlyFalse() 옵션을 사용해야 합니다. 쿠키의 http only 설정에 대해 검색해보시길 바랍니다.
                        .ignoringRequestMatchers( "/login/**", "/logout/**", "/register/validate/email"
                                , "/addLike", "/addComment", "/board/post" , "/deleteComment"
                                , "/member/follow", "/member/searchUsers", "/member/add", "/member/accept", "/"
                                , "/setup/changeInfo", "/setup/changeInfoProc"
                                , "/chat/messages/**"
                        )  /*  운영 환경에서는 ignoringRequestMatchers 함수 삭제 및 주석처리 ★★★★★  */
                )
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers( "/member/join", "/member/joinProc", "/login", "/oauth2/**", "/loginProc", "/logout/**").permitAll() /*  ,"/" */
                        .requestMatchers( "/resources/**", "/static/**", "/assets/**", "/error", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers( "/bootstrap-icons.svg").permitAll()
                        .requestMatchers( "/addLike", "/addComment", "/board/post").authenticated()
//                        .requestMatchers( "/board").permitAll()
//                        .requestMatchers("/admin").hasRole("ADMIN")   //admin 페이지 인경우 로그인 필요.
//                        .requestMatchers("/**").hasAnyRole("ADMIN", "USER")
//                        .requestMatchers("/").hasAnyRole("A")
//                        .requestMatchers("/manager").hasAnyRole("B")
//                        .requestMatchers("/admin").hasAnyRole("C")
                        .anyRequest().authenticated() //나머지 요청들은 권한이 있어야만 접근 가능
                );
                /*.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class);*/
        //사용 권한 없을때 로그인페이지로 이동시키는 역할
        //.httpBasic(Customizer.withDefaults());

        //
        http
                .formLogin((auth) -> auth.loginPage("/login")  //로그인 페이지 경로 설정
                        .loginProcessingUrl("/loginProc")      //form action 경로 설정 > 시큐리티 내부 작동
                        .failureHandler(customAuthFailureHandler())
                        .permitAll()
                );

        /*사용안함*/
        //http
        //        .csrf((auth) -> auth.disable());
        http
                .sessionManagement((auth) -> auth //
                .maximumSessions(5)               //하나의 아이디에서 중복로그인 갯수 설정
                .maxSessionsPreventsLogin(true)); //true 로그인 차단 false 초과시 기존세션 하나 삭제

        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId()); //세션 보호
        http
                .logout((auth) -> auth.logoutUrl("/logout")
                        .logoutSuccessUrl("/login"))
                ;

        return http.build();
    }


}
