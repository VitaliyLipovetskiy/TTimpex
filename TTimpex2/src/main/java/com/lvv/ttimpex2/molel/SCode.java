package com.lvv.ttimpex2.molel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Vitalii Lypovetskyi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SCode {
    @Id
    private String card;
    private String sCode;

    @Override
    public String toString() {
        return "SCode{" +
                "card='" + card + '\'' +
                ", sCode='" + sCode + '\'' +
                '}';
    }

    //    CREATE TABLE TRZ_SC (
    //            CARD VARCHAR(4),
    //            SCODE VARCHAR(8),
    //            CONSTRAINT PK_TRZ_SC PRIMARY KEY ()
    //    );
}
