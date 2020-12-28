package kr.co.fastcampus.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {

    @Id
    @GeneratedValue
    @Setter
    private long id;  //생성자에 초기화 필요

    @NotEmpty
    private String name;

    @NotEmpty
    private String address;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Setter
    private List<MenuItem> menuItems;     //addmenuitems 메서드에 따라 필드에 추가

    @Setter
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Review> reviews;

    public String getInformation() {
        return name+" in "+ address;
    }

    public void updateInformation(String name, String address) {
        this.name=name;
        this.address=address;
    }

    //    public Restaurant(Long id, String name, String address) {
//        this.id=id;
//       this.name=name;
//       this.address =address;
//    }

    //   public void addMenuItem(MenuItem menuItem) {
//        if(menuItems==null){
//            menuItems=new ArrayList<>();
//        }
//
//        menuItems.add(menuItem);
//    }

//    public void setName(String name) {
//        this.name=name;
//    }


}
