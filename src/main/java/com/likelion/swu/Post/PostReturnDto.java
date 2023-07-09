package com.likelion.swu.Post;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class PostReturnDto {

    private Long id;
    @NotNull(message = "제목은 필수 값 입니다.")
    private String title;
    private String body;
    //private LocalDateTime date;
    private Building building;
    //private RequestStatus request;
    private static ModelMapper modelMapper = new ModelMapper();

    public Post createPost() { return modelMapper.map(this, Post.class);}

    public static PostFromDto of(Post post) { return modelMapper.map(post, PostFromDto.class);}
}
