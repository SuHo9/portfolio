package suho.pofol.domain.notice;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import suho.pofol.domain.member.User;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noticeId;

    private String message; // 알림 메시지
    private boolean isRead; // 읽음 여부

    private LocalDateTime createdAt; // 알림 생성 시간

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 알림을 받을 사용자

    // 생성자, 게터, 세터 등 생략
}