package kr.co.fastcampus.eatgo.domain;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {

    private final String name;
    private final String location;
    private final long id;  //생성자에 초기화 필요
    private List<MenuItem> menuItems=new ArrayList<MenuItem>();     //addmenuitems 메서드에 따라 필드에 추가

    public Restaurant(Long id,String name, String location) {
        this.id=id;
       this.name=name;
       this.location=location;
    }

    public String getName() {
        return name;
    }

    public String getlocation() {
        return location;
    }

    public String getInformation() {
        return name+" in "+location;
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
}
