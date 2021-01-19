package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // 이 파일을 가져다 쓸 수 있게함, @Component와 같은 역할을 스프링에서 @Service라는 이름으로 지원
public class RestaurantService {
    @Autowired
    RestaurantRepository restaurantRepository;


    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository=restaurantRepository;
    }

    public Restaurant getRestaurant(Long id){
        Restaurant restaurant=restaurantRepository.findById(id)
                .orElseThrow(()->new RestaurantNotFoundException(id));    // 예외처리

        return restaurant;
    }

    public List<Restaurant> getRestaurants() {
        List<Restaurant> restaurants=restaurantRepository.findAll();
        return restaurants;

    }

    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public Restaurant updateRestaurant(long id, String name, String address) {
        Restaurant restaurant=restaurantRepository.findById(id).orElse(null);

        restaurant.updateInformation(name,address);

        restaurantRepository.save(restaurant);

        return restaurant;
    }
}
