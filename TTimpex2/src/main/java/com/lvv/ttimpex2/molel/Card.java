package com.lvv.ttimpex2.molel;

public class Card {
    private final String card;
    private final String firstName;
    private final String lastName;
    private final String middleName;
    private final String position;

    public Card(String card, String firstName, String lastName, String middleName, String position) {
        this.card = card;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.position = position;
    }

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
