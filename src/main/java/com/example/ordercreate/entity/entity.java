package com.example.ordercreate.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.intellij.lang.annotations.Pattern;

import java.time.LocalDateTime;
import java.util.Date;
//import java.sql.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name ="CREATION")
@Data
public class entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cname;

    private Long cid;
    @Column(nullable = false)
    private String cadress;

    @Column(nullable = false)
    private String cmail;

    @Column(nullable = false)
    private Long cnumber;

   // @JsonFormat(pattern="yyyy-MM-dd")
   @Column(nullable = false)
    private LocalDateTime pruposed_date;
    private String order_status;
    private Long id_tec;
    private Long id_slots;

//    @ManyToOne
//    @JoinColumn(name ="id_tec", insertable = false, updatable = false)
//    private techentity techentity;

}
