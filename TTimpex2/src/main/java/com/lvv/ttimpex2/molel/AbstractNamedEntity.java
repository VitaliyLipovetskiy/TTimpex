package com.lvv.ttimpex2.molel;


public abstract class AbstractNamedEntity extends AbstractBaseEntity {

    protected String name;

    protected AbstractNamedEntity() {}

    protected AbstractNamedEntity(String id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString() + '(' + name + ')';
    }
}
