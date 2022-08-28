package com.example.jabdongsani.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne // 해당 댓글 entity 여러개가, 하나의 부모 Article에 연결된다.
    @JoinColumn(name = "article_id") // 테이블에서 연결된 대상의 정보의 컬럼을 article_id로 하겠다. 라는 의미.
    private Article article; // 댓글의 부모 게시글
    @Column
    private String nickname;
    @Column
    private String body;


}
