package com.likelion.swu.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "postList")
public class PostList {
    @Id
    @Column(name = "postList")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

//    @ManyToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name="user_id")
//    private User user;
    private LocalDateTime date;
    @Enumerated(EnumType.STRING)
    private RequestStatus request;
}
