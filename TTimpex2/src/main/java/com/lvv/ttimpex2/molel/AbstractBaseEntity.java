package com.lvv.ttimpex2.molel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import java.util.Objects;

/**
 * @author Vitalii Lypovetskyi
 */
//@Access(AccessType.FIELD)
public abstract class AbstractBaseEntity implements HasId {
    protected  Integer id;

    protected AbstractBaseEntity() {}

    protected AbstractBaseEntity(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractBaseEntity that = (AbstractBaseEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id;
    }
}
