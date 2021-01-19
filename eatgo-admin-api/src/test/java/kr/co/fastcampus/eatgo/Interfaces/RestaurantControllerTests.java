package kr.co.fastcampus.eatgo.Interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)    //에게 get 실행 요청
@WebMvcTest(RestaurantController.class)     //컨트롤러를 테스트 한다고 명시
public class RestaurantControllerTests {
    @Autowired
    private MockMvc mvc;    //mock오브젝트로 가짜 객체를 만들어서 테스트를 더 간편하게

    @MockBean // 실제 테스트하는 것(여기선 restauarantController) 이외의 것들을 가짜로 대체
    private  RestaurantService restaurantService;
// RestauarantController가 직접적으로 restaurantService를 의존하고 있는데 이를 가짜로 대체=>가짜는 repository를 사용하지 않음
    //가짜 restaurantService도 올바른 결과를 내도록 테스트를 만들어줘야함(밑에 리스트 수정)

    //밑(진짜객체)은 가짜객체로 대체하면서 삭제
//    @SpyBean(RestaurantService.class)           // reposit  ory+menuitem
//    private RestaurantService restaurantService;
//
//    @SpyBean(RestaurantRepositoryImpl.class) //spy뒤에 어떤 구현을 쓸지 적어줘야 함.
//    private RestaurantRepository restaurantRepository;
//    //컨트롤러에 원하는 객체(의존성)을 주입 => 없으면 오류 //리파지토리를 주입
//    //인터페이스가 만들어진 후에 구현(Impl)에서 실제 인터페이스로 변경
//
//    @SpyBean(MenuItemRepositoryImpl.class)
//    private MenuItemRepository menuItemRepository;

    @Test
    public void list() throws Exception {       //throws Exception: perform예외
        List<Restaurant> restaurants=new ArrayList<>();
        restaurants.add(Restaurant.builder()
                .id(1004L)
                .name("JOKER House")
                .address("Seoul")
                .categoryId(1L)
                .build());
        given(restaurantService.getRestaurants()).willReturn(restaurants);
        //여기까지 코드 : 가짜 객체 restaurantService를 통해 가짜 목록을 생성, (마지막)getRestaurants()를 하면 (가짜)레스토랑 목록을 돌려줄것이다
        //가짜객체를 이용하면 실제 service와 상관없이 테스트를 할 수 있음
        //given->BDD MOKITO,

        mvc.perform( get("/restaurants"))    //get 실행요청
               .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1004")))
                .andExpect(content().string(containsString("\"name\":\"JOKER House\"")));
                // content > import2    ,   containsString > import3

    }

    @Test
    public void detailWithExisted() throws Exception {
        Restaurant restaurant=Restaurant.builder()
                .id(1004L)
                .name("JOKER House")
                .address("Seoul")
                .categoryId(1L)
                .build();


        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant);

        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1004")))            //해당 string이 포함되어 있는지 확인
                .andExpect(content().string(containsString("\"name\":\"JOKER House\"")));
    }

    @Test
    public void detailWithNotExisted() throws Exception{ //404: 에러발생으로 설정
        given(restaurantService.getRestaurant(404L)).willThrow(new RestaurantNotFoundException(404L));
                //RestaurantNotFoundException클래스 생성(domain에)

        //실제 에러처리는 RestaurantController에서
        mvc.perform(get("/restaurants/404"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{}")); //Json포맷으로 빈 내용을 나타내고 싶음

    }

    @Test   //가게 추가하기
    public void createWithValidData() throws Exception {        //create -> createWithVlidData : 올바른 데이터로 생성테스트, controller에서 create에 restaurant객체 매개변수에 @valid추가
//        Restaurant restaurant=new Restaurant(1234L,"BeRyong","Seoul");
        given(restaurantService.addRestaurant(any())).will(invocation -> {
            Restaurant restaurant=invocation.getArgument(0);
            return Restaurant.builder()
                    .id(1234L)
                    .name(restaurant.getName())
                    .address(restaurant.getAddress())
                    .categoryId(1L)
                    .build();
        });

        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("  {\"name\":\"Beryong\",\"address\":\"Busan\",\"categoryId\":1}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/restaurants/1234"))
                .andExpect(content().string("{}"));

        verify(restaurantService).addRestaurant(any());
    }

    @Test
    public void createWithInValidData() throws Exception {  // 올바르지 않게 데이터를 입력했을 때 생성test
        //add는 다 없앰
        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("  {\"name\":\"\",\"address\":\"\",,\"categoryId\":}"))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void updateWithValidData() throws Exception {

        mvc.perform(patch("/restaurants/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"JOKER Bar\", \"address\":\"Busan\",\"categoryId\":1}"))
                .andExpect(status().isOk());    //200번
        verify(restaurantService).updateRestaurant(1004L,"JOKER Bar","Busan");
    }

    @Test
    public void updateWithInValidData() throws Exception {

        mvc.perform(patch("/restaurants/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"\", \"address\":\"\",\"categoryId\":}"))
                .andExpect(status().isBadRequest());    //400번
    }

    @Test
    public void updateWithoutName() throws Exception {

        mvc.perform(patch("/restaurants/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"\", \"address\":\"Busan\",\"categoryId\":1}"))
                .andExpect(status().isBadRequest());    //400번
    }
}