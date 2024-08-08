package com.example.ordercreate.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name="tech_info")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class techentity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_tec;
    private String Tech_names;


@OneToMany( fetch = FetchType.LAZY)
private List<entity> entity;

}
