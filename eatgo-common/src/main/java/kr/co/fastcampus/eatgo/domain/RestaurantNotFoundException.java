package kr.co.fastcampus.eatgo.domain;

public class RestaurantNotFoundException extends RuntimeException { //기존에 있는 runtimeexception상속하기

    public RestaurantNotFoundException(Long id){
        super("Could not find restaurant"+id);  //메시지추가
    }
}
