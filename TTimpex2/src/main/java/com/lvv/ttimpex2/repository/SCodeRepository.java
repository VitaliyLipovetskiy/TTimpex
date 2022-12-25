package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.SCode;

import java.util.List;
import java.util.Optional;

public interface SCodeRepository {
    Optional<SCode> findById(String id);
    List<SCode> findAllByEmployeeId(String id);
    Optional<SCode> saveSCode(SCode entity);
    boolean deleteSCodeById(String id);
    List<SCode> findAllSCodes();
    Optional<SCode> deleteById(String id);
}
