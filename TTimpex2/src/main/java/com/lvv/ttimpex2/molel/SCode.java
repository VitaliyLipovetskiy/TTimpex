package com.lvv.ttimpex2.molel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Vitalii Lypovetskyi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "s_code", schema = "timestamp")
public final class SCode {
    @Id
    @Column(name = "card")
    private String id;
    @Column(name = "s_code")
    private String sCode;

    @Override
    public String toString() {
        return "SCode{" +
                "card='" + id + '\'' +
                ", sCode='" + sCode + '\'' +
                '}';
    }

    //    CREATE TABLE TRZ_SC (
    //            CARD VARCHAR(4),
    //            SCODE VARCHAR(8),
    //            CONSTRAINT PK_TRZ_SC PRIMARY KEY ()
    //    );
}
