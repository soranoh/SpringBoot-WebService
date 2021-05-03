package com.nsr.webservice.springboot.config.auth;

import com.nsr.webservice.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/*
 * @EnableWebSecurity
 * : Spring Security 설정 활성화 어노테이션
 */
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    /*
     * .csrf().disable()
     * : basic auth 를 사용하기 위해 csrf(Cross-Site Request Forgery) 보호 기능 disable
     * .headers().frameOptions().disable()
     * : x-frame-options 비활성화
     * : 대신 보안적인 이슈 발생 가능
     * .authorizeRequests()
     * : URL 별 권한 관리 설정 옵션의 시작점
     * : 선언되어야만 andMatchers 옵션 사용 가능
     * .andMatchers()
     * : 권한 관리 대상 지정 옵션
     * : URL, HTTP 메서드 별로 관리 가능
     * : permitAll() 옵션 - 보안 요구 없이 요청 허용
     * : hasRole(String ~) 옵션 - ~ 에 맞는 권한을 가진 사람만 요청 허용
     * .anyRequest()
     * : 설정된 값 이외의 나머지 URL 설정
     * .authenticated()
     * : 로그인된 사용자가 요청을 수행시 필요
     *      ㄴ> 사용자 인증이 안 되어있을 경우 스프링 시큐리티 필터에서 요청을 잡아내고 사용자를 로그인 페이지로 리다이렉션
     * .logout().logoutSuccessUrl("/")
     * : 로그아웃 설정 시작점
     * : 로그아웃 성공시 설정 주소로 이동
     * .oauth2Login()
     * : OAuth 2 로그인 기능의 설정 진입점
     * .userInfoEndpoint()
     * : OAuth 2 로그인 성공 이후 사용자 정보 가져오는 설정 담당
     * .userService()
     * : 소셜 로그인 성공시 후속 조치 진행할 인터페이스의 구현체 등록
     * : 리소스 서버에서 사용자 정보를 가져온 상태에서 추가로 기능 명시 가능
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/images/**",
                                "/js/**", "/h2-console/**").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);
    }
}
