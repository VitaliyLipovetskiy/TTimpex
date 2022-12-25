package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.DayOff;
import com.lvv.ttimpex2.dto.DayOffAndWorkedDto;
import com.lvv.ttimpex2.molel.Employee;
import com.lvv.ttimpex2.molel.EmployeeDate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@AllArgsConstructor
public class DayOffRepositoryImpl implements DayOffRepository {
    private final JpaDayOffRepository dayOffRepository;

    @Override
    public Optional<DayOff> findById(EmployeeDate id) {
        return dayOffRepository.findById(id);
    }

    @Override
    public Map<String, Map<LocalDate, DayOffAndWorkedDto>> getAllEmployeeWithDaysOffBetween(LocalDate startDate, LocalDate endDate) {
        Map<String, Map<LocalDate, DayOffAndWorkedDto>> result = new ConcurrentHashMap<>();

        dayOffRepository.findAll().forEach(dayOff -> {
//            System.out.println(dayOff);
            Employee employee = dayOff.getEmployeeDate().getEmployee();
//            System.out.println(employee);
            Map<LocalDate, DayOffAndWorkedDto> mapDaysOffDto = result.get(employee.id());
            if (mapDaysOffDto == null) {
                mapDaysOffDto = new ConcurrentHashMap<>();
            }
            LocalDate date = dayOff.getEmployeeDate().getDate();
            if (date.isAfter(startDate.minusDays(1)) && date.isBefore(endDate)) {
                mapDaysOffDto.put(date, new DayOffAndWorkedDto(dayOff));
            }
            for (date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
                if (!mapDaysOffDto.containsKey(date)) {
                    mapDaysOffDto.put(date, new DayOffAndWorkedDto(date));
                }
            }
            result.put(employee.getId(), mapDaysOffDto);
        });
        return result;
    }

    @Override
    public DayOff saveDayOff(DayOff dayOff) {
        return dayOffRepository.save(dayOff);
    }

    @Override
    public List<DayOff> getAll() {
        return dayOffRepository.findAll();
    }
}
