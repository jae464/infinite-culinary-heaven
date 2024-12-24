package com.culinaryheaven.domain.comment.event.handler;

import com.culinaryheaven.domain.comment.event.CommentEvent;
import com.culinaryheaven.global.notification.FcmNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentEventHandler {

    private final FcmNotificationService fcmNotificationService;

    @TransactionalEventListener
    public void sendCommentNotification(CommentEvent commentEvent) {
        log.info("Comment event: {}", commentEvent);
        fcmNotificationService.sendCommentNotification(commentEvent);
    }

}
