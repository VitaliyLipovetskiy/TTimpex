package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.SCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Vitalii Lypovetskyi
 */
@Repository
public interface SCodeRepository extends JpaRepository<SCode, String> {
}
