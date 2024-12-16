package com.culinaryheaven.domain.recipe.event.handler;

import com.culinaryheaven.domain.recipe.event.RecipeLikeEvent;
import com.culinaryheaven.global.notification.FcmNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecipeEventHandler {

    private final FcmNotificationService fcmNotificationService;

    @TransactionalEventListener
    public void sendRecipeLikeNotification(RecipeLikeEvent recipeLikeEvent) {
        fcmNotificationService.sendNotification("누군가 나의 레시피에 좋아요를 눌렀어요!", recipeLikeEvent.recipeTitle(), recipeLikeEvent.targetUserId());
    }

}
