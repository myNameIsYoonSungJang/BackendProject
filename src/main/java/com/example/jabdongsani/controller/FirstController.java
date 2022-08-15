package com.example.jabdongsani.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/hi")//hi라는 주소로 접속했을때, 밑에 있는 메서드를 실행시킴.
    public String niceToMeetYou(Model model){//model을 통해 변수에 값을 추가할 수 있다.
        model.addAttribute( "username", "장윤성");//username이라는 변수에 "홍팍"이라는 값을 저장하여 해당 mustache에 추가
        return "greetings";//templates 폴더 안의 "greetings"라는 greetings.mustache라는 파일을 브라우저로 전송해준다.
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model){
        model.addAttribute("nickname", "장윤성");
        return "goodbye";
    }
}
