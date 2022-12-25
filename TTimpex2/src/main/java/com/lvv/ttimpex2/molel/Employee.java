package com.lvv.ttimpex2.molel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "employees", schema = "timestamp")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class Employee implements HasId {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private String id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "middle_name")
    private String middleName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
//    @JsonDeserialize(using = DepartmentDeserializer.class)
    private Department department;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @Column(name = "start_time")
    private LocalTime startTime; // согласованное время начала раб дня
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @Column(name = "end_time")
    private LocalTime endTime; // согласованное время окончания раб дня
    @Column(name = "accounting_for_hours_worked")
    private Boolean accountingForHoursWorked;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    @JsonManagedReference
//    @JsonDeserialize(using = DepartmentDeserializer.class)
    @ToString.Exclude
    private List<SCode> cards;

    public Employee(String id, String name, Department department, LocalTime startTime, LocalTime endTime, Boolean accountingForHoursWorked) {
        this(id, null, null, null, department, startTime, endTime, accountingForHoursWorked, null);
        setNames(name);
    }

    public Employee(String id, String name, Department department, LocalTime startTime, LocalTime endTime) {
        this(id, name, department, startTime, endTime, false);
    }

    public Employee(String name, Department department, LocalTime startTime, LocalTime endTime, Boolean accountingForHoursWorked) {
        this(null, name, department, startTime, endTime, accountingForHoursWorked);
    }

    public Employee(String name, Department department, LocalTime startTime, LocalTime endTime) {
        this(null, name, department, startTime, endTime);
    }

    public Employee(String id, String name, Department department) {
        this(id, name, department, null, null);
    }

    public Employee(String name, Department department) {
        this(name, department, null, null);
    }

    public Employee(String id, String name, Department department, Boolean accountingForHoursWorked) {
        this(id, name, department,null, null, accountingForHoursWorked);
    }

    public Employee(String id, String name) {
        this(id, name, null, null, null, null);
    }

    public Employee(String name) {
        this(null, name);
    }

//    public Employee(String id, String name, Department department) {
//        this(id, name, department, null, null, null);
//    }

//    public Employee(String id, String name, String cardId) {
//        this(id, name, null, cardId, null, null);
//    }

//    public Employee(EmployeeDto empTo) {
//        this(empTo.getId(), empTo.getFirstName(), empTo.getLastName(), empTo.getMiddleName(),
//                empTo.getDepartment(), empTo.getCardId(), empTo.getStartTime(), empTo.getEndTime(), empTo.getAccountingForHoursWorked());
//    }
    public void setNames(String name) {
        if (name == null) return;
        String[] names = name.split(" ");
        if (names.length > 0) {
            this.lastName = names[0];
        }
        if (names.length > 1) {
            this.firstName = names[1];
        }
        if (names.length > 2) {
            this.middleName = names[2];
        }
    }

    public String getNames() {
        StringBuilder builder = new StringBuilder();
        if (lastName != null && !lastName.isEmpty()) {
            builder.append(lastName);
        }
        if (firstName != null && !firstName.isEmpty()) {
            builder.append(" ").append(firstName);
        }
        if (middleName != null && !middleName.isEmpty()) {
            builder.append(" ").append(middleName);
        }
        return builder.toString();
    }

    public String getNameDepartment() {
        return department == null ? null : department.getName();
    }

//    public void setDepartment(@Null DepartmentDto dto) {
//        if (dto == null) {
//            this.department = null;
//        } else {
//            this.department = new Department(dto.getId(), dto.getName());
//        }
//    }

}
