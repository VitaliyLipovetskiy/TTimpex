package com.lvv.ttimpex2.molel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "s_code", schema = "timestamp")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public final class SCode {
    @Id
    @Column(name = "card")
    @NotNull
    @Size(min = 4, max = 4)
    private String id;
    @NotNull
    @Size(min = 8, max = 8)
    @Column(name = "s_code")
    private String sCode;
//    @Column(length = 36, nullable = true, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    @ToString.Exclude
    private Employee employee;

    public SCode(String id, String sCode) {
        this(id, sCode, null);
    }

    //    CREATE TABLE TRZ_SC (
    //            CARD VARCHAR(4),
    //            SCODE VARCHAR(8),
    //            CONSTRAINT PK_TRZ_SC PRIMARY KEY ()
    //    );
}

