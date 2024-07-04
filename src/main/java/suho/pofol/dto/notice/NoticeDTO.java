package suho.pofol.dto.notice;

import lombok.Getter;
import lombok.Setter;
import suho.pofol.domain.notice.Notice;

import java.time.LocalDateTime;

@Getter
@Setter
public class NoticeDTO {

    private int noticeId;
    private String message;
    private boolean isRead;
    private LocalDateTime createdAt;
    private int userId;
    private String followerName; // follower_nm을 담을 필드
    private int followerId;
    private Boolean accepted;
    private Boolean anotherAcc;
    private String nickname;

    // 생성자
    public NoticeDTO(int noticeId, int userId, String followerName, String message, int followerId, Boolean accepted, Boolean anotherAcc, String nickname) {
        this.noticeId = noticeId;
        this.userId = userId;
        this.followerName = followerName;
        this.message = message;
        this.followerId = followerId;
        this.accepted = accepted;
        this.anotherAcc = anotherAcc;
        this.nickname = nickname;
//        this.isRead = isRead;   , boolean isRead
//        this.createdAt = createdAt; , LocalDateTime createdAt



    }

    // Notice 엔티티를 기반으로 NoticeDTO를 생성하는 메서드
    public static NoticeDTO from(Notice notice, String followerName, int followerId, Boolean accepted, Boolean anotherAcc, String nickname) {
        return new NoticeDTO(
                notice.getNoticeId(),
                notice.getUser().getUserId(),
                notice.getMessage(),
                followerName,
                followerId,
                accepted,
                anotherAcc,
                nickname
//                notice.isRead(),
//                notice.getCreatedAt(),

        );
    }


}
