package com.example.jabdongsani.repository;

import com.example.jabdongsani.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Article, Long> {//Crud = create, read, update, delete
    //<>안에 첫번째 파라미터로 관리대상 entity 그리고, 두번째는 대표값의 타입을 넣어준다. Article의 대표값은 id 타입은 Long

    @Override
    ArrayList<Article> findAll();
    // Iterable<Article> findAll(); 원래의 코드는 이렇게 짜여져 있다.
    // 하지만 우리는 우리에게 익숙한 List형식으로 변환하기 위해 캐스팅을 ArrayList 형식으로 Override 해준다.
}
