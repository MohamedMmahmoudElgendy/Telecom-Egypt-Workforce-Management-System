package com.example.ordercreate.services;


import com.example.ordercreate.entity.entity;
import com.example.ordercreate.entity.techentity;
import com.example.ordercreate.repo.repo;
import com.example.ordercreate.repo.techrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Techservice  {

   @Autowired
    private techrepo tech_repo;
   @Autowired
   private repo Repo;


    public ResponseEntity<?> getTechNames(Long id){

       return ResponseEntity.ok(tech_repo.getTechNames(id)) ;
   }

   public ResponseEntity<?>get_avaliable_technames(Long id)
   {
       return ResponseEntity.ok(tech_repo.get_avaliable_technames(id));
   }


   public ResponseEntity<?> reassign_techname (String tech_name,Long id)
   {

//       System.out.println("hello");
       entity en=Repo.findById(id).get();

       Long tech_id= tech_repo.reassignTechname(tech_name);
       String status= en.getOrder_status();
       System.out.println(status);
       if(en.getOrder_status().contains("Canceled") || en.getOrder_status().contains("Forced")  )
       {
           System.out.println("hello");
           return ResponseEntity.badRequest().body("order already canceled or forced , create new one");
       }
       else {

           if (tech_id == null)
               return ResponseEntity.badRequest().build();

           else {
               en.setId_tec(tech_id);
               Repo.save(en);
               return ResponseEntity.ok().body("Reassign Successfully");
           }
       }


   }



}
