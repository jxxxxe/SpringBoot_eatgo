package kr.co.fastcampus.eatgo.domain;

import org.springframework.stereotype.Component;//도메인 영역에 저장소를 두어 심플하게 함

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//
//@Component  //저장소를 스프링이 직접 관리해줌
//
//public class RestaurantRepositoryImpl implements RestaurantRepository {     //구현체로, 인터페이스를 구현
//
//   private List<Restaurant> restaurants=new ArrayList<>();
//
//   public RestaurantRepositoryImpl(){
//       restaurants.add(new Restaurant(1004L,"Bob zip","Seoul"));   //멤버추가
//       restaurants.add(new Restaurant(2020L,"Cyber Food","Seoul"));
//   }
//
//   //위 리스트와 생성자를 필드에 두어 밑에 두개의 함수 모두 쓸 수 있게 한다.
//   //두개의 함수를 인터페이스로 따로 만든다. >> 우클릭>Refactor>extract interface > 두개 함수 체크
//   // ~Impl는 인터페이스 구현체에서 인터페이스를 사용하도록 바꿈.
//        @Override
//        public List<Restaurant> findAll() {
//            return restaurants;
//    }
//
//    @Override
//    public Optional<Restaurant> findById(Long id){
//        Restaurant restaurant=restaurants.stream()      //컬렉션에서 찾아서
//        .filter(r-> r.getId().equals(id))       //id를 얻어서 이와 같은 id를 찾음
//        .findFirst()
//        .get();         //값을 얻음
//        //.orElse(null)     //값이 없다면 null을 얻음
//        return restaurant;
//        }
//
//    @Override
//    public Restaurant save(Restaurant restaurant) {
//       restaurant.setId(1234);
//       restaurants.add(restaurant);
//        return restaurant;
//    }
//}
