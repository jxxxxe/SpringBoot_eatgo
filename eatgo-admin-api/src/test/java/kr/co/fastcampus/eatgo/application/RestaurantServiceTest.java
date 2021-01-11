package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


public class RestaurantServiceTest {    //일반적인 테스트라서 autowired로 의존관계를 주입해줄 수 없음(?) => 직접 repository와 연결하도록

    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;  // 실제 테스트 하려는 restaurantservice를 제외한 repository등은 관련 없음=>가짜객체로

    @Before// 모든 테스트가 실행되기 전에 아래를 반드시 실행
    public void setUp(){
        MockitoAnnotations.initMocks(this);     //실제 mock객체를 할당하여 초기화 해야함

        mockRestaurantRepository();

//        restaurantRepository=new RestaurantRepositoryImpl();      //가짜 객체를 사용함으로 진짜객체는 필요없음

        restaurantService=new RestaurantService(restaurantRepository);
    }

    private void mockRestaurantRepository() {       //setup안에 할 것을 길어서 따로 메서드로 빼줌
        List<Restaurant> restaurants=new ArrayList<>();
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();
        restaurants.add(restaurant);

        given(restaurantRepository.findAll()).willReturn(restaurants);
        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));
    }

    @Test
    public void getRestaurantWithExisted(){
        Restaurant restaurant=restaurantService.getRestaurant(1004L);

        assertThat(restaurant.getId(),is(1004L));   //assertthat import >> junit
    }

    @Test(expected = RestaurantNotFoundException.class) //요청만 해도 예외발생(=~.class실행) 원함
    public void getRestaurantWithNotExisted(){
        restaurantService.getRestaurant(404L);
    }


    @Test
    public void getRestaurants(){
        List<Restaurant> restaurants=restaurantService.getRestaurants();
        Restaurant restaurant = restaurants.get(0);
        assertThat(restaurant.getId(),is(1004L));
    }



    @Test
    public void addRestaurant(){
        given(restaurantRepository.save(any())).will(invocation ->{
            Restaurant restaurant = invocation.getArgument(0);
            restaurant.setId(1234L);
            return restaurant;
        });

        Restaurant restaurant = Restaurant.builder()
                .name("BeRyong")
                .address("Busan")
                .build();

//       Restaurant saved =  Restaurant.builder().id(1234L).name("BeRyong").address("Busan").build();
//       given(restaurantRepository.save(any())).willReturn(saved); >> saved+given = 맨위에 given~로 대체

        Restaurant created=restaurantService.addRestaurant(restaurant);

        assertThat(created.getId(),is(1234L));
    }

    @Test
    public void updateRestaurant(){
        Restaurant restaurant= Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();

        given(restaurantRepository.findById(1004L))
                .willReturn(Optional.of(restaurant));

       restaurantService.updateRestaurant(1004L,"Sool zip","Busan");

        assertThat(restaurant.getAddress(),is("Busan"));
        assertThat(restaurant.getName(),is("Sool zip"));
    }
}