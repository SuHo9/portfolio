package suho.pofol.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import suho.pofol.domain.member.Follow;

public interface FollowRepository extends JpaRepository<Follow, Integer> {

    // followerId와 followingId로 친구 관계를 조회
    @Query("SELECT f FROM Follow f WHERE f.followerId.userId = :followerId AND f.followingId.userId = :followingId")
    Follow findByFollowerIdAndFollowingId(@Param("followerId") int followerId, @Param("followingId") int followingId);

//    @Modifying
//    @Transactional
//    @Query("UPDATE Notice n SET n.message = :message WHERE n.user.userId = :userId")   //  AND n.follower.userId = :followerId
//    void updateMessageByUserIdAndFollowerId(@Param("userId") int userId, @Param("message") String message);   // , @Param("followerId") int followerId


}