package com.example.itemService.web.basic;

import com.example.itemService.domain.Item;
import com.example.itemService.domain.ItemRepository;
import com.example.itemService.domain.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemService itemService;

    @GetMapping
    public String items(Model model){
        List<Item> items = itemService.findAllItems();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemService.findItem(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model){

        Item item = new Item(itemName,price,quantity);

        itemService.save(item);
        model.addAttribute("item",item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item){
        itemService.save(item);
//        model.addAttribute("item",item); 자동추가, 생략 가능
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item){
        itemService.save(item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV4(Item item){
        itemService.save(item);
        return "/basic/item";
    }

    @PostMapping("/add")
    public String addItemV5(Item item){
        itemService.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemService.findItem(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item){
        itemService.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스터용 아이템 추가
     */
    @PostConstruct
    public void init(){
        itemService.save(new Item("itemA", 10000,10));
        itemService.save(new Item("itemB", 20000,20));
    }
}
