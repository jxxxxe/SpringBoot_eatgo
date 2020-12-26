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
    private List<MenuItem> menuItems;     //addmenuitems 메서드에 따라 필드에 추가

    public String getInformation() {
        return name+" in "+ address;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
       this.menuItems=new ArrayList<>(menuItems);   //menuitems생성을 위에 선언에서 안해줬으므로 여기서 함
        //set은 새로 등록하는 것
        // addmenuitems 메서드를 없앰 >> addmenuitems를 단독으로 쓰는 파일에서 setmenuitems를 사용하도록 함(Arrays.asList 사용)

//        for(MenuItem menuItem : menuItems)
//            addMenuItem(menuItem);
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
