package com.huttchang.sns.relation.service;

import com.huttchang.global.exception.DataNotFoundException;
import com.huttchang.global.exception.InvalidRequestException;
import com.huttchang.sns.account.domain.User;
import com.huttchang.sns.account.dto.AccountReq;
import com.huttchang.sns.notification.domain.Notification;
import com.huttchang.sns.notification.model.NotificationReq;
import com.huttchang.sns.notification.model.NotificationType;
import com.huttchang.sns.notification.service.NotificationService;
import com.huttchang.sns.relation.domain.Relation;
import com.huttchang.sns.relation.domain.RelationId;
import com.huttchang.sns.relation.domain.RelationUser;
import com.huttchang.sns.relation.domain.SimpleRelation;
import com.huttchang.sns.relation.dto.RelationReq;
import com.huttchang.sns.relation.dto.RelationState;
import com.huttchang.sns.relation.repository.RelationRepository;
import com.huttchang.sns.relation.repository.SimpleRelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RelationService {

    private final RelationRepository relationRepository;
    private final SimpleRelationRepository simpleRelationRepository;
    private final NotificationService notificationService;

    /**
     * 친구 관계의 유저 불러오는 로직
     *
     * @param id
     * @return
     * @throws DataNotFoundException
     */
    public List<Relation> findByRelation(AccountReq req) {
        return relationRepository.findRelationByCondition(req.getId(), req.getOffset(), req.getLimit());
    }

    /**
     * 친구 관계의 수락|거절
     *
     * @param id
     * @return
     * @throws DataNotFoundException
     */
    public boolean updateByRelation(RelationReq req) throws DataNotFoundException, InvalidRequestException {
        // 기존관계 조회 및 없는 경우 예외 밠행
        SimpleRelation relation
                = simpleRelationRepository.findById(new RelationId(req.getRequesterId(), req.getSomeoneId()))
                .orElseThrow(DataNotFoundException::new);

        NotificationReq.NotificationReqBuilder messageBuilder = NotificationReq.builder();

        // 수락일 경우만 상태 변경
        if (req.getStatus() == RelationState.ACCEPTED) {
            // 이미 수락 상태인경우도 잘못된 요청응답
            if (relation.getStatus() == RelationState.ACCEPTED)
                throw new InvalidRequestException();
            // 수락 상태로 변경하려면 내가 요청을 받아야하기때문에 someone과 내아이디 비교
            if (req.getSomeoneId() != req.getUserId())
                throw new InvalidRequestException();

            // 관계 상태 변경
            relation.changeStatusAndRelatedAt(req.getStatus());
            // 상태 변경 저장
            simpleRelationRepository.save(relation);
            // 노티메세지 발송
            notificationService.createNotification(messageBuilder
                    .toUserId(req.getRequesterId())
                    .fromUserId(req.getSomeoneId())
                    .type(NotificationType.RELATION_ACCEPTED).build());
        } else {
            // 신청 취소 등의 요청은 내가 요청자 일때 가능
            if (req.getRequesterId() != req.getUserId()) {
                throw new InvalidRequestException();
            }
            // 신청 시
            if (req.getStatus() == RelationState.REQUESTED) {
                relation.changeStatusAndRequestAt(req.getStatus());
                notificationService.createNotification(messageBuilder
                        .toUserId(req.getSomeoneId())
                        .fromUserId(req.getRequesterId())
                        .type(NotificationType.RELATION_REQUESTED).build());

            } else {
                // 그 외엔 삭제
                simpleRelationRepository.deleteById(new RelationId(req.getRequesterId(), req.getSomeoneId()));
            }
        }
        return true;
    }
}
