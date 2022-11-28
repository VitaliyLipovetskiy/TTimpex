package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.SCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SCodeRepositoryImpl implements SCodeRepository {
    private final JpaSCodeRepository sCodeRepository;

    @Override
    public Optional<SCode> findById(String id) {
        return sCodeRepository.findById(id);
    }

    @Override
    public List<SCode> findAllByEmployeeId(String id) {
        return sCodeRepository.findAllByEmployeeId(id);
    }

    @Override
    public Optional<SCode> saveSCode(SCode entity) {
        try {
            return Optional.of(sCodeRepository.save(entity));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteSCodeById(String id) {
        Optional<SCode> optionalSCode = findById(id);
        if (optionalSCode.isPresent()) {
            sCodeRepository.delete(optionalSCode.get());
            return true;
        }
        return false;
    }

    @Override
    public List<SCode> findAllSCodes() {
        return sCodeRepository.findAll();
    }

    @Override
    public Optional<SCode> deleteById(String id) {
        Optional<SCode> optionalSCode = findById(id);
        if (optionalSCode.isPresent()) {
            sCodeRepository.delete(optionalSCode.get());
            return optionalSCode;
        }
        return Optional.empty();
    }
}
