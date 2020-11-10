package kr.co.fastcampus.eatgo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue
    private long id;  //생성자에 초기화 필요

    private String name;
    private String address;

    @Transient
    private List<MenuItem> menuItems=new ArrayList<MenuItem>();     //addmenuitems 메서드에 따라 필드에 추가

    public Restaurant() {
    }

    public Restaurant(String name, String address) {
        this.name=name;
        this.address=address;
    }

    public Restaurant(Long id, String name, String address) {
        this.id=id;
       this.name=name;
       this.address =address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getInformation() {
        return name+" in "+ address;
    }

    public void setId(long id) {
        this.id=id;
    }

    public Long getId() {
        return id;
    }

    public List<MenuItem> getMenuItems(){
        return menuItems;       //메뉴아이템을 얻어오는것
    }

    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        for(MenuItem menuItem : menuItems)      //리스트 menuitems에 menuitem을 하나씩
            addMenuItem(menuItem);              //직접 넣어준다.
        //* for(A:B) B에서 차례대로 객체를 꺼내서 a에다가 넣겠다.(B에 더이상 꺼낼 객체가 없을 때 까지)
    }

//    public void setName(String name) {
//        this.name=name;
//    }

    public void updateInformation(String name, String address) {
        this.name=name;
        this.address=address;
    }
}
