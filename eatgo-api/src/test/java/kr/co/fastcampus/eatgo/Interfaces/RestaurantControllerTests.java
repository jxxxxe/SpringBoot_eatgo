package kr.co.fastcampus.eatgo.Interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.MenuItemRepository;
import kr.co.fastcampus.eatgo.domain.MenuItemRepositoryImpl;
import kr.co.fastcampus.eatgo.domain.RestaurantRepository;
import kr.co.fastcampus.eatgo.domain.RestaurantRepositoryImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)    //에게 get 실행 요청
@WebMvcTest(RestaurantController.class)     //컨트롤러를 테스트 한다고 명시
public class RestaurantControllerTests {
    @Autowired
    private MockMvc mvc;    //mvc field 만들어짐

    @SpyBean(RestaurantService.class)           // repository+menuitem
    private RestaurantService restaurantService;

    @SpyBean(RestaurantRepositoryImpl.class) //spy뒤에 어떤 구현을 쓸지 적어줘야 함.
    private RestaurantRepository restaurantRepository;
    //컨트롤러에 원하는 객체(의존성)을 주입 => 없으면 오류 //리파지토리를 주입
    //인터페이스가 만들어진 후에 구현(Impl)에서 실제 인터페이스로 변경

    @SpyBean(MenuItemRepositoryImpl.class)
    private MenuItemRepository menuItemRepository;

    @Test
    public void list() throws Exception {       //throws Exception: perform예외
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
                "\"name\":\"Bob zip\""))) //가게이름이 포함되도록
                // content > import2    ,   containsString > import3
                // 큰 따옴표를 그대로 기록되도록 앞에 \를 붙임
                // "name" : "Bob zip" 형태(JSON)
        .andExpect(content().string(containsString("Kimch")));
    }

    @Test
    public void detail() throws Exception {
    mvc.perform(get("/restaurants/1004"))
            .andExpect(status().isOk())
             .andExpect(content().string(containsString(
                "\"id\":1004")))            //해당 string이 포함되어 있는지 확인
            .andExpect(content().string(containsString(
                        "\"name\":\"Bob zip\"")));
        mvc.perform(get("/restaurants/2020"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "\"id\":2020")))            //해당 string이 포함되어 있는지 확인
                .andExpect(content().string(containsString(
                        "\"name\":\"Cyber Food\"")));
    }
}