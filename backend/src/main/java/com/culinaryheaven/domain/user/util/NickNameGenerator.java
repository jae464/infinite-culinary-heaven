package com.culinaryheaven.domain.user.util;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class NickNameGenerator {

    private static final List<String> adjectives = List.of(
            "빠른", "용감한", "슬기로운", "즐거운", "현명한", "강한", "빛나는", "푸른", "붉은", "검은",
            "활기찬", "조용한", "차분한", "날렵한", "우아한", "열정적인", "화려한", "고요한", "대담한", "독창적인"
    );

    private static final List<String> nouns = List.of(
            "호랑이", "독수리", "용", "늑대", "사자", "뱀", "곰", "여우", "돌고래", "표범",
            "까마귀", "부엉이", "토끼", "하이에나", "코끼리", "치타", "펭귄", "고래", "판다", "코알라"
    );

    public static String generateNickName() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        String adjective = adjectives.get(random.nextInt(adjectives.size()));
        String noun = nouns.get(random.nextInt(nouns.size()));

        return adjective + " " + noun;
    }

}
