package suho.pofol.dto.setup;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ChangeInfoDTO {

    private String username;
    @NotEmpty(message = "닉네임은 필수 입력 값입니다.")
    private String nickname;
    private String profileImageUrl;
    private MultipartFile profileImageFile; // 추가된 변수

}
