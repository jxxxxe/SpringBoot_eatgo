package kr.co.fastcampus.eatgo.Interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin
@RestController     //바로 밑에 처럼 컨트롤러 객체를 만들지 않아도 됨

public class RestaurantController {
//
//    @Autowired  //컨트롤러를 만들어줄 때 스프링이 알아서 저장소를 여기에 생성
//    private RestaurantRepository restaurantRepository;
//    //=new RestaurantRepository(); // 이렇게 직접 저장소 객체를 생성하지 않아도 자동 생성
//
//    @Autowired
//    private MenuItemRepository menuItemRepository;  //새로운 저장소생성, autowired필요
    // restaurantService로만 처리하므로 repository 삭제

    @Autowired
    private RestaurantService restaurantService;
//Menu~ -> import class

    @GetMapping("/restaurants")     //컨트롤러 처음 만들 때 부터
    public List<Restaurant> list(){     //리스트를 돌려주는 메서드
                                        // List > import
       /* List<Restaurant> restaurants=new ArrayList<>();
       // Restaurant restaurant=new Restaurant(1004L,"Bob zip","Seoul");
        restaurants.add(new Restaurant(1004L,"Bob zip","Seoul"));   //멤버추가
        restaurants.add(new Restaurant(2020L,"Cyber Food","Seoul"));
*/
        List <Restaurant> restaurants= restaurantService.getRestaurants();  //새로운 메서드를 만들어서 (1)+(2)정보 모두 얻게 해보자
        //=restaurantRepository.findAll(); //저장소에서 정보를 얻어서 그것을 레스토랑에 (1) 기본정보
            //repository > field추가
            // findAll() > create
        //restaurants.add(restaurant);        //  리스트에 추가
        return restaurants;             //즉, 콜렉션=리스트 , 멤버=객체
    }

    @GetMapping("/restaurants/{id}")    //id값은 변수임
    public Restaurant detail(@PathVariable("id") Long id){     //@path~ = 주소에 있는 변수를 파라메타로
        Restaurant restaurant=restaurantService.getRestaurant(id);  //(1)기본정보+(2)메뉴정보 >>> 어플리케이션(복잡한 것을 단순화)
        //에러처리 >> RestaurantErrorAdvice.class 생성해서 처리!

//        List<Restaurant> restaurants=new ArrayList<>();     //컬렉션 만듦
//        restaurants.add(new Restaurant(1004L,"Bob zip","Seoul"));   //멤버추가
//        restaurants.add(new Restaurant(2020L,"Cyber Food","Seoul"));
//       => 위 세줄이 상단에 list()함수와 동일하다. -> 같은 부분을 저장하는 저장소(도메인에 속함)

 //       Restaurant restaurant= restaurantRepository.findById(id);     //(1)기본정보
//        Restaurant restaurant=restaurants.stream()      //컬렉션에서 찾아서
//                .filter(r-> r.getId().equals(id))       //id를 얻어서 이와 같은 id를 찾음
//                .findFirst()
//                .get();         //값을 얻음
//                //.orElse(null)     //값이 없다면 null을 얻음

//        List<MenuItem> menuItems=menuItemRepository.findAllByRestaurantId(id);        //(2)메뉴정보
//        restaurant.setMenuItem(menuItems);      //메뉴아이템 클래스추가(add->set)
        return restaurant;

    }
    @PostMapping("/restaurants")
    public ResponseEntity<?> create(@Valid @RequestBody Restaurant resource) throws URISyntaxException {    //valid추가 > Restaurant에서 valid 어노테이션(ex. @notnull)을 붙여줌
        Restaurant restaurant= restaurantService.addRestaurant(resource);  //new Restaurant(name, address); 대신에 이렇게 생성 가능

        URI location=new URI("/restaurants/"+restaurant.getId());
        return ResponseEntity.created(location).body("{}");
    }

    @PatchMapping("/restaurants/{id}")
    public String update(@PathVariable("id") Long id,
                         @Valid @RequestBody Restaurant resource){     //restaurant=> resource
        String name = resource.getName();
        String address = resource.getAddress();
        restaurantService.updateRestaurant(id, name, address);  //=introduce variable
        return  "{}";

    }
}
