package suho.pofol.service.notice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import suho.pofol.domain.member.User;
import suho.pofol.domain.notice.Notice;
import suho.pofol.dto.notice.NoticeDTO;
import suho.pofol.mappers.notice.NoticeMapper;
import suho.pofol.repository.member.UserRepository;
import suho.pofol.repository.notice.NoticeRepository;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoticeMapper noticeMapper; // NoticeMapper 주입

    private final ConcurrentHashMap<Integer, List<SseEmitter>> sseEmitters = new ConcurrentHashMap<>();



    // MyBatis를 사용하는 메서드 추가

    //기존에는 도메인을 리스트로 뿌렸다가 지금은 DTO 생성해서 DTO로 뿌림
//    public List<Notice> getNoticesWithFollowerName(int userId) {
//        return noticeMapper.findNoticesWithFollowerName(userId);
//    }

    public void updateNoticeMessage(int userId, String updatedMessage) {
        noticeRepository.updateMessageByUserIdAndFollowerId(userId, updatedMessage);
    }

    /**
     * 알림 내역 조회
     * @param userId
     * @return
     */
    @Transactional
    public List<NoticeDTO> getNoticesWithFollowerName(int userId) {  //int
//        List<NoticeDTO> notices = noticeMapper.findNoticesWithFollowerName(userId);
//        return notices;

        // 로그에 타입과 값을 출력
//        log.info("userId2 (type: {}, value: {})", ((Object) userId).getClass().getSimpleName(), userId);
//
//
//
//
//        return noticeMapper.findNoticesWithFollowerName(userId);


        log.info("userId2 (type: {}, value: {})", ((Object) userId).getClass().getSimpleName(), userId);

        List<NoticeDTO> notices = null;
        try {
            notices = noticeMapper.findNoticesWithFollowerName(userId);

            if (notices != null) {
                for (NoticeDTO notice : notices) {
                    log.info("NoticeDTO: {}", notice);
                }
            } else {
                log.info("No notices found for userId: {}", userId);
            }
        } catch (Exception e) {
            log.error("Error fetching notices for userId {}: {}", userId, e.getMessage());
            // 예외 스택 트레이스를 로그에 출력
            log.error("Exception stack trace:", e);
        }


        return notices;
    }


    /**
     * 새로운 알림을 생성하고 저장하며, 실시간으로 알림을 전송합니다.
     *
     * @param userId  알림을 받을 사용자의 ID
     * @param message 알림 메시지
     */
    public void createNotice(int userId, String message) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Notice notice = new Notice();
        notice.setUser(user); // user 객체 설정
        notice.setMessage(message);
        noticeRepository.save(notice); // Notice 객체 저장
        sendNotification(userId, message); // 알림 전송
    }

    /**
     * 특정 사용자의 읽지 않은 알림 목록을 반환합니다.
     *
     * @param userId 사용자의 ID
     * @return 읽지 않은 알림 목록
     */
    public List<Notice> getUnreadNotices(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("invalid user ID: " + userId));
        return noticeRepository.findByUserAndIsReadFalse(userId);
    }

    /**
     * 특정 알림을 읽음 상태로 변경합니다.
     *
     * @param noticeId 알림의 ID
     */
    public void markAsRead(int noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new RuntimeException("Notice not found"));
        notice.setRead(true);
        noticeRepository.save(notice);
    }

    /**
     * 특정 사용자가 실시간 알림을 받을 수 있도록 SSE 구독을 설정합니다.
     *
     * @param userId 사용자의 ID
     * @return SSE Emitter 객체
     */
    public SseEmitter subscribe(int userId) {
        SseEmitter sseEmitter = new SseEmitter();
        sseEmitter.onCompletion(() -> removeEmitter(userId, sseEmitter));
        sseEmitter.onTimeout(() -> removeEmitter(userId, sseEmitter));
        sseEmitters.computeIfAbsent(userId, k -> new CopyOnWriteArrayList<>()).add(sseEmitter);
        return sseEmitter;
    }

    /**
     * SSE Emitter를 제거합니다.
     *
     * @param userId  사용자의 ID
     * @param emitter 제거할 Emitter 객체
     */
    private void removeEmitter(int userId, SseEmitter emitter) {
        List<SseEmitter> emitters = sseEmitters.get(userId);
        if (emitters != null) {
            emitters.remove(emitter);
        }
    }

    /**
     * 특정 사용자에게 실시간으로 알림을 전송합니다.
     *
     * @param userId  사용자의 ID
     * @param message 알림 메시지
     */
    private void sendNotification(int userId, String message) {
        List<SseEmitter> emitters = sseEmitters.get(userId);
        if (emitters != null) {
            for (SseEmitter emitter : emitters) {
                try {
                    emitter.send(SseEmitter.event().name("notice").data(message));
                } catch (IOException e) {
                    emitter.completeWithError(e);
                }
            }
        }
    }
}