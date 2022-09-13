package com.lvv.ttimpex2.molel;

/**
 * @author Vitalii Lypovetskyi
 */
public class Department extends AbstractNamedEntity implements HasId {

    public Department() {
        super();
    }

    public Department(Integer id, String name) {
        super(id, name);
    }

    public Department(String name) {
        super(null, name);
    }
}
