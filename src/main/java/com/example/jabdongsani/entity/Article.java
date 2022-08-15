package com.example.jabdongsani.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

// ArticleForm에 있는 데이터들을 DB가 인식할 수 있는 Entity 형식으로 변환하기 위해 필요한 Class (Entity Class)
@Entity // DB가 해당 객체를 인식 가능!  => Dto를 Entity로 변환.

@AllArgsConstructor // 생성자 어노테이션
@NoArgsConstructor // default생성자 어노테이션
@ToString // toString 어노테이션
@Getter // 모든 get 요청이 왔을때 get을 처리해줄 어노테이션 Article 타입에서 getXX()가 발생하면 처리해준다.(ex -> getId(), getTitle 각 변수의 앞글자는 대문자 처리.)
//lombok의 기능덕에 가능한 문법   *Refactoring*
public class Article {

    @Id //대표값을 지정해준다. LIKE a 주민등록번호
    @GeneratedValue //1,2,3, .... 자동 생성 어노테이션 (save시점 기준인듯?)
    private Long id; // 기본적으로 Entity에는 대표값이 있어야한다. 서로 이름이나 내용이 같아도 다름을 판별해주는 대표값.

    @Column // DB에서 관리하는 Table의 단위에 연결할 수 있도록 column을 써준다.
    private String title;
    @Column
    private String content;

}
