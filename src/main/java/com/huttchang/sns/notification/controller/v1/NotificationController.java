package com.huttchang.sns.notification.controller.v1;

import com.huttchang.global.model.ResponseBody;
import com.huttchang.sns.account.domain.User;
import com.huttchang.sns.notification.domain.Notification;
import com.huttchang.sns.notification.model.NotificationReq;
import com.huttchang.sns.notification.service.NotificationService;
import com.huttchang.sns.post.dto.PostDto;
import com.huttchang.sns.post.dto.PostReq;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseBody<List<Notification>> getNotification(@AuthenticationPrincipal User principal, NotificationReq req) throws Exception {
        req.setToUserId(principal.getId());
        return new ResponseBody<>(notificationService.findNotificationsByMe(req));
    }

}
