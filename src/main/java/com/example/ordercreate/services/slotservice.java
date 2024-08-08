package com.example.ordercreate.services;


import com.example.ordercreate.DTOs.timechange;
import com.example.ordercreate.entity.entity;
import com.example.ordercreate.repo.repo;
import com.example.ordercreate.repo.slotsrepo;
import com.example.ordercreate.repo.techrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service

public class slotservice {

    @Autowired
    private slotsrepo slots;
    @Autowired
    private techrepo tech_repo;
    @Autowired
    private repo Repo;

//    public ResponseEntity<List<String>>  getslots(LocalDateTime startDate , Long id_tec)
//    {
//
//        return  ResponseEntity.ok(slots.getslots(startDate,id_tec));
//    }

    public ResponseEntity<?> changetime(timechange tchange) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        LocalDate changeDate = LocalDate.parse(tchange.getChangetime(), formatter);
//        DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(tchange.getChangetime(),
                DateTimeFormatter.ofPattern( "dd-MM-yyyy" ));

        long daysDifference = ChronoUnit.DAYS.between(LocalDate.now(), date);

        if (daysDifference > 14) {
            return ResponseEntity.ok().body("Date exceeded more than 14 days!");
        }

        else if (daysDifference < 0) {
            return ResponseEntity.ok().body("Can't choose past day");
        }

        if(tchange.getId() == null )
        {
            return ResponseEntity.notFound().build();
        }

//        entity orderNumber = Repo.findById(tchange.getId()).get();

        List<String> list = slots.getslots(tchange.getChangetime() + " 00:00:00" , tchange.getChangetime() + " 23:59:59" ,tchange.getId());
        return ResponseEntity.ok().body(list);
    }


    public ResponseEntity<?> get_available_slots(timechange tchange)
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
       LocalDate date = LocalDate.parse(tchange.getChangetime(),DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        long daysDifference = ChronoUnit.DAYS.between(LocalDate.now(), date);


        if (daysDifference > 14) {
            return ResponseEntity.ok().body("Date exceeded more than 14 days!");
        }

        else if (daysDifference < 0) {
            return ResponseEntity.ok().body("Can't choose past day");
        }

        if(tchange.getId() == null )
        {
            return ResponseEntity.notFound().build();
        }
        List<String> List = slots.get_available_slots(tchange.getChangetime() + " 00:00:00" , tchange.getChangetime() + " 23:59:59" ,tchange.getId());
        return ResponseEntity.ok().body(List);
    }

    public ResponseEntity<?> insert_slot(LocalDateTime time , String solts, Long id)
    {
        entity en=Repo.findById(id).get();

        Long slots_id=slots.insert_slot(solts);
        if(en.getOrder_status().contains("Canceled") || en.getOrder_status().contains("Forced")  )
        {
            System.out.println("hello");
            return ResponseEntity.badRequest().body("order already canceled or forced , create new one");
        }
        else {
            if (slots_id == null)
                return ResponseEntity.badRequest().build();

            else {
                en.setPruposed_date(time);
                en.setId_slots(slots_id);
                Repo.save(en);
                return ResponseEntity.ok().body("rescheduled Successfully");
            }
        }

    }

    public ResponseEntity<?> insert_techname_slots(String slot,String Tech_name, Long order_id)
    {
        entity en = Repo.findById(order_id).get();
        Long ava_slot=slots.insert_slot(slot);
        Long name= tech_repo.reassignTechname(Tech_name);
        if(ava_slot == null)
            return ResponseEntity.badRequest().build();

        else
        {
            en.setId_tec(name);
            en.setId_slots(ava_slot);
            Repo.save(en);
            return ResponseEntity.ok().body("technician available slots added successfully");
        }

    }










}






