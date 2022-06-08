package com.lvv.ttimpex2.molel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Card {
    @Id
    private String card;
    private String firstName;
    private String lastName;
    private String middleName;
    private String position;

    @Override
    public String toString() {
        return "Card{" +
                "card='" + card + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", position='" + position + '\'' +
                '}';
    }

    //    CREATE TABLE TRZ_VIPS (
//      CARD VARCHAR(4),
//      F VARCHAR(15),
//      I VARCHAR(15),
//      O VARCHAR(15),
//      DOL VARCHAR(30), должность
//      GD VARCHAR(2),   = D0)
}
