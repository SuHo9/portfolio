package suho.pofol.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class JoinDTO {

    private String username;
    private String password;
    @Email
    private String email;
}
