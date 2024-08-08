package com.example.ordercreate.DTOs;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class timechange {

//    @JsonFormat( pattern = "DD-MM-YYYY")
    private String changetime;
    private Long id;

}
