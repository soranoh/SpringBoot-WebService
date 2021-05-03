package com.nsr.webservice.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * @Target(ElementType.PARAMETER)
 * : 어노테이션 생성 위치 지정
 * : PARAMETER 로 지정 -> 메서드의 파라미터로 선언된 객체에서만 사용 가능
 * @interface
 * : 해당 파일을 어노테이션 클래스로 지정
 * @Retention(RetentionPolicy.RUNTIME)
 * : 컴파일 이후에도 JVM 에 의해 참조 가능
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
}
