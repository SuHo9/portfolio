package suho.pofol.service.board;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import suho.pofol.domain.board.Post;
import suho.pofol.domain.member.User;
import suho.pofol.dto.board.PostDTO;
import suho.pofol.repository.board.PostRepository;
import suho.pofol.repository.member.UserRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class PostService {

    @Value("${file.dir}")
    private String fileDir;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }


    public void createPost(PostDTO postDTO, String username) throws IOException {

        log.info("service before username : {}", username);

        // username으로 User 객체 조회
        User user = userRepository.findByUsername(username);
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));


//        log.info("service after username : {}", user);

        // 파일 업로드 처리
        MultipartFile file = postDTO.getFile();
        String imageUrl = saveFile(file);

        // Post 엔티티 생성 및 저장
        Post post = postDTO.toEntity(user, postDTO.getCaption(), imageUrl);
        postRepository.save(post);
    }

    private String saveFile(MultipartFile file) throws IOException {
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


}
