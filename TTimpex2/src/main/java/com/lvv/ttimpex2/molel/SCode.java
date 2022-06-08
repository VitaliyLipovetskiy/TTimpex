package com.lvv.ttimpex2.molel;

/**
 * @author Vitalii Lypovetskyi
 */
public class SCode {
    private final String card;
    private final String sCode;

    public SCode(String card, String sCode) {
        this.card = card;
        this.sCode = sCode;
    }

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
