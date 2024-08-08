package com.example.ordercreate.repo;

import com.example.ordercreate.entity.techentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface techrepo extends JpaRepository<techentity,Long> {

    @Query(value = "Select tech.tech_names\n" +
            "from tech_info tech ,creation c\n" +
            "where c.id =?1 and tech.id_tec <> c.ID_TEC and tech.tech_names NOT in \n" +
            "(\n" +
            " select tech_names\n" +
            " from tech_info t \n" +
            " join creation c on t.id_tec= c.id_tec\n" +
            " where c.pruposed_date in(select c.pruposed_date\n" +
            " from creation c\n" +
            " where c.id=?1)\n" +
            " and \n" +
            " c.ID_SLOTS in ( select c.ID_SLOTS \n" +
            " from creation c \n" +
            " where c.id=?1))" , nativeQuery = true)
     List<String> getTechNames(Long id);



    @Query(value=" select id_tec\n" +
            "from tech_info \n" +
            "where tech_names =?1",nativeQuery = true)
    Long reassignTechname (String Tech_names);



    @Query(value="Select tech.tech_names\n" +
            "from tech_info tech ,creation c\n" +
            "where c.id = ?1  and tech.tech_names NOT in\n" +
            "(\n" +
            "Select tech_names\n" +
            "from tech_info t\n" +
            "join creation c on t.id_tec= c.id_tec \n" +
            "where c.pruposed_date in (select c.pruposed_date \n" +
            "from creation c\n" +
            "where c.id = ?1)\n" +
            "GROUP BY tech_names, c.pruposed_date\n" +
            "Having COUNT(*)=6)", nativeQuery = true)
    List<String> get_avaliable_technames(Long order_id);

}
