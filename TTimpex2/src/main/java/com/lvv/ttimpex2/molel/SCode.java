package com.lvv.ttimpex2.molel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Vitalii Lypovetskyi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
//@Table(name = "s_code", schema = "timestamp")
public final class SCode {
//    @Id
//    @Column(name = "card")
    @NotNull
    @Size(min = 4, max = 4)
    private String id;
    @NotNull
    @Size(min = 8, max = 8)
//    @Column(name = "s_code")
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

