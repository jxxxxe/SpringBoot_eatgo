package kr.co.fastcampus.eatgo.domain;

import org.springframework.data.repository.CrudRepository;  //왜 내가 직접 적어줘야 하고 난리임ㅡ.ㅡ

import java.util.List;

public interface MenuItemRepository extends CrudRepository<MenuItem,Long>{

    List<MenuItem> findAllByRestaurantId(Long restaurantId);

}

//도메인에서 새파일 추가
