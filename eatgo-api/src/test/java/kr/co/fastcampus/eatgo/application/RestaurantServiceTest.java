package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class RestaurantServiceTest {    //일반적인 테스트라서 autowired로 의존관계를 주입해줄 수 없음(?) => 직접 repository와 연결하도록

    private RestaurantService restaurantService;
    private RestaurantRepository restaurantRepository;
    private MenuItemRepository menuItemRepository;

    @Before// 모든 테스트가 실행되기 전에 아래를 반드시 실행
    public void setUp(){
        restaurantRepository=new RestaurantRepositoryImpl();
        menuItemRepository=new MenuItemRepositoryImpl();
        restaurantService=new RestaurantService(restaurantRepository,menuItemRepository);
        //직접 넣어줌(서비스의 레파지토리로 레스토랑레&메뉴 레파지토리를 넣어줌)
    }

    @Test
    public void getRestaurant(){        //레스토랑 정보 얻기
        Restaurant restaurant=restaurantService.getRestaurant(1004L);       //rest~Service -> field로 만들어줌

        assertThat(restaurant.getId(),is(1004L));   //assertthat import >> junit

        MenuItem menuItem=restaurant.getMenuItems().get(0);

        assertThat(menuItem.getName(), is("Kimchi"));
    }


    @Test
    public void getRestaurants(){        //레스토랑 정보 얻기
        List<Restaurant> restaurants=restaurantService.getRestaurants();
        Restaurant restaurant = restaurants.get(0); //첫번째를 get
        assertThat(restaurant.getId(),is(1004L));     //변수를 refactor, / 첫번째의 id가 1004인지 확인

    }
}