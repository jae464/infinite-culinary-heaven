package com.culinaryheaven.global.notification;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmNotificationService {

    public void sendMessage(String title, String body, String token) {
        try {
            Notification notification = makeNotification(title, body);
            Message message = Message.builder()
                    .setNotification(notification)
                    .setToken(token)
                    .build();
            FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            log.error(e.getMessage());
        }
    }

    private Notification makeNotification(final String title, final String body) {
        return Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();
    }
}
