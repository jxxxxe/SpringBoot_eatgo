package kr.co.fastcampus.eatgo.Interfaces;

import kr.co.fastcampus.eatgo.domain.RestaurantNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

//밑에 모조리 추가
@ControllerAdvice
public class RestaurantErrorAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)   //404를 직접 돌려줌
    @ExceptionHandler(RestaurantNotFoundException.class)
    public String handleNotFound(){
        return "{}";
        //notfound에 대해서 처리
    }
}
