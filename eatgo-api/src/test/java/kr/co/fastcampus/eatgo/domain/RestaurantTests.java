package kr.co.fastcampus.eatgo.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


public class RestaurantTests  {
    @Test
    public void creation(){
//        Restaurant restaurant=new Restaurant(1004L, "Bob zip","Seoul"); //Restaurant클래스에서 @allargs~를 했기 때문에 이 매개변수+ 리스트매개변수까지 등록해야함.
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build(); //생성자를 사용하지 않음. 각 매개변수가 무엇을 뜻하는지 명확히 할 수 있음(@Builder 추가 필요)
        assertThat(restaurant.getId(),is(1004L));
        assertThat(restaurant.getName(),is("Bob zip"));
        assertThat(restaurant.getAddress(),is("Seoul"));
    }

    @Test
    public void information(){
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();

        assertThat(restaurant.getInformation(),is("Bob zip in Seoul"));
    }

}