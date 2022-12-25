package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.Employee;
import com.lvv.ttimpex2.molel.Worked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface JpaWorkedRepository extends JpaRepository<Worked, String> {

    Optional<Worked> findFirstByEmployeeIdOrderByRecruitmentDesc(String employeeId);

    @Query(value = "SELECT w.* FROM worked w " +
            "join (SELECT employee_id, MAX(recruitment) as last_recruitment " +
            "FROM worked group by employee_id) m " +
            "on w.employee_id = m.employee_id and w.recruitment = m.last_recruitment", nativeQuery = true)
    List<Worked> findAllWithMaxRecruitment();

    Optional<Worked> findFirstByEmployeeAndRecruitment(Employee employee, LocalDate recruitment);
}
