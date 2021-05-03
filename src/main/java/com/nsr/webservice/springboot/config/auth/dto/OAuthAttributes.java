package com.nsr.webservice.springboot.config.auth.dto;

import com.nsr.webservice.springboot.domain.user.Role;
import com.nsr.webservice.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
        private Map<String, Object> attributes;
        private String nameAttributeKey;
        private String name;
        private String email;
        private String picture;


        @Builder
        public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
            this.attributes = attributes;
            this.nameAttributeKey = nameAttributeKey;
            this.name = name;
            this.email = email;
            this.picture = picture;
        }

        /*
         * OAUth2User 에서 반환하는 사용자 정보가 Map 이기 때문에 값 변환
         */
        public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {

            if("naver".equals(registrationId)) {
                return ofNaver("id", attributes);
            }
            return ofGoogle(userNameAttributeName, attributes);
        }

        private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");

            return OAuthAttributes.builder()
                    .name((String)attributes.get("name"))
                    .email((String)attributes.get("email"))
                    .picture((String)attributes.get("profile_image"))
                    .attributes(response)
                    .nameAttributeKey(userNameAttributeName)
                    .build();
        }

        private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
            return OAuthAttributes.builder()
                    .name((String)attributes.get("name"))
                    .email((String)attributes.get("email"))
                    .picture((String)attributes.get("picture"))
                    .attributes(attributes)
                    .nameAttributeKey(userNameAttributeName)
                    .build();
        }

        /*
         * User 엔티티 생성
         * OAuthAttributes 에서 엔티티 생성 시점 - 처음 가입시
         * 가입시 기본 권한 - GUEST 로 주기 위해 role 빌더값에 Role.GUEST 지정
         * OAUthAttributes 클래스 생성 끝날 경우 SessionUser 클래스 생성
         */
        public User toEntity() {
            return User.builder()
                    .name(name)
                    .email(email)
                    .picture(picture)
                    .role(Role.GUEST)
                    .build();
        }
}
