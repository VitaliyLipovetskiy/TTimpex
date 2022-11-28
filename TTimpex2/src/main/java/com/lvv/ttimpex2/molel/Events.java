package com.lvv.ttimpex2.molel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Events {
    INPUT(0, "ВХОД"),
    EXIT(1, "ВЫХОД"),
    LOGIN_ATTEMPT(2, "ВХОД ПОПЫТКА"),
    EXIT_ATTEMPT(3, "ВЫХОД ПОПЫТКА");

    private final int id;
    private final String name;

    public static Events getById(int id) {
        for (Events event : Events.values()) {
            if (event.id == id) return event;
        }
        throw new IllegalArgumentException();
    }
}
