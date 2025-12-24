package com.learnpath.security;

import com.learnpath.entity.User;
import com.learnpath.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    
    private final UserRepository userRepository;
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        
        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo userInfo = new OAuth2UserInfo(oauth2User.getAttributes());
        
        Optional<User> existingUser = userRepository.findByProviderAndProviderId(provider, userInfo.getId());
        
        User user;
        if (existingUser.isPresent()) {
            user = existingUser.get();
            user.setName(userInfo.getName());
            user.setPicture(userInfo.getPicture());
            userRepository.save(user);
        } else {
            // Check if user exists with same email
            Optional<User> userByEmail = userRepository.findByEmail(userInfo.getEmail());
            if (userByEmail.isPresent()) {
                user = userByEmail.get();
                user.setProvider(provider);
                user.setProviderId(userInfo.getId());
                user.setPicture(userInfo.getPicture());
                userRepository.save(user);
            } else {
                user = User.builder()
                        .name(userInfo.getName())
                        .email(userInfo.getEmail())
                        .picture(userInfo.getPicture())
                        .provider(provider)
                        .providerId(userInfo.getId())
                        .build();
                userRepository.save(user);
            }
        }
        
        return oauth2User;
    }
}
