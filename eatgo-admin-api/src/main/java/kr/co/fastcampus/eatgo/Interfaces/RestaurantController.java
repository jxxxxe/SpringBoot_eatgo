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

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public List<Restaurant> list(){

        List <Restaurant> restaurants= restaurantService.getRestaurants();

        return restaurants;
    }

    @GetMapping("/restaurants/{id}")    //id값은 변수임
    public Restaurant detail(@PathVariable("id") Long id){     //@path~ = 주소에 있는 변수를 파라메타로
        Restaurant restaurant=restaurantService.getRestaurant(id);
        //에러처리 >> RestaurantErrorAdvice.class 생성해서 처리!

//        Restaurant restaurant=restaurants.stream()      //컬렉션에서 찾아서
//                .filter(r-> r.getId().equals(id))       //id를 얻어서 이와 같은 id를 찾음
//                .findFirst()
//                .get();         //값을 얻음
//                //.orElse(null)     //값이 없다면 null을 얻음

       return restaurant;
    }

    @PostMapping("/restaurants")
    public ResponseEntity<?> create(@Valid @RequestBody Restaurant resource) throws URISyntaxException {
        Restaurant restaurant= restaurantService.addRestaurant(resource);  //new Restaurant(name, address); 대신에 이렇게 생성 가능

        URI location=new URI("/restaurants/"+restaurant.getId());
        return ResponseEntity.created(location).body("{}");
    }

    @PatchMapping("/restaurants/{id}")
    public String update(@PathVariable("id") Long id,
                         @Valid @RequestBody Restaurant resource){
        String name = resource.getName();
        String address = resource.getAddress();
        restaurantService.updateRestaurant(id, name, address);
        return  "{}";

    }
}
