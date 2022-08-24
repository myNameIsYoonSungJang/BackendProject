package com.example.jabdongsani.service;

import com.example.jabdongsani.dto.ArticleForm;
import com.example.jabdongsani.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 해당 클래스는 스프링 부트와 연동되어 테스팅 된다.
// 클래스로 test 전체 실행을 할 경우 데이터를 변형시키는(생성, 삭제 등) 메서드에는 @Transactional 어노테이션을 꼭 사용해주어야 한다.
class ArticleServiceTest {

    @Autowired ArticleService articleService;

    // 에러가 난다면 File - setting -> gradle 검색! -> build,run 그리고 test를 다 gradle(default)에서 intellij로 바꿔준다.
    @Test // 해당 메서드가 테스트를 위한 코드라는걸 나타내주는 어노테이션
    void index() { // 데이터의 생성이나 삭제가 되는 test가 있는 경우, @transactional로 묶어주지 않으면 데이터의 변형이 발생하여 test에러가 날 수 있다.
        // 예상
        Article a = new Article(1L, "가가가가", "1111");
        Article b = new Article(2L, "나나나나", "2222");
        Article c = new Article(3L, "다다다다", "3333");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a,b,c));
        // 실제
        List<Article> articles = articleService.index();
        // 비교, 검증
        assertEquals(expected.toString(), articles.toString());
    }

    @Test
    void show_성공____존재하는_id_입력() {
        // 예상
        Long id = 1L;
        Article expected = new Article(id, "가가가가", "1111");
        // 실제
        Article article = articleService.show(id);
        // 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void show_실패____존재하지_않는_id_입력() {
        // 예상
        Long id = -1L;
        Article expected = null;
        // 실제
        Article article = articleService.show(id);
        // 비교
        assertEquals(expected, article);
//        assertEquals(expected.toString(), article.toString());
//        null은 toString으로 비교할수 없기 때문에, 그냥 비교한다.
    }

    @Test
    @Transactional
    void create_성공____title과_content만_있는_dto_입력() {
        // 예상
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(4L, title, content);
        // 실제
        Article article = articleService.create(dto);
        // 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void create_실패____id가_포함된_dto_입력() {
        // 예상
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(4L, title, content);
        Article expected = null;
        // 실제
        Article article = articleService.create(dto);
        // 비교
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void update_성공____존재하는_id와_title_content가_있는_dto_입력() {
        // 예상 데이터 생성 및 update에 전해줄 dto 생성.
        Long id = 1L;
        String title = "ㅋㅋㅋㅋ";
        String content = "4444";
        ArticleForm dto = new ArticleForm(id, title, content);
        // 예상 데이터.
        Article expected = new Article(id, title, content);
        // 실제 update메서드를 통하여 바뀌는 데이터.
        Article article = articleService.update(id, dto);
        // 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void update_성공____존재하는_id와_title만_있는_dto_입력() {
        // 예상 데이터를 얻기위한 데이터 준비 과정.
        Long id = 1L; // 해당 id값을 가진 데이터로 예상 데이터를 만들어주면 됨.
        String title = "ㅋㅋㅋㅋ";
        String content = null;
        // 실제 데이터를 생성하기 위한 dto 생성
        ArticleForm dto = new ArticleForm(id, title , content);
        // 실제 데이터.
        Article article = articleService.update(id, dto);
        // 예상 데이터.
        Article expected = new Article(id, title, "1111"); // test할 id값을 1로 정했기 때문에 content에 1111을 넣어준다.
        // 어짜피 테스트 데이터이기 때문에 값을 알고 있다면 repository를 통해 값을 가져와서 patch를 사용할 필요 없이 그냥 값을 지정해주면 된다. (Test이기 때문에!!!!)
        // 비교.
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void update_실패____존재하지_않는_id의_dto_입력() {
        // 예상 데이터를 얻기위한 데이터 준비 과정.
        Long id = -1L;
        String title = "ㅋㅋㅋㅋ";
        String content = "1234";
        // 실제 데이터를 생성하기 위한 dto 생성
        ArticleForm dto = new ArticleForm(id, title , content);
        // 실제 데이터.
        Article article = articleService.update(id, dto);
        // 예상 데이터.
        Article expected = null;
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void update_실패____id만_있는_dto_입력() {
        // 예상 데이터를 얻기위한 데이터 준비 과정.
        Long id = 1L;
        // 실제 데이터를 생성하기 위한 dto 생성
        ArticleForm dto = new ArticleForm(id, null , null);
        // 실제 데이터.
        Article article = articleService.update(id, dto);
        // 예상 데이터.
        Article expected = new Article(id, "가가가가", "1111");
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void delete_성공____존재하는_id_입력() {
        // 예상 데이터를 얻기위한 데이터 준비 과정.
        Long id = 1L;
        // 실제 데이터.
        Article article = articleService.delete(id);
        // 예상 데이터.
        Article expected = new Article(id, "가가가가", "1111");
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void delete_실패____존재하지_않는_id_입력() {
        // 예상 데이터를 얻기위한 데이터 준비 과정.
        Long id = -1L;
        // 실제 데이터.
        Article article = articleService.delete(id);
        // 예상 데이터.
        Article expected = null;
        assertEquals(expected, article);
    }
}