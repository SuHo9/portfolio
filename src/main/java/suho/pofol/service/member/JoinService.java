package suho.pofol.service.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import suho.pofol.domain.member.User;
import suho.pofol.dto.member.JoinDTO;
import suho.pofol.repository.member.UserRepository;

@Service
@Slf4j
public class JoinService {


    //필드 주입 보다는 생성자 주입이 좋다고함...
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    public void joinProcess(JoinDTO joinDTO) {

        //db에 이미 동일한 username을 가진 회원이 존재하는지? 검증 로직 필요.
        boolean isUser = userRepository.existsByUsername(joinDTO.getUsername());
        if (isUser) {
            return;
        }

        //Dto > entity로 변경
        User data = new User();

        data.setUsername(joinDTO.getUsername());
        data.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword())); //암호화
        data.setNickname(joinDTO.getNickname());
        data.setEmail(joinDTO.getEmail());
        data.setRole("ROLE_USER");

        //log.info("data.setEmail(joinDTO.getEmail())", data.setEmail(joinDTO.getEmail()));

        userRepository.save(data);

    }
}
