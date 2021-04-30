package com.nsr.webservice.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*
 * 보통 Dao 라고 생각하는 DB Layer 접근자
 * JpaRepository<Entity 클래스 타입, PK 타입> 을 상속하면 기본적인 CRUD 생성됨
 * @Repository 생성 x
 *
 * @Query
 * : SpringDataJpa 에서 제공하지 않는 메서드 직접 작성 가능하게 하는 어노테이션션 */
public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}
