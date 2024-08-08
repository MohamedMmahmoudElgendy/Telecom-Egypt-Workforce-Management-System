package com.example.ordercreate.repo;

import com.example.ordercreate.entity.slotsentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface slotsrepo extends JpaRepository<slotsentity,Long> {

//    @Query(value = "select slot.sltos from ava_slots slot  where(\n" +
//            "\n" +
//            "    slot.id_slots NOT IN(\n" +
//            "        SELECT id_slots FROM creation c where c.pruposed_date BETWEEN TO_TIMESTAMP(?1, 'DD-MON-YYYY HH24:MI:SS')\n" +
//            "                          AND TO_TIMESTAMP(?2, 'DD-MON-YYYY HH24:MI:SS') and c.id_tec = ?3\n" +
//            "    )\n" +
//            ")" , nativeQuery = true)
//    List<String> getslots(LocalDateTime startDate,LocalDateTime endDate , Long id_tec);


    @Query(value = "SELECT slot.sltos\n" +
            "FROM ava_slots slot\n" +
            "WHERE slot.id_slots NOT IN (\n" +
            "    SELECT c.id_slots\n" +
            "    FROM creation c \n" +
            "    WHERE c.pruposed_date BETWEEN TO_TIMESTAMP(?1, 'DD-MM-YYYY HH24:MI:SS')\n" +
            "                          AND TO_TIMESTAMP(?2, 'DD-MM-YYYY HH24:MI:SS')\n" +
            "      AND c.id_tec in (\n" +
            "          SELECT c_inner.id_tec\n" +
            "          FROM creation c_inner\n" +
            "          WHERE c_inner.id = ?3))" , nativeQuery = true)
    List<String> getslots(String start, String end, Long order_id);

    @Query(value="SELECT slot.sltos\n" +
            "FROM ava_slots slot\n" +
            "WHERE slot.id_slots NOT IN (\n" +
            "    SELECT c.id_slots\n" +
            "    FROM creation c \n" +
            "    WHERE c.pruposed_date BETWEEN TO_TIMESTAMP(?1, 'DD-MM-YYYY HH24:MI:SS')\n" +
            "                          AND TO_TIMESTAMP(?2, 'DD-MM-YYYY HH24:MI:SS')\n" +
            "      AND c.id_tec =?3)",nativeQuery = true)
    List<String> get_available_slots( String start, String end, Long tech_id);


    @Query(value="select id_slots\n" +
            "from ava_slots \n" +
            "where sltos=?1",nativeQuery = true)
    Long insert_slot (String slots);




}
