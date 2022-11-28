package com.lvv.ttimpex2.service;

import com.lvv.ttimpex2.molel.SCode;
import com.lvv.ttimpex2.repository.SCodeRepository;
import com.lvv.ttimpex2.service.handlers.ParadoxHandler;
import com.lvv.ttimpex2.validation.exceptions.ApplicationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class SCodeService implements ParadoxHandler {
    private final SCodeRepository sCodeRepository;

    public SCode findById(String id) {
        return sCodeRepository.findById(id).orElseThrow(
                () -> new ApplicationException(HttpStatus.NOT_FOUND, "Unable to found SCode")
        );
    }

    public List<SCode> findAllByEmployeeId(String id) {
        return sCodeRepository.findAllByEmployeeId(id);
    }

    public List<SCode> findAllSCodes() {
        return sCodeRepository.findAllSCodes();
    }

    public List<SCode> findAllSCodesWithEmptyEmployee() {
        return findAllSCodes().stream()
                .filter(sCode -> sCode.getEmployee() == null)
                .collect(Collectors.toList());
    }

    public Map<String, SCode> getMapSCode() {
        return findAllSCodes().stream()
                .collect(Collectors.toMap(SCode::getId, Function.identity()));
    }

    @Transactional
    public SCode saveSCode(SCode sCode) {
        return sCodeRepository.saveSCode(sCode).orElseThrow(
                () -> new ApplicationException(HttpStatus.NOT_FOUND, "Unable to save SCode")
        );
    }

    @Override
    @Transactional
    public void call(Path pathDB, ResultSet resultSet, LocalDate date) throws SQLException {
        while (resultSet.next()) {
            if (sCodeRepository.findById(resultSet.getString("CARD")).isEmpty()) {
                SCode sCode = new SCode(resultSet.getString("CARD"),
                    resultSet.getString("SCODE"));
                log.info("Add new s_code " + saveSCode(sCode));
            }
        }
    }

    @Transactional
    public SCode deleteSCodeById(String id) {
        return sCodeRepository.deleteById(id).orElseThrow(
                () -> new ApplicationException(HttpStatus.NOT_FOUND, "Unable to find scode")
        );
    }

    @Transactional
    public SCode createSCode(SCode sCode) {
        if (presentById(sCode.getId())) {
            throw new ApplicationException(HttpStatus.CONFLICT, "Exists scode by id");
        }
        return saveSCode(sCode);
    }

    @Transactional
    public SCode updateSCode(SCode sCode) {
        findById(sCode.getId());
        return saveSCode(sCode);
    }

    private boolean presentById(String id) {
        return sCodeRepository.findById(id).isPresent();
    }
}
