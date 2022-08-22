package com.example.jabdongsani.api;

import com.example.jabdongsani.dto.ArticleForm;
import com.example.jabdongsani.entity.Article;
import com.example.jabdongsani.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// RestApi가 필요한 이유 -> 앞으로 끝없이 나올 다양한 기기들에 적절히 대응하기 위해 필요하다.
// 웹서버의 자원을 클라이언트에 구애받지않고 http를 통해 서버의 자원을 다루게하는 기술.
// 서버는 모든 기기, 기술에 통용될수 있는 화면이 아닌! 데이터를 반환한다.(JSON형식)

@Slf4j
@RestController // RestApi용 컨트롤러 -> 데이터(JSON)을 반환
// http 요청과 응답을 통한 RestApi
public class ArticleApiController {

    @Autowired // DI -> 디펜던시 인젝션 -> 외부에서 가져온다, 끌어온다.
    private ArticleRepository articleRepository;

    // GET
    @GetMapping("api/articles")
    public List<Article> index(){
        return articleRepository.findAll();
    }
    @GetMapping("api/articles/{id}")
    public Article index(@PathVariable Long id){
        return articleRepository.findById(id).orElse(null);
    }
    // POST
    @PostMapping("api/articles")
    public Article create(@RequestBody ArticleForm dto){
        // dto객체를 불러온다. 원래는 그냥 dto객체로 불러오면 된다.
        // 하지만 RestApi에서 JSON으로 던진 데이터는 그냥 받아지지 않는다.
        // @RequestBody 어노테이션을 이용해 http의 request 부분에서 body의 데이터를 불러와야 데이터를 받을수 있다.
        Article article = dto.toEntity(); // DB에 저장하기 위해 entity 변환
        return articleRepository.save(article); // DB에 저장
    }
    // PATCH
    @PatchMapping("api/articles/{id}")
    //public Article update(@PathVariable Long id, @RequestBody ArticleForm dto){
    // 원래 코드는 이거지만 나중에 400 상태코드를 위해 데이터를 Article 타입으로 담아 보내줘야 한다.
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){ // 상태코드 표현 가능! 같이 실어서 보낸다.
        // 1. 수정용 엔티티 생성
        Article article = dto.toEntity();
        log.info("id : {}, article : {}", id, article.toString()); // 각각의 값이 {} 빈 괄호 안으로 대응된다.
        // 2. 대상 엔티티 조회
        Article target = articleRepository.findById(id).orElse(null);
        // 3. 잘못된 요청 처리(대상이 없거나, id가 다른 경우)
        if(target == null || id != article.getId()){
            // 400, 잘못된 요청 응답
            log.info("잘못된 요청! id : {}, article : {}", id, article.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        // 4. 업데이트 및 정상 응답(200)
        target.patch(article);
        Article updated = articleRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated); // 데이터의 정상적인 요청과 응답에는 body에 데이터를 실어서 보낸다.
    }
    //DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        // 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);
        // 잘못된 요청 처리
        if(target == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        // 대상 삭제
        articleRepository.delete(target);
        // 데이터 반환
        return ResponseEntity.status(HttpStatus.OK).build(); // body(null)로 줘도 되고 build()로 보내도 된다.
    }
}
