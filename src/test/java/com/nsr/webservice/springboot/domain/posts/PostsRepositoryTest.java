package com.nsr.webservice.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

/*
 * @SpringBootTest
 * : 별다른 설정 없을 경우 H2 DB 자동 실행
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    /*
     * @After
     * : 단테 끝날 때마다 수행되는 메서드 지정
     * : 여러 테스트 수행시 H2에 데이터가 남아있을 수 있어 데이터 침범 방지를 위해 delete
     */
    @After
    public void cleanup() {
        /*
         * postsRepository.deleteAll()
         * : posts 테이블의 데이터 전체 delete
         */
        postsRepository.deleteAll();
    }

    @Test
    public void getPostsList() {
        //given
        String title = "게시글 제목";
        String content = "게시글 본문";

        /*
         * postsRepository.save()
         * : posts 테이블에 insert/update 쿼리 실행
         * : id 값이 있다면 update, 없다면 insert
         */
        postsRepository.save(Posts.builder()
                                    .title(title)
                                    .content(content)
                                    .author("sora@test.com")
                                    .build());

        /*
         * postsRepository.findAll()
         * : posts 테이블 데이터 전체 select
         */
        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }
}
