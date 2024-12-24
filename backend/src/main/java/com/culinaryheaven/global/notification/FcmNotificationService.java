package com.culinaryheaven.global.notification;

import com.culinaryheaven.domain.device.domain.DeviceToken;
import com.culinaryheaven.domain.device.repository.DeviceTokenRepository;
import com.culinaryheaven.domain.comment.event.CommentEvent;
import com.culinaryheaven.domain.recipe.event.RecipeLikeEvent;
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
    private static final String RECIPE_ID_KEY = "recipeId";

    public void sendRecipeLikeNotification(RecipeLikeEvent event) {

        DeviceToken deviceToken = deviceTokenRepository.findByUserId(event.targetUserId()).orElseThrow(
                () -> new CustomException(ErrorCode.DEVICE_TOKEN_NOT_FOUND)
        );

        try {
            Notification notification = makeNotification("누군가 나의 레시피에 좋아요를 눌렀어요!", event.recipeTitle());
            Message message = Message.builder()
                    .setNotification(notification)
                    .setToken(deviceToken.getToken())
                    .putData(RECIPE_ID_KEY, event.recipeId().toString())
                    .build();
            FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            log.error(e.getMessage());
        }

    }

    public void sendCommentNotification(CommentEvent event) {
        DeviceToken deviceToken = deviceTokenRepository.findByUserId(event.targetUserId()).orElseThrow(
                () -> new CustomException(ErrorCode.DEVICE_TOKEN_NOT_FOUND)
        );

        try {
            Notification notification = makeNotification("누군가 나의 레시피에 댓글을 남겼어요!", event.comment());
            Message message = Message.builder()
                    .setNotification(notification)
                    .setToken(deviceToken.getToken())
                    .putData(RECIPE_ID_KEY, event.recipeId().toString())
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
