package kr.co.fastcampus.eatgo.domain;

import java.util.List;

public interface MenuItemRepository {

    List<MenuItem> findAllByRestaurantId(Long restaurantId);

}

//도메인에서 새파일 추가
