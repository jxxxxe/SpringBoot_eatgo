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

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Before// 모든 테스트가 실행되기 전에 아래를 반드시 실행
    public void setUp(){
        MockitoAnnotations.initMocks(this);     //실제 mock객체를 할당하여 초기화 해야함

        mockRestaurantRepository();
        mockMenuItemRepository();
        mockReviewRepository();

//        restaurantRepository=new RestaurantRepositoryImpl();      //가짜 객체를 사용함으로 진짜객체는 필요없음
//        menuItemRepository=new MenuItemRepositoryImpl();
        restaurantService=new RestaurantService(restaurantRepository,menuItemRepository,reviewRepository);
        //직접 넣어줌(서비스의 레파지토리로 레스토랑레&메뉴 레파지토리를 넣어줌)
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
        //findAll과 findById가 각각 restaurants, restaurant를 올바르게 반환하도록 해야함

    }

    private void mockMenuItemRepository() {
        List<MenuItem> menuItems=new ArrayList<>();
        menuItems.add(MenuItem.builder()
                .name("Kimchi")
                .build());
        given(menuItemRepository.findAllByRestaurantId(1004L)).willReturn(menuItems);
    }

    private void mockReviewRepository() {
        List<Review> reviews=new ArrayList<>();
        reviews.add(Review.builder()
                .name("JOKER")
                .score(5)
                .description("Great!")
                .build());

        given(reviewRepository.findAllByRestaurantId(1004L)).willReturn(reviews);
    }

    //이렇게 service에만 집중하고 repository를 가짜로 만듦으로써 실제 repository의 구현과 상관없이 서비스를 어떻게 활용할 것인지 확인 가능

    @Test
    public void getRestaurantWithExisted(){        //레스토랑 정보 얻기
        Restaurant restaurant=restaurantService.getRestaurant(1004L);

        verify(menuItemRepository).findAllByRestaurantId(1004L);
        verify(reviewRepository).findAllByRestaurantId(1004L);

        assertThat(restaurant.getId(),is(1004L));   //assertthat import >> junit

        MenuItem menuItem=restaurant.getMenuItems().get(0);

        assertThat(menuItem.getName(), is("Kimchi"));

        Review review=restaurant.getReviews().get(0);

        assertThat(review.getDescription(),is("Great!"));
    }

    @Test(expected = RestaurantNotFoundException.class) //요청만 해도 예외발생(=~.class실행) 원함
    public void getRestaurantWithNotExisted(){
        restaurantService.getRestaurant(404L);
    }



    @Test
    public void getRestaurants(){        //레스토랑 정보 얻기
        List<Restaurant> restaurants=restaurantService.getRestaurants();
        Restaurant restaurant = restaurants.get(0); //첫번째를 get
        assertThat(restaurant.getId(),is(1004L));     //변수를 refactor, / 첫번째의 id가 1004인지 확인

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
//        Restaurant saved =  Restaurant.builder()
//                .id(1234L)
//                .name("BeRyong")
//                .address("Busan")
//                .build();
//
//       given(restaurantRepository.save(any())).willReturn(saved);
//          >> saved+given = 맨위에 given~로 대체

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