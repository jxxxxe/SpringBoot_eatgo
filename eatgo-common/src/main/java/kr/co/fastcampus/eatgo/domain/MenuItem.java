package kr.co.fastcampus.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem {

    @Id
    @GeneratedValue
    private Long id;

    private Long restaurantId;

    private String name;    // final이면 안됨(@NoArgsConstructor 때문)

    @Transient
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean destroy;
}
