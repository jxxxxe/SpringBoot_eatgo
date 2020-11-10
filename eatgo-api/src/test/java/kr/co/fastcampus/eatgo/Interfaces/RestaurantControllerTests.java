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
    //가짜 restaurantService도 올바른 결과를 내도록 테스트를 만들어줘야함

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
        restaurants.add(new Restaurant(1004L,"JOKER House","Seoul"));
        given(restaurantService.getRestaurants()).willReturn(restaurants); //getRestaurants()를 하면 가짜의 레스토랑 목록을 돌려줄것이다
        //given->BDD MOKITO,

        mvc.perform( get("/restaurants"))    //get 실행요청, url의 컬렉션 부분이 restaurants
                // mvc > create field
                // get > import
                // perform > add exception(예외처리)
               .andExpect(status().isOk())     //요청을 하면 성공적인 결과(200)가 나오는지 확인
                //status() > import
                //끝에 세미콜론 없음 주의
                .andExpect(content().string(containsString(
                        "\"id\":1004")))            //해당 string이 포함되어 있는지 확인
                .andExpect(content().string(containsString(
                "\"name\":\"JOKER House\""))); //가게이름이 포함되도록
                // content > import2    ,   containsString > import3
                // 큰 따옴표를 그대로 기록되도록 앞에 \를 붙임
                // "name" : "Bob zip" 형태(JSON)
    }

    @Test
    public void detail() throws Exception {
        Restaurant restaurant1=new Restaurant(1004L,"JOKER House","Seoul");
        restaurant1.addMenuItem(new MenuItem("Kimchi"));
        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant1);

        Restaurant restaurant2=new Restaurant(2020L,"Cyber Food","Seoul");
        restaurant2.addMenuItem(new MenuItem("Kimchi"));
        given(restaurantService.getRestaurant(2020L)).willReturn(restaurant2);

        mvc.perform(get("/restaurants/1004"))
            .andExpect(status().isOk())
             .andExpect(content().string(containsString(
                "\"id\":1004")))            //해당 string이 포함되어 있는지 확인
            .andExpect(content().string(containsString(
                        "\"name\":\"JOKER House\"")))
            .andExpect(content().string(containsString("Kimch")));

        mvc.perform(get("/restaurants/2020"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "\"id\":2020")))            //해당 string이 포함되어 있는지 확인
                .andExpect(content().string(containsString(
                        "\"name\":\"Cyber Food\"")));
    }

    @Test   //가게 추가하기
    public void create() throws Exception {
//        Restaurant restaurant=new Restaurant(1234L,"BeRyong","Seoul");
        given(restaurantService.addRestaurant(any())).will(invocation -> {
            Restaurant restaurant=invocation.getArgument(0);
            return new Restaurant(1234L,restaurant.getName(),
                    restaurant.getAddress());
        });//////////////이거 언제 지혼자 추가함?ㅁㅊ넘이

        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("  {\"name\":\"Beryong\",\"address\":\"Busan\"}"))       //get이 아닌 post , perform예외처리
                .andExpect(status().isCreated())        //isOk가 아닌 isCreated
                .andExpect(header().string("location","/restaurants/1234"))
                .andExpect(content().string("{}"));

        verify(restaurantService).addRestaurant(any());
    }

    @Test
    public void update() throws Exception {

        mvc.perform(patch("/restaurants/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"JOKER Bar\", \"address\":\"Busan\"}")
        )
                        .andExpect(status().isOk());    //200번
        verify(restaurantService).updateRestaurant(1004L,"JOKER Bar","Busan");
    }
}