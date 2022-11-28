package com.lvv.ttimpex2.molel;

import org.springframework.util.Assert;

public interface HasId {
    String getId();

    void setId(String id);

    default boolean isNew() {
        return getId() == null;
    }

    // doesn't work for hibernate lazy proxy
    default String id() {
        Assert.notNull(getId(), "Entity must has id");
        return getId();
    }
}
