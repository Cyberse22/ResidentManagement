package com.nhom13.controllers;

import com.nhom13.pojo.ElectronicLocker;
import com.nhom13.pojo.Item;
import com.nhom13.pojo.Resident;
import com.nhom13.pojo.User;
import com.nhom13.services.ElectronicLockerService;
import com.nhom13.services.ItemService;
import com.nhom13.services.NotificationService;
import com.nhom13.services.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import org.springframework.validation.BindingResult;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private ElectronicLockerService electronicLockerService;
    @Autowired
    private NotificationService notService;
    @Autowired
    private ResidentService resService;

    @GetMapping("/electronic-lockers/{elId}/items")
    public String getAllItemByElId(@PathVariable int elId,
            @RequestParam Map<String, String> params, Model model) {
        ElectronicLocker el = electronicLockerService.getElectronicLockerById(elId);
        String residentName = el.getResidentId().getUserId().getLastName()
                + " " + el.getResidentId().getUserId().getFirstName();
        model.addAttribute("items", itemService.getAllItemById(elId, params));
        model.addAttribute("residentName", residentName);
        model.addAttribute("elId", elId);
        return "item";
    }

    @GetMapping("/electronic-lockers/{elId}/items/{id}")
    public String getItem(@PathVariable int id, Model model) {
        Item i = itemService.getItemById(id);
        model.addAttribute("item", i);
        model.addAttribute("elId", i.getElectronicLockerId().getId());
        return "item-detail";
    }

    @GetMapping("/electronic-lockers/{elId}/items/create")
    public String createView(@PathVariable int elId, Model model) {
        ElectronicLocker el = electronicLockerService.getElectronicLockerById(elId);
        Item i = new Item();
        i.setElectronicLockerId(el);
        model.addAttribute("item", i);
        model.addAttribute("elId", elId);
        return "item-detail";
    }

    @PostMapping("/electronic-lockers/{elId}/items")
    public String create(@ModelAttribute(value = "item") Item item, Model model,
            BindingResult result, @PathVariable(value = "elId") int elId) {

        if (!result.hasErrors()) {
            try {
                
                itemService.updatOrCreateItem(item);
                
                int lockerId = item.getElectronicLockerId().getId();
                ElectronicLocker l = this.electronicLockerService.getElectronicLockerById(lockerId);
                Resident r = this.resService.getResidentById(l.getResidentId().getId());
                
                //lay user -> lay token
                User u = this.resService.getUserById(r.getUserId().getId());
                String token = u.getNotificationToken();
                String title = "Thông báo";
                String body = "Bạn vừa được nhận hàng";

                if (token != null && !token.isEmpty()) {
                    this.notService.sendNotification(token, title, body);
                }
                
                
                return "redirect:/electronic-lockers/" + item.getElectronicLockerId().getId() + "/items";
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
        return "redirect:/electronic-lockers/" + item.getElectronicLockerId().getId() + "/items/create?error";
    }
}
