package com.culinaryheaven.global.notification;

import com.culinaryheaven.domain.device.domain.DeviceToken;
import com.culinaryheaven.domain.device.repository.DeviceTokenRepository;
import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.ErrorCode;
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

    private final DeviceTokenRepository deviceTokenRepository;

    public void sendRecipeLikeNotification(String title, String body, Long userId, Long recipeId) {

        DeviceToken deviceToken = deviceTokenRepository.findByUserId(userId).orElseThrow(
                () -> new CustomException(ErrorCode.DEVICE_TOKEN_NOT_FOUND)
        );

        try {
            Notification notification = makeNotification(title, body);
            Message message = Message.builder()
                    .setNotification(notification)
                    .setToken(deviceToken.getToken())
                    .putData("recipeId", recipeId.toString())
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
