package kr.co.fastcampus.eatgo.Interfaces;

import kr.co.fastcampus.eatgo.application.MenuItemService;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MenuItemController {
    @Autowired
    private MenuItemService menuItemService;

    @GetMapping("/restaurants/{restaurantId}/menuitems")
    public List<MenuItem> list(@PathVariable("restaurantId") Long restaurantId){
        List <MenuItem> menuitems=menuItemService.getMenuItems(restaurantId);

        return menuitems;
    }

    @PatchMapping("/restaurants/{restaurantId}/menuitems")
    public String bulkUpdate(
            @PathVariable("restaurantId") Long restaurantId,
            @RequestBody List<MenuItem> menuItems
    ){
        menuItemService.bulkUpdate(restaurantId,menuItems);
        return "";
    }
}
