package com.lvv.ttimpex2.molel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "card", schema = "timestamp")
public final class Card {
    @Id
    @Column(name = "card")
    @NotNull
    @Size(min = 4, max = 4)
    private String id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "middle_name")
    private String middleName;
    @Column
    private String position;
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "card")
//    private SCode s_code;

//    public Card(String id, String firstName, String lastName, String middleName, String position) {
//        this.id = id;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.middleName = middleName;
//        this.position = position;
//    }

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
