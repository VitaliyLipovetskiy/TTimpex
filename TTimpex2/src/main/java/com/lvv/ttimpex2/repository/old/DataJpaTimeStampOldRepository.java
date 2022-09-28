package com.lvv.ttimpex2.repository.old;

import com.lvv.ttimpex2.molel.old.SCodeOld;
import com.lvv.ttimpex2.molel.old.TimeStampOld;
import com.lvv.ttimpex2.to.old.TimeStampOldTo;

import java.util.List;

/**
 * @author Vitalii Lypovetskyi
 */
//@Repository
public interface DataJpaTimeStampOldRepository //extends PagingAndSortingRepository<TimeStamp, String>,
//        JpaSpecificationExecutor<TimeStamp>
{
    List<TimeStampOld> findAllByPost(int post);
    List<TimeStampOld> findAllByCard(String card);
    List<TimeStampOld> findAllByCardAndEvent(String card, int event);
    TimeStampOld getFirstByCardOrderByDateTimeDesc(String card);
    TimeStampOld getTopByCardAndEventOrderByDateTimeDesc(String card, int event);

//    @Query("SELECT ts.id, ts.dateTime, ts.post, ts.event, c, s FROM TimeStampOld ts JOIN Card c ON ts.card=c.id JOIN SCode s ON ts.card=s.id")
    List<TimeStampOldTo> findAllTo();
}
