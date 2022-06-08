package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Vitalii Lypovetskyi
 */
@Repository
public interface CardRepository extends JpaRepository<Card, String> {
}
