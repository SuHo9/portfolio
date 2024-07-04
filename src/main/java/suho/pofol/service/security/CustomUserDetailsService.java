package suho.pofol.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import suho.pofol.domain.member.User;
import suho.pofol.dto.security.CustomUserDetails;
import suho.pofol.repository.member.UserRepository;

import java.util.Map;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        User userData = userRepository.findByUsername(username);
//
//        if (userData != null) {
//
//            return new CustomUserDetails(userData);
//        }
//
//        return null;
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(user);

//        return new CustomUserDetails(User user, Map<String, Object> attribute);
//        return  new CustomUserDetails(user, oAuth2User.getAttributes());   //ksh - Oauth2User 추가 소스
    }
}
