package com.nsr.webservice.springboot.web;

import com.nsr.webservice.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

/*
 * @RunWith
 * : JUnit에 내장된 실행자 외의 실행자를 실행
 * @WebMvcTest
 * : Spring MVC에 집중된 어노테이션
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
public class HelloControllerTest {

    /*
     * MockMvc
     * : 웹 API 테스트시 사용 - HTTP, GET, POST 등에 대한 API 테스트 가능하게 함.
     * : 스프링 MVC 테스트 시작점
     */
    @Autowired
    private MockMvc mvc;

    @WithMockUser(roles = "USER")
    @Test
    public void returnHello() throws Exception{
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @WithMockUser(roles = "USER")
    @Test
    public void returnHelloDto() throws Exception {
        String name = "hello";
        int amount = 1000;

        /*
         * jsonPath(String, Matcher<T>)
         * : JSON 응답값을 필드별로 검증하는 메서드
         * : $ 기준으로 필드명 명시
         */
        mvc.perform(
                    get("/hello/dto")
                        .param("name", name)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)))
                ;
    }
}
