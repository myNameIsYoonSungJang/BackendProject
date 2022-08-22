package com.example.jabdongsani.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 일반 컨트롤러는 view탬플릿을 반환하지만 RestController는 JSON을 반환한다. -> 데이터 반환 (view 탬플릿 X)
public class FirstApiController {
    @GetMapping("api/hello")
    public String hello(){
        return "hello world!";
    }

}
