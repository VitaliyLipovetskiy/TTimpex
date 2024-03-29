package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.EmployeeDate;
import com.lvv.ttimpex2.molel.TimeStampDate;

import java.util.Collection;

public interface TimeStampDateRepository {
    TimeStampDate save(TimeStampDate entity);
    boolean delete(EmployeeDate employeeDate);
    TimeStampDate get(EmployeeDate employeeDate);
    Collection<TimeStampDate> getCollection();
}
