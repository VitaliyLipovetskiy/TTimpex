package com.lvv.ttimpex2.service.old;

import com.lvv.ttimpex2.molel.old.TimeStampOld;
import com.lvv.ttimpex2.repository.old.DataJpaTimeStampOldRepository;
import com.lvv.ttimpex2.dto.old.TimeStampOldTo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.*;

//import static org.slf4j.LoggerFactory.getLogger;


//@Service
public final class TimeStampOldService {
    private final DataJpaTimeStampOldRepository dataJpaTimeStampOldRepository;

//    final static private Logger log = getLogger(TimeStampService.class);

    public TimeStampOldService(DataJpaTimeStampOldRepository dataJpaTimeStampOldRepository) {
        this.dataJpaTimeStampOldRepository = dataJpaTimeStampOldRepository;
    }

    public Page<TimeStampOld> findAll(PageRequest page, Map<String, String> filter) {
//        if (filter.isEmpty()) {
//            return timeStampRepository.findAll(page);
//        } else {
        return null; //dataJpaTimeStampRepository.findAll(specification(filter), page);
//        }
    }

    public List<TimeStampOldTo> findAllTo(PageRequest page, Map<String, String> filter) {
//        if (filter.isEmpty()) {
//            return timeStampRepository.findAll(page);
//        } else {

        List<TimeStampOldTo> allTo = dataJpaTimeStampOldRepository.findAllTo();
//        System.out.println(allTo.size());
        return allTo;
//        }
    }

    public List<TimeStampOld> findAllByPost(int post) {
        return dataJpaTimeStampOldRepository.findAllByPost(post);
    }

    public List<TimeStampOld> findAllByCard(String card) {
        return dataJpaTimeStampOldRepository.findAllByCard(card);
    }

    public List<TimeStampOld> findAllByCardAndEvent(String card, int event) {
        return dataJpaTimeStampOldRepository.findAllByCardAndEvent(card, event);
    }

    public TimeStampOld getFirstByCard(String card) {
        return dataJpaTimeStampOldRepository.getFirstByCardOrderByDateTimeDesc(card);
    }

    public TimeStampOld getTopByCardAndEvent(String card, int event) {
        return dataJpaTimeStampOldRepository.getTopByCardAndEventOrderByDateTimeDesc(card, event);
    }

    private Specification<TimeStampOld> specification(Map<String, String> filter) {
        Specification<TimeStampOld> spec = Specification.where(null);
        if (filter.get("date") != null) {
            spec = spec.and(FilterSpecs.today(filter.get("date")));
        }
//        if (filter.get("name") != null)
//            spec = spec.and(FilterSpecs.nameContains(filter.get("name")));
//        if (filter.get("before") != null)
//            spec = spec.and(FilterSpecs.birthdayLessThanOrEqualTo(filter.get("before")));

        return spec;
    }

    static final class FilterSpecs {

        private FilterSpecs() {}

        public static Specification<TimeStampOld> today(String date) {
            LocalDate localDate = LocalDate.parse(date);
            return (Specification<TimeStampOld>) (root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("dateTime").as(LocalDate.class), localDate);
        }
        public static Specification<TimeStampOld> nameContains(String name) {
            return (Specification<TimeStampOld>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
        }

        public static Specification<TimeStampOld> birthdayLessThanOrEqualTo(String before) {
            return (Specification<TimeStampOld>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"), new Date(Long.parseLong(before)));
        }
    }
}
