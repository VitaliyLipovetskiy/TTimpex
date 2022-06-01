package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.Timestamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Vitalii Lypovetskyi
 */
@Repository
public interface TimestampRepository extends JpaRepository<Timestamp, String>{
    List<Timestamp> findAllByPost(int post);
    List<Timestamp> findAllByCard(String card);
    List<Timestamp> findAllByCardAndEvent(String card, int event);
    Timestamp getFirstByCardOrderByTimeDesc(String card);
    Timestamp getTopByCardAndEventOrderByTimeDesc(String card, int event);
}
