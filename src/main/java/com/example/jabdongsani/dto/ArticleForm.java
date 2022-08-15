package com.example.jabdongsani.dto;

import com.example.jabdongsani.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor //생성자를 어노테이션으로 대체
@ToString //toString을 어노테이션으로 대체
//lombok의 기능덕에 가능한 문법   *Refactoring*
public class ArticleForm {//form데이터를 받아올 그릇 dto클래스 라고도 부름

    //new.mustache 파일 안에 submit했을때 전송되는 데이터 form 데이터 형식은 input과 textarea가 있는데 이들의 이름을 각각 정해주고 넘겨졌을때,
    //이 ArticleForm이라는 class안에 생성자를 통하여 데이터를 받을 수 있게 해준다.
    private Long id; //id 필드 추가.
    private String title;
    private String content;

    public Article toEntity() {
        // dto 객체를 생성하는 class를 가져와서 DB가 알아들을 수 있게 잘 규격화된 데이터로 만들어서 return 해준다.
        return new Article(id, title, content);
        // 원래는 id가 필요없어서 null값을 주었지만, 수정페이지를 거칠때, 해당 Article의 id값도 숨겨서 같이 보내기 때문에 id값이 필요하다
    }
}
