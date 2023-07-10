package com.likelion.swu.Post;
import com.likelion.swu.User.Account;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name ="post")
public class Post {

    @Id
    @Column(name ="post_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String body;

//    @ManyToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name="user_id")
//    private Account user; //사용자랑 외래키 설정 해줘야함

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private Building building;
    @Enumerated(EnumType.STRING)
    private RequestStatus request;


    public void updatePost(PostFromDto postFromDto) {
        this.title = postFromDto.getTitle();
        this.body = postFromDto.getBody();
        this.date = LocalDateTime.now(); // 현재 시간으로 설정
        //this.building = postFromDto.getBuilding();
        //this.request = postFromDto.getRequest();
    }
}
