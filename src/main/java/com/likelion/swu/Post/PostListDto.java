package com.likelion.swu.Post;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostListDto {

    private Long id;
    private String title;
    private LocalDateTime date;
    private RequestStatus request;

    private static ModelMapper modelMapper = new ModelMapper();
    public static PostListDto of(PostList postList){return modelMapper.map(postList, PostListDto.class);}
}
