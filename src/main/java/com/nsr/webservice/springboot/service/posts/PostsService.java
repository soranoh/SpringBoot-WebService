package com.nsr.webservice.springboot.service.posts;

import com.nsr.webservice.springboot.domain.posts.Posts;
import com.nsr.webservice.springboot.domain.posts.PostsRepository;
import com.nsr.webservice.springboot.web.dto.PostsResponseDto;
import com.nsr.webservice.springboot.web.dto.PostsSaveRequestDto;
import com.nsr.webservice.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

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
}
