package com.nsr.webservice.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/*
 * @EnableJpaAuditing
 * : JPA Auditing 활성화 어노테이션
 *      ㄴ> JPA Auditing
 *          : Spring Data JPA 에서 시간에 대해서 자동으로 값 설정
 * @SpringBootApplication
 * : 스프링부트 및 스프링 bean 관련(읽기, 생성) 자동 설정 어노테이션
 * : 해당 위치부터 설정 읽기 -> 최상단에 위치 필수
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        /*
         * SpringApplication.run
         * : 내장 WAS 실행
         */
        SpringApplication.run(Application.class, args);
    }
}
