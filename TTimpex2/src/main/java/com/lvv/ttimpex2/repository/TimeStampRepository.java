package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.TimeStamp;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Vitalii Lypovetskyi
 */
@Repository
public interface TimeStampRepository extends PagingAndSortingRepository<TimeStamp, String>,
        JpaSpecificationExecutor<TimeStamp>{
    List<TimeStamp> findAllByPost(int post);
    List<TimeStamp> findAllByCard(String card);
    List<TimeStamp> findAllByCardAndEvent(String card, int event);
    TimeStamp getFirstByCardOrderByDateTimeDesc(String card);
    TimeStamp getTopByCardAndEventOrderByDateTimeDesc(String card, int event);
}
