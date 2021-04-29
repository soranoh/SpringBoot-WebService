package com.nsr.webservice.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
/*
 * 보통 Dao 라고 생각하는 DB Layer 접근자
 * JpaRepository<Entity 클래스 타입, PK 타입> 을 상속하면 기본적인 CRUD 생성됨
 * @Repository 생성 x
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {
}
