package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.EmployeeDate;
import com.lvv.ttimpex2.molel.TimeStampDate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
@AllArgsConstructor
public class TimeStampDateRepositoryImpl implements TimeStampDateRepository {

    private final JpaTimeStampDateRepository timeStampDateRepository;

    @Override
    public TimeStampDate save(TimeStampDate entity) {
        return null;
    }

    @Override
    public boolean delete(EmployeeDate employeeDate) {
        return false;
    }

    @Override
    public TimeStampDate get(EmployeeDate employeeDate) {
        return null;
    }

    @Override
    public Collection<TimeStampDate> getCollection() {
        return List.of();
    }
}
