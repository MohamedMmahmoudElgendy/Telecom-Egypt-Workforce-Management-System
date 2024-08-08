package com.example.ordercreate.controllers;


import com.example.ordercreate.DTOs.timechange;
import com.example.ordercreate.entity.entity;
import com.example.ordercreate.entity.techentity;
import com.example.ordercreate.services.Techservice;
import com.example.ordercreate.services.service;
import com.example.ordercreate.services.slotservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
@CrossOrigin("*")
public class controller {

    @Autowired
    private service sorder;

    @Autowired
    private Techservice techservice;
    @Autowired
    private slotservice slotservice;


    @GetMapping("/All")
    public ResponseEntity<List<entity>> getallorders()
    {
        return ResponseEntity.ok(sorder.getallorders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<entity>> getorderbyid(@PathVariable long id)
    {
        return ResponseEntity.ok(sorder.getorderid(id));
    }
    @GetMapping("/search")
    public ResponseEntity<?> searchorders(@RequestParam (required = false) Long id,
                                          @RequestParam (required = false) String cname,
                                          @RequestParam (required = false) Long cnumber)
    {
       List<entity> orders= sorder.serachorders(id,cname,cnumber);

        return ResponseEntity.ok(orders);
    }
    @GetMapping("/searchbystring")
    public ResponseEntity<?> searchbystring(@RequestParam String name)
    {
        List<entity> orders=  sorder.searchbystring(name);
        return ResponseEntity.ok(orders);
    }

//    public ResponseEntity<entity> getorderbyname(@RequestParam String name)
//    {
//        return sorder.getorderbyname(name);
//    }

    @PostMapping("/create")
    public ResponseEntity<?> createorder (@RequestBody entity en)
    {
        System.out.println("hello"+en.getId());
        return new ResponseEntity<>(sorder.createorder(en), HttpStatus.CREATED);

    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateorder(@PathVariable Long id,@RequestBody entity en)
    {

        return  sorder.updateorder(id,en);

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteorder (@PathVariable Long id)
    {
        return sorder.deleteorder(id);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<String> ordercancel ( @PathVariable Long id)
    {

        return (ResponseEntity<String>) sorder.cancelorder(id);

    }
    @PutMapping("/force/{id}")
    public ResponseEntity<String> orderforce ( @PathVariable Long id)
    {

        return (ResponseEntity<String>) sorder.forcedorder(id);

    }
    @PutMapping("/changetime")
    public ResponseEntity<?> timeChange (@RequestBody timechange Tchange) throws ParseException {
       return slotservice.changetime(Tchange);
    }

    @GetMapping("/getTechNames")
    public ResponseEntity<?> getTechNames (@RequestParam Long id)
    {
        return techservice.getTechNames(id);
    }

    @PutMapping("/reassign")
    public ResponseEntity<?> reassignTechname(@RequestParam String tech_name,@RequestParam Long order_id)
    {
        return techservice.reassign_techname(tech_name,order_id);
    }
    @PutMapping("/rescheduled")
    public ResponseEntity<?> insert_slot(@RequestParam LocalDateTime time ,@RequestParam String slot, @RequestParam Long order_id )
    {

        return  slotservice.insert_slot(time,slot,order_id);

    }

    @GetMapping("/assigntechname")
    public  ResponseEntity<?> get_avaliable_technames ( @RequestParam Long order_id)
    {
        return techservice.get_avaliable_technames(order_id);
    }
    @GetMapping("/assignslots")
    public ResponseEntity<?> get_avaliable_slots(@RequestBody timechange Tchange)
    {

        return slotservice.get_available_slots(Tchange);
    }
    @PutMapping("/addtechandslot")
    public ResponseEntity<?> insert_techname_slots (@RequestParam String slot,@RequestParam String tech_name,@RequestParam Long order_id)
    {

        return slotservice.insert_techname_slots(slot,tech_name,order_id);

    }



}
