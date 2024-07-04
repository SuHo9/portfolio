package suho.pofol.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import suho.pofol.domain.member.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUsername(String username);

    User findByUsername(String username);

    //친구 찾기
    List<User> findByUsernameContaining(String username);

    List<User> findByNicknameContainingAndNicknameIsNotNull(String username);

    // nickname이 null이 아닌 사용자를 조회하는 메서드
    @Query("SELECT u FROM User u WHERE u.id <> :loggedInUserId AND u.username LIKE %:username% AND u.nickname IS NOT NULL")
    List<User> findAllExceptLoggedInUserWithNicknameNotNull(@Param("loggedInUserId") int loggedInUserId, @Param("username") String username);


//    // 현재 로그인한 사용자의 ID를 제외한 모든 사용자를 조회하는 JPQL 쿼리
//    @Query("SELECT u FROM User u WHERE u.id <> :loggedInUserId AND u.username LIKE %:username%")
//    List<User> findAllExceptLoggedInUser(@Param("loggedInUserId") int loggedInUserId, @Param("username") String username);

}
