package com.example.ordercreate.repo;


import com.example.ordercreate.entity.entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface repo  extends JpaRepository<entity, Long> {

//    @Query(value="select e from entity e where e.cname=cname")
    @Query(value=" select * \n" +
            "      from creation c\n" +
            "      where c.cname=?1",nativeQuery = true)
    List<entity> findbyname(String cname);
//   @Query(value="select e from entity e where e.cnumber=cnumber")
    @Query(value="    select * \n" +
            "      from creation c\n" +
            "      where c.cnumber=?1",nativeQuery = true)
    List <entity> findbyphonenumber(Long cnumber);

}
