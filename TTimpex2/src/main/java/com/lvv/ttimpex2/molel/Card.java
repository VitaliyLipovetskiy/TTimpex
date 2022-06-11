package com.lvv.ttimpex2.molel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "card", schema = "timestamp")
public class Card {
    @Id
    @Column(name = "card")
    private String id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "middle_name")
    private String middleName;
    @Column
    private String position;

    @Override
    public String toString() {
        return "Card{" +
                "card='" + id + '\'' +
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
