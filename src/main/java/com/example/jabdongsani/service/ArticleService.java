package com.example.jabdongsani.service;

import com.example.jabdongsani.dto.ArticleForm;
import com.example.jabdongsani.entity.Article;
import com.example.jabdongsani.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service // 서비스 선언 (서비스 객체를 스프링 부트에 생성)
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if(article.getId() != null)
            return null;
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        // 1. 수정용 엔티티 생성
        Article article = dto.toEntity();
        log.info("id : {}, article : {}", id, article.toString()); // 각각의 값이 {} 빈 괄호 안으로 대응된다.
        // 2. 대상 엔티티 조회
        Article target = articleRepository.findById(id).orElse(null);
        // 3. 잘못된 요청 처리(대상이 없거나, id가 다른 경우)
        if(target == null || id != article.getId() || article.getTitle() == null && article.getContent() == null){
            // 400, 잘못된 요청 응답
            log.info("잘못된 요청! id : {}, article : {}", id, article.toString());
            return null;
        }
        // 4. 업데이트
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }

    public Article delete(Long id) {
        // 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);
        // 잘못된 요청 처리
        if(target == null){
            return null;
        }
        // 대상 삭제
        articleRepository.delete(target);
        // 데이터 반환
        return target;
    }

    @Transactional // 해당 메소드를 트랜잭션으로 묶는다. 하나 실패하면 롤백!
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // 1. dto 묶음을 entity 묶음으로 변환
        List<Article> articleList = dtos.stream().map(dto -> dto.toEntity()).collect(Collectors.toList()); // stream 문법
        // stream 문법을 for문으로 작성했을 때의 코드
//        List<Article> articleList = new ArrayList<>();
//        for(int i = 0; i < dtos.size(); i++){
//            ArticleForm dto = dtos.get(i);
//            Article entity = dto.toEntity();
//            articleList.add(entity);
//        }

        // 2. entity 묶음을 DB로 저장
        articleList.stream().forEach(article -> articleRepository.save(article));
         // stream 문법을 for문으로 작성했을 때의 코드
//          for(int i = 0; i < articleList.size(); i++){
//            Article article = articleList.get(i);
//            articleRepository.save(article);
//        }
        // 3. 강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("결제 실패!")
        );
        // 4. 결과값 반환
        return articleList;
    }
}
