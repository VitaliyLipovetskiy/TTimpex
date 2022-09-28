package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.SCode;

import java.util.Collection;

/**
 * @author Vitalii Lypovetskyi
 */
public interface SCodeRepository {

    SCode save(SCode entity);
    boolean delete(String id);
    SCode get(String id);
    Collection<SCode> getCollection();
}
