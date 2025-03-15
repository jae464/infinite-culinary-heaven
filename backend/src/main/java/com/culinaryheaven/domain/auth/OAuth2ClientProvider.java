package com.culinaryheaven.domain.auth;

import com.culinaryheaven.domain.auth.domain.OAuth2Type;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OAuth2ClientProvider {

    public Map<OAuth2Type, OAuth2Client> clients;

    public OAuth2ClientProvider(Set<OAuth2Client> clients) {
        this.clients = clients.stream()
                .collect(Collectors.toMap(OAuth2Client::getOAuth2Type, client -> client));
    }

    public OAuth2Client getClient(OAuth2Type type) {
        return clients.get(type);
    }
}
