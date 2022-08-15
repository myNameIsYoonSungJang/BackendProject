package com.example.jabdongsani.controller;

import com.example.jabdongsani.dto.ArticleForm;
import com.example.jabdongsani.entity.Article;
import com.example.jabdongsani.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j // 로깅을 위한 어노테이션
public class ArticleController {
    @Autowired // spring boot가 미리 생성해놓은 객체를 가져다가 자동으로 연결해준다.
    private ArticleRepository articleRepository; // 뒤에 = new ~~ 안해줘도 되는 이유 @Autowired

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    @PostMapping("/articles/create")//form태그에서 action으로 넘긴 데이터를 PostMapping을 통하여 받음.
    // 아마 form의 action안의 경로와 Mapping안의 경로가 같아야 할 것이다.
    public String createArticle(ArticleForm form){//form으로 전송된 각각의 데이터들이 해당 파라미터의 form이라는 dto 객체로 들어감.
        //데이터를 받아오려면 dto로 (객체) 받아야하는데 ArticleForm class를 이용하여 데이터를 담은뒤 그 객체의 이름을 form으로 사용한다는 뜻.

        log.info(form.toString());
        //System.out.println(); 실제 서버에서 데이터가 잘 들어갔는지 plintln으로 절대 확인하지 않는다.
        //기록에 남지도 않고 서버의 성능에도 상당히 악영향을 끼친다.
        //하여 로깅 기능으로 대체한다.

        //DB에 데이터를 저장하려면 java가 아닌 CB가 알아들을 수 있는 언어로 바꾸어주어야 한다. dto객체를 entity로 바꾸어주는게 이 과정이다.
        //dto는 java언어이고 entity는 DB가 알아들을 수 있게 잘 규격화된 데이터이다.
        //이 entity가 repository란는 것을 통하여 DB에 전달되고 처리된다.
        //1. Dto를 변환! Entity로
        Article article = form.toEntity();
        log.info(article.toString());
        //System.out.println(article.toString());  위에서 설명한 내용처럼 println을 로깅으로 대체


        //2. Repository에게 Entity를 DB안에 저장하게 함!
        Article saved = articleRepository.save(article);//repository를 이용하여 save하여 DB에 데이터를 넣어준다.
        log.info(saved.toString());
        //System.out.println(saved.toString());   위에서 설명한 내용처럼 println을 로깅으로 대체

        //저장이 완료된 후에 return을 해주는데 redirect를 사용한다.
        return "redirect:/articles/" + saved.getId();
        //redirect 어떤 URL로 웹 서버를 요청했을때 다른 URL로 넘겨주는 것을 말한다. url이 연속하여 2~n개만큼 연결 연결되는 구조. (사용자는 1개의 과정으로 보임)
    }

    @GetMapping("/articles/{id}") //변수명을 {}로 감싸서 주소창 혹은 문자열에 변하는 값을 넣어줄 수 있다.
    public String show(@PathVariable Long id, Model model){//위의 {}안의 문자와 PathVariable할 변순는 타입과 이름도 같아야한다.
        // @PathVariable 이라는 어노테이션은 url Path로부터 입력이 된다 라는것을 의미한다. == url데이터를 파라미터로 사용하겠다 -> @PathVariable
        log.info("id = " + id);

        // 1. id를 데이터로 가져옴.
        Article articleEntity = articleRepository.findById(id).orElse(null);// orElse(Null)을 해주지 않는다면 id값을 못찾았을 때, 에러가 남, 해결방법 Optional<Artcle> 타입의 변수로 값을 받으면 가능.
        // id로 찾고 (findById(id)), 찾지 못했다면 Null을 반환해라.(orElse(Null))
        // 2. 가져온 데이터를 모델에 등록.
        model.addAttribute("article", articleEntity);
        // 3. 보여줄 페이지를 설정.
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){
        // 1. 모든 article을 가져온다.
        List<Article> articleEntityList = articleRepository.findAll();
        // findAll()은 iterable타입으로 리턴해주는데 가져온 데이터들을 처리해주기 위한 3가지 방법.
        // 1-1. repository에서 가져온 데이터의 타입 캐스팅을 해준다. (List<Article>)
        // 1-2. iterable타입의 데이터를 받을 변수를 iterable타입으로 캐스팅을 해준다. Iterable<Article>
        // 1-3. articleRepository에서 데이터를 반환할 때, iterable 형식의 데이터를 arrayList 타입으로 변환해준다.
        // ArrayList<ARrticle>로도 받을 수 있지만, 그의 자손 형식인 List 형식으로도 받을 수 있다.

        // 2. 가져온 모든 article 묶음을 뷰로 전달.
        model.addAttribute("articleList", articleEntityList);
        // 3. 뷰 페이지를 설정한다.
        return "articles/index"; // articles/index.mustache 뷰페이지 연결
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        // 1. 수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        // 2. 모델에 데이터 등록
        model.addAttribute("article", articleEntity);
        // 3. 뷰 페이지 설정
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    //원래는 @PatchMapping을 사용하면 되지만, form을 사용해 patch를 지원하지 않으니 post로 대체한것.
    public String update(ArticleForm form){
        log.info(form.toString());

        // 1. DTO를 Entity로 변경.
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());
        // 2. Entity를 DB에 저장.
        // 2-1. DB에서 기존 데이터를 가져옴.
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        // 2-2. 기존 데이터의 값을 수정, 갱신한다.
        if (target != null){
            articleRepository.save(articleEntity); // Entity가 DB로 갱신. 덮어씌우기.
        }
        // 3. 수정 결과 페이지로 redirect.
        return "redirect:/articles/" + articleEntity.getId();
    }
}
