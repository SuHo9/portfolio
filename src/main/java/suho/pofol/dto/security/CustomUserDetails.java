package suho.pofol.dto.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import suho.pofol.domain.member.User;
import suho.pofol.dto.oauth2.OAuth2Response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * 일반 회원 가입 로그인 및 Oauth2 회원가입 로그인 연동 (UserDetails / Oauth2User)
 */
public class CustomUserDetails implements UserDetails, OAuth2User  {

    private final User user;
    private final OAuth2Response oAuth2Response;
    private final String role;


    /**
     * CustomUserDetailsService 용
     * @param user
     */
    public CustomUserDetails(User user) {
        this.user = user;
        this.oAuth2Response = null;
        this.role = user.getRole();
    }

    /**
     * CustomOAuth2UserService 용
     * @param oAuth2Response, role
     */
    public CustomUserDetails(User user, OAuth2Response oAuth2Response, String role){
        this.user = user;
        this.oAuth2Response = oAuth2Response;
        this.role = role;
    }

//    public CustomUserDetails(User user, Map<String, Object> attributes){   //ksh - Oauth2User 추가 소스
//        this.user = user;
//        this.attributes = attributes;   //ksh - Oauth2User 추가 소스
//    }

    //사용자 특정 권한 return
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(() -> role);

//        Collection<GrantedAuthority> collection = new ArrayList<>();
//        collection.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority()
//                return user.getRole();
//            }
//        });

        return collection;
    }

    @Override
    public String getPassword() {
        return user != null ? user.getPassword() : null;
//        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user != null ? user.getUsername() : oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        //   oAuth2Response.getProviderId() - > oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId() ;


//        return user.getUsername();
    }

    @Override
    public String getName() {

        return oAuth2Response.getName();
    }

    public String getUserName() {

        return oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
    }


    // userId 값을 반환하는 메서드 추가
    public int getUserId() {
//        return user.getUserId();
        return user != null ? user.getUserId() : -1; // OAuth2 로그인 시에는 User ID를 사용할 수 없으므로 -1 반환
    }

    //ksh - Oauth2User 추가 소스
    @Override
    public Map<String, Object> getAttributes() {
        return null;
//        return oAuth2Response != null ? oAuth2Response.getAttributes() : null;   ksh 확인
//        return attributes;
        //return null;
    }


    /**
     * 아래 메소드들을 쓸려면 user 테이블에 컬럼 추가해서 데이터 넣고 기능 추가 필요.
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomUserDetails that = (CustomUserDetails) o;
        return (user != null ? user.equals(that.user) : that.user == null) &&
                (oAuth2Response != null ? oAuth2Response.equals(that.oAuth2Response) : that.oAuth2Response == null);
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (oAuth2Response != null ? oAuth2Response.hashCode() : 0);
        return result;
    }

}
