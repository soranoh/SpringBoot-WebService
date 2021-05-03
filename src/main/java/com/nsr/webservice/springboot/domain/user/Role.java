package com.nsr.webservice.springboot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
 * 사용자 권한을 관리할 Enum 클래스
 * 스프링 시큐리티에선느 권한 코드 앞에 항상 'ROLE_'이 존재
 */
@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;
}
