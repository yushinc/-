package com.likelion.swu.Post;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityNotFoundException;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;

//    //게시글 생성
//    @PostMapping(value = "/posts")
//    public ResponseEntity<PostReturnDto> createPost(@RequestBody @Valid PostFromDto postFromDto,
//                                                    @RequestBody Long id,
//                                                    @RequestParam("building") Building building) {
//        try {
//            Post post = postService.createPost(postFromDto,building,id);
//            // 게시글 작성 성공 시, 201 Created 상태코드와 생성된 게시글 정보 반환
//            PostReturnDto postReturnDto = convertToDto(post); //생성한 객체 정보 다시 returndto로 넘김, 조회를 위해
//            return ResponseEntity.status(HttpStatus.CREATED).body(postReturnDto);
//        } catch (Exception e) {
//            // 게시글 작성 실패 시, 500 Internal Server Error 반환
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
@PostMapping(value = "/posts")
public ResponseEntity<PostReturnDto> createPost(@RequestBody @Valid PostFromDto postFromDto,
                                                @RequestParam("building") Building building) {
    try {
        Post post = postService.createPost(postFromDto,building);
        // 게시글 작성 성공 시, 201 Created 상태코드와 생성된 게시글 정보 반환
        PostReturnDto postReturnDto = convertToDto(post); //생성한 객체 정보 다시 returndto로 넘김, 조회를 위해
        return ResponseEntity.status(HttpStatus.CREATED).body(postReturnDto);
    } catch (Exception e) {
        // 게시글 작성 실패 시, 500 Internal Server Error 반환
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}

    //게시글 수정
    @PutMapping(value = "/posts/edit/{postId}")
    public ResponseEntity<PostReturnDto> updatePost(@PathVariable("postId") Long postId,
                                                    @RequestBody @Valid PostFromDto postFromDto) {
        Post post = postRepository.findById(postId).orElseThrow(EntityNotFoundException::new);
        postService.updatePost(postFromDto);

        Post updatedPost = postRepository.save(post);
        PostReturnDto postReturnDto = convertToDto(updatedPost);
        return ResponseEntity.ok(postReturnDto);
    }

    //게시글 삭제
    @DeleteMapping("/posts/edit/{postId}")
    public ResponseEntity<HttpStatus> deletePost(@PathVariable("postId") Long postId) {
        PostFromDto postFromDto = new PostFromDto();
        postFromDto.setId(postId);

        postService.deletePost(postFromDto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //게시글 목록 조회
    @GetMapping("/posts")
    public ResponseEntity<List<PostListDto>> getAllPosts(@RequestParam(value = "building", required = false) Building building) {
        List<PostListDto> filteredPosts = postService.getAllPosts(building);
        return ResponseEntity.ok(filteredPosts);
    }

    //게시글 상세 조회
    @GetMapping(value = "posts/{postId}")
    public ResponseEntity<PostReturnDto> getPostDtl(@PathVariable("postId") Long postId){
        PostReturnDto postReturnDto = postService.getPostDetails(postId);
        return ResponseEntity.status(HttpStatus.OK).body(postReturnDto);
    }

    private PostReturnDto convertToDto(Post post) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(post, PostReturnDto.class);
    }
}
