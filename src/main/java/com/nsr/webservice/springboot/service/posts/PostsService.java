package com.nsr.webservice.springboot.service.posts;

import com.nsr.webservice.springboot.domain.posts.Posts;
import com.nsr.webservice.springboot.domain.posts.PostsRepository;
import com.nsr.webservice.springboot.web.dto.PostsListResponseDto;
import com.nsr.webservice.springboot.web.dto.PostsResponseDto;
import com.nsr.webservice.springboot.web.dto.PostsSaveRequestDto;
import com.nsr.webservice.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    /*
     * 더티 체킹(Dirty Checking)
     * : 상태 변경 검사
     * : JPA 에서 엔티티 조회상태 그대로의 스냅샷과 비교하여 변경된 내용이 있다면 update query 를 DB로 전달한다.
     * : JPA 의 영속성 컨텍스트 때문(엔티티를 영구 저장하는 환경)
     * : 별도로 update query 날릴 필요가 없다.
     * : 전체 컬럽을 update 하기 때문에 변경 컬럼만 update 하고 싶을 경우 @DynamicUpdate 어노테이션 설정이 필요하다.
     */
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다. id = " + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다. id = " + id));
        return new PostsResponseDto(entity);
    }

    /*
     * readOnly = true
     * : 트랜잭션 범위 유지, 조회 기능만 두어 조회 속도 개선
     */
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다. id = " + id));
        postsRepository.delete(posts);
    }

}
