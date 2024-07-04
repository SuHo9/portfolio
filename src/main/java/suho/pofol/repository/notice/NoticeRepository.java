package suho.pofol.repository.notice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import suho.pofol.domain.member.User;
import suho.pofol.domain.notice.Notice;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
//    List<Notice> findByUserAndIsReadFalse(User userId); //ksh


    @Query("SELECT n FROM Notice n JOIN FETCH n.user u WHERE u.id = :userId AND n.isRead = false")
    List<Notice> findByUserAndIsReadFalse(@Param("userId") int userId);

    @Modifying
    @Query("UPDATE Notice n SET n.message = :updatedMessage WHERE n.user.id = :userId") //  AND n.follower.id = :followerId
    void updateMessageByUserIdAndFollowerId(@Param("userId") int userId, @Param("updatedMessage") String updatedMessage);
    //, @Param("followerId") int followerId
}
