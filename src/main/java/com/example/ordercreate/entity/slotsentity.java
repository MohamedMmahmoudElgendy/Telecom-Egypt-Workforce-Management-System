package com.example.ordercreate.entity;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="ava_slots")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class slotsentity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_slots;
    private String sltos;


//@OneToMany(fetch = FetchType.LAZY)
//@Nullable
//private List<entity> entity;




}
