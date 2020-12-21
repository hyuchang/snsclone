package com.huttchang.sns.notification.service;

import com.huttchang.global.exception.DataNotFoundException;
import com.huttchang.sns.account.domain.User;
import com.huttchang.sns.account.repository.UserRepository;
import com.huttchang.sns.notification.domain.Notification;
import com.huttchang.sns.notification.model.NotificationReq;
import com.huttchang.sns.notification.model.NotificationType;
import com.huttchang.sns.notification.repository.NotificationRepository;
import com.huttchang.sns.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private String MESSAGE_TEMPLATE_LIKE = "%s님께서 회원님의 게시물에 관심을 표하였습니다.";
    private String MESSAGE_TEMPLATE_COMMENT = "%s님께서 회원님의 게시물에 관심의 댓글을 달았습니다.";
    private String MESSAGE_TEMPLATE_REQUEST = "%s님께서 회원님에게 친구요청을 하셨습니다.";
    private String MESSAGE_TEMPLATE_REQUEST_ACCEPT = "%s님께서 회원님에게 친구요청 수락하셨습니다.";

    public List<Notification> findNotificationsByMe(NotificationReq req){
        return notificationRepository.findByToUserId(PageRequest.of(req.getOffset(), req.getLimit(), Sort.by("id").descending()), req.getToUserId());
    }

    public void createNotification(NotificationReq req) throws DataNotFoundException{
        // 내가 내 게시물에 한 행위는 무시된다.
        if ( req.getToUserId() == req.getFromUserId()) {
            return;
        }
        // 메세지 발송자의 정보 조회
        User fromUser = userRepository.findById(req.getFromUserId()).orElseThrow(DataNotFoundException::new);
        // 관계 관련 알림아 아닌 경우
        if ( req.getType() == NotificationType.LIKE
                || req.getType() == NotificationType.COMMENT ) {
            // 게시물이 존재 하는지 확인
            postRepository.findById(req.getPostId()).orElseThrow(DataNotFoundException::new);
            // 메세지타입별 메세지 템플릿 분기
            if ( req.getType() == NotificationType.LIKE )
                req.setDescription(String.format(MESSAGE_TEMPLATE_LIKE, fromUser.getNickname()));
            else if ( req.getType() == NotificationType.COMMENT )
                req.setDescription(String.format(MESSAGE_TEMPLATE_COMMENT, fromUser.getNickname()));

        } else {
            // 메세지타입별 메세지 템플릿 분기
            if ( req.getType() == NotificationType.RELATION_REQUESTED ) {
                req.setDescription(String.format(MESSAGE_TEMPLATE_REQUEST, fromUser.getNickname()));
            } else if ( req.getType() == NotificationType.RELATION_ACCEPTED ) {
                // 수락 시 보낸 사람에게 다시 노티 해주기위하여 TouserId의 정보를 조회
                User toUser = userRepository.findById(req.getToUserId()).orElseThrow(DataNotFoundException::new);
                req.setDescription(String.format(MESSAGE_TEMPLATE_REQUEST_ACCEPT, toUser.getNickname()));
                // 상대방에게 발송하는 기능이므로 수신, 발신자의 정보를 교환
                req.setToUserId(req.getFromUserId());
                req.setFromUserId(toUser.getId());
            }
        }
        // 메세지 저장
        notificationRepository.save(req.toEntity());
    }

    public void readNotification(NotificationReq req) throws DataNotFoundException {
        // 알림메세지의 조회
        Notification notification
                = notificationRepository.findById(req.getId()).orElseThrow(DataNotFoundException::new);
        // 메세지 읽음 처리
        notification.read();
        // 읽은 상태 저장
        notificationRepository.save(notification);
    }
}
