package com.nsr.webservice.springboot.domain.posts;


import com.nsr.webservice.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * @NoArgsConstructor
 * : 파라미터가 없는 생성자 생성
 * @Entity
 * : 테이블과 링크될 클래스임을 명시
 * : ☆☆☆ Entity 클래스에서는 절대 Setter 메서드 생성 금지
 */
@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

    /*
     * @ Id
     * : 해당 테이블의 PK 임을 명시
     * @GeneratedValue
     * : PK 생성규칙 지정
     * : strategy = GenerationType.IDENTITY 설정 -> auto_increment
     * @Column
     * : 테이블의 컬럼임을 명시(필수x)
     * @Builder
     * : 해당 클래스의 빌더 패턴 클래스 생성
     * : 생성자 상단에 선언할 경우 생성자에 지정된 필드만 빌더에 포함
     *      ㄴ> 빌더 패턴(점층적 생성자 패턴 + 자바 빈즈 패턴)
     *          : 자바 빈트 패턴과 같이 정보를 받고, 데이터의 일관성을 위해 정보를 다 받은 후에 객체를 생성하는 패턴
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title,String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
