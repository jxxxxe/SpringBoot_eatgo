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


public class RestaurantServiceTest {    //일반적인 테스트라서 autowired로 의존관계를 주입해줄 수 없음(?) => 직접 repository와 연결하도록

    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;  // 실제 테스트 하려는 restaurantservice를 제외한 repository등은 관련 없음=>가짜객체로

    @Mock
    private MenuItemRepository menuItemRepository;

    @Before// 모든 테스트가 실행되기 전에 아래를 반드시 실행
    public void setUp(){
        MockitoAnnotations.initMocks(this);     //실제 mock객체를 초기화 해줌

        mockRestaurantRepository();
        mockMenuItemRepository();

//        restaurantRepository=new RestaurantRepositoryImpl();      //가짜 객체를 사용함으로 진짜객체는 필요없음
//        menuItemRepository=new MenuItemRepositoryImpl();
        restaurantService=new RestaurantService(restaurantRepository,menuItemRepository);
        //직접 넣어줌(서비스의 레파지토리로 레스토랑레&메뉴 레파지토리를 넣어줌)
    }

    private void mockMenuItemRepository() {
        List<MenuItem> menuItems=new ArrayList<>();
        menuItems.add(new MenuItem("Kimchi"));
        given(menuItemRepository.findAllByRestaurantId(1004L)).willReturn(menuItems);
        //menuitems를 올바르게 반환하도록함
    }

    private void mockRestaurantRepository() {       //길어서 따로 메서드로 빼줌
        List<Restaurant> restaurants=new ArrayList<>();
        Restaurant restaurant=new Restaurant(1004L,"Bob zip","Seoul");
        restaurants.add(restaurant);

        given(restaurantRepository.findAll()).willReturn(restaurants);
        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));
        //findAll과 findById가 각각 restaurants, restaurant를 올바르게 반환하도록 함

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

    @Test
    public void addRestaurant(){
        Restaurant restaurant = new Restaurant("BeRyong", "Busan");
        Restaurant saved = new Restaurant(1234L,"BeRyong", "Busan");

        given(restaurantRepository.save(any())).willReturn(saved);

        Restaurant created=restaurantService.addRestaurant(restaurant);

        assertThat(created.getId(),is(1234L));
    }

    @Test
    public void updateRestaurant(){
        Restaurant restaurant=new Restaurant(1004L,"Bob zip","Seoul");

        given(restaurantRepository.findById(1004L))
                .willReturn(Optional.of(restaurant));

       restaurantService.updateRestaurant(1004L,"Sool zip","Busan");

        assertThat(restaurant.getAddress(),is("Busan"));
        assertThat(restaurant.getName(),is("Sool zip"));
    }
}