package com.nsr.webservice.springboot.config.auth.dto;

import com.nsr.webservice.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

/*
 * 세션에 저장하기 위해서는 직렬화 기능을 가진 세션 Dto 에서 정보 사용
 */
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
