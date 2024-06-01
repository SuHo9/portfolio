package suho.pofol.dto.board;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import suho.pofol.domain.board.Post;
import suho.pofol.domain.member.User;

@Getter
@Setter
public class PostDTO {

    /**
     * 게시글 등록시 user 객체들 db를 2번 타지 않고 postDTO 에서 한번에 등록하기 위헤 PostDTO 작성
     */

    /* domain Post*/
    @NotEmpty(message = "게시글 내용을 입력해 주세요.")
    private String caption;
    @NotNull(message = "이미지를 필수로 등록해 주세요..")
    private MultipartFile file;
//    private String username;


    public Post toEntity(User username , String caption, String imageUrl){
        return Post.builder()
                .user(username)
                .caption(caption)
                .imageUrl(imageUrl)
                .build();

    }

//    public void setUsername2(String username) {
//        this.username = username;
//    }
}
