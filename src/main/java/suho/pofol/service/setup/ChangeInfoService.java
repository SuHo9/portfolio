package suho.pofol.service.setup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import suho.pofol.domain.member.User;
import suho.pofol.dto.setup.ChangeInfoDTO;
import suho.pofol.repository.member.UserRepository;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ChangeInfoService {

    @Value("${file.upload.dir.profile}") // 프로필 사진 저장 경로 설정   롬복 value 쓰는거 아님!!!!!!!!!!
    private String fileDir;

    @Autowired
    private UserRepository userRepository;

    //@Transactional
    public void changeProcess(ChangeInfoDTO changeInfoDTO, String username) throws IOException {
        // 프로필 사진 업로드 처리
        MultipartFile profileImageFile = changeInfoDTO.getProfileImageFile();
        if (profileImageFile != null && !profileImageFile.isEmpty()) {
            String profileImageUrl = saveProfileImage(profileImageFile);
            changeInfoDTO.setProfileImageUrl(profileImageUrl);
        }

        // 기타 프로필 정보 저장 로직
        User user = userRepository.findByUsername(username); // 예시로 사용자 조회

        log.info("user : {}", user);

        if (user != null) {
            user.setNickname(changeInfoDTO.getNickname());
            user.setProfileImageUrl(changeInfoDTO.getProfileImageUrl()); // 프로필 사진 URL 저장

            // 예시로 추가 필드가 있다면 저장
            // user.setAdditionalField(changeInfoDTO.getAdditionalField());

            userRepository.save(user); // 사용자 정보 저장
        } else {
            throw new IllegalStateException("사용자를 찾을 수 없습니다.");
        }
    }

    private String saveProfileImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        // 파일명 생성
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String savedFileName = UUID.randomUUID().toString() + extension;

        // 파일 저장 경로
        String savePath = fileDir + savedFileName;
        file.transferTo(new File(savePath));

        return savePath;
    }

    public ChangeInfoDTO getUserInfo(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            ChangeInfoDTO changeInfoDTO = new ChangeInfoDTO();
            changeInfoDTO.setUsername(user.getUsername());
            changeInfoDTO.setNickname(user.getNickname());
            changeInfoDTO.setProfileImageUrl(user.getProfileImageUrl());
            return changeInfoDTO;
        } else {
            throw new IllegalStateException("사용자를 찾을 수 없습니다.");
        }
    }
}
