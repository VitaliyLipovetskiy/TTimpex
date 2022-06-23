package com.lvv.ttimpex2.service;

import com.lvv.ttimpex2.molel.TimeStamp;
import com.lvv.ttimpex2.repository.TimeStampRepository;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Vitalii Lypovetskyi
 */
@Service
public class TimeStampService {
    final private TimeStampRepository timeStampRepository;

    final static private Logger log = getLogger(TimeStampService.class);

    public TimeStampService(TimeStampRepository timestampRepository) {
        this.timeStampRepository = timestampRepository;
    }

    public Page<TimeStamp> findAll(PageRequest page, Map<String, String> filter) {
//        if (filter.isEmpty()) {
//            return timeStampRepository.findAll(page);
//        } else {
        return timeStampRepository.findAll(specification(filter), page);
//        }
    }

    public List<TimeStamp> findAllByPost(int post) {
        return timeStampRepository.findAllByPost(post);
    }

    public List<TimeStamp> findAllByCard(String card) {
        return timeStampRepository.findAllByCard(card);
    }

    public List<TimeStamp> findAllByCardAndEvent(String card, int event) {
        return timeStampRepository.findAllByCardAndEvent(card, event);
    }

    public TimeStamp getFirstByCard(String card) {
        return timeStampRepository.getFirstByCardOrderByDateTimeDesc(card);
    }

    public TimeStamp getTopByCardAndEvent(String card, int event) {
        return timeStampRepository.getTopByCardAndEventOrderByDateTimeDesc(card, event);
    }

    private Specification<TimeStamp> specification(Map<String, String> filter) {
        Specification<TimeStamp> spec = Specification.where(null);
        if (filter.get("date") != null) {
            spec = spec.and(FilterSpecs.today(filter.get("date")));
        }
//        if (filter.get("name") != null)
//            spec = spec.and(FilterSpecs.nameContains(filter.get("name")));
//        if (filter.get("before") != null)
//            spec = spec.and(FilterSpecs.birthdayLessThanOrEqualTo(filter.get("before")));

        return spec;
    }

    static class FilterSpecs {

        public FilterSpecs() {}

        public static Specification<TimeStamp> today(String date) {
            LocalDate localDate = LocalDate.parse(date);
            return (Specification<TimeStamp>) (root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("dateTime").as(LocalDate.class), localDate);
        }
        public static Specification<TimeStamp> nameContains(String name) {
            return (Specification<TimeStamp>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
        }

        public static Specification<TimeStamp> birthdayLessThanOrEqualTo(String before) {
            return (Specification<TimeStamp>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"), new Date(Long.parseLong(before)));
        }
    }
}
