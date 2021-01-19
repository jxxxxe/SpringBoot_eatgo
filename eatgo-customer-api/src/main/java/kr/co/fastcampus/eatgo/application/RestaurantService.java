package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // 이 파일을 가져다 쓸 수 있게함, @Component와 같은 역할을 스프링에서 @Service라는 이름으로 지원
public class RestaurantService {
    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    MenuItemRepository menuItemRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public RestaurantService(RestaurantRepository restaurantRepository,MenuItemRepository menuItemRepository, ReviewRepository reviewRepository) {//TEST에 맞춰 생성자가 수정됨
        this.restaurantRepository=restaurantRepository;     //TEST에 맞춰 생성자가 수정됨
        this.menuItemRepository=menuItemRepository;
        this.reviewRepository=reviewRepository;
    }

    public List<Restaurant> getRestaurants(String region,Long category) {
        List<Restaurant> restaurants=restaurantRepository.findAllByAddressContainingAndCategoryId(region,category);
        return restaurants;
    }

    public Restaurant getRestaurant(Long id){
        Restaurant restaurant=restaurantRepository.findById(id)     //id가 매칭되는 레스토랑 객체를 repository에서 가지고 옴
                .orElseThrow(()->new RestaurantNotFoundException(id));    //아니라면 예외처리

        List<MenuItem> menuItems=menuItemRepository.findAllByRestaurantId(id);        //(2)메뉴정보
        restaurant.setMenuItems(menuItems);

        List<Review> reviews=reviewRepository.findAllByRestaurantId(id);
        restaurant.setReviews(reviews);

        return restaurant;
    }

}
