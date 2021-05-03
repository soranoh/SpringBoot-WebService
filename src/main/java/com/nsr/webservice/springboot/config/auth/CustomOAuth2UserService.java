package com.nsr.webservice.springboot.config.auth;

import com.nsr.webservice.springboot.config.auth.dto.OAuthAttributes;
import com.nsr.webservice.springboot.config.auth.dto.SessionUser;
import com.nsr.webservice.springboot.domain.user.User;
import com.nsr.webservice.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

/*
 * 구글 로그인 이후 가져온 사용자 정보를 기반으로 가입 및 정보 수정, 세션 저장 등의 기능 지원 클래스
 */
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        /*
         * registrationId
         * : 현재 로그인 진행중인 서비스 구분 코드
         * : 여러 로그인 연동시 구분하기 위해 사용(구글, 카카오, 네이버 ...)
         * userNameAttributeName
         * : OAuth2 로그인 진행시 키가 되는 필드값( = Primary Key)
         * : 구글만 지원(기본 코드 = "sub")
         * OAuthAttributes
         * : OAuth2UserService 를 통해 가져온 OAuth2User 의 attribute 담을 클래스
         * SessionUser
         * : 세션에 사용자 정보를 저장하기 위한 Dto 클래스
         */
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
