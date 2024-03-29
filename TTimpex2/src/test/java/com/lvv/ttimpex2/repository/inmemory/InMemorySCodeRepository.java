package com.lvv.ttimpex2.repository.inmemory;

import com.lvv.ttimpex2.molel.SCode;
import com.lvv.ttimpex2.repository.SCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class InMemorySCodeRepository implements SCodeRepository {
    private final Map<String, SCode> map = new ConcurrentHashMap<>();
    private static final Logger log = LoggerFactory.getLogger(InMemorySCodeRepository.class);

    public static final SCode sCode_0012 = new SCode("0012", "12345678");
    public static final SCode sCode_0035 = new SCode("0035", "asdfghjk");
    public static final SCode sCode_0067 = new SCode("0067", "ertyuioo");
    public static final SCode sCode_0039 = new SCode("0039", "234ert56");
    public static final SCode sCode_0072 = new SCode("0072", "fgh678yu");
    public static final SCode sCode_0006 = new SCode("0006", "r5t6y7u8");

    public InMemorySCodeRepository() {
        map.put("0012", sCode_0012);
        map.put("0035", sCode_0035);
        map.put("0067", sCode_0067);
        map.put("0039", sCode_0039);
        map.put("0072", sCode_0072);
        map.put("0006", sCode_0006);
    }

    @Override
    public Optional<SCode> findById(String id) {
        return Optional.of(map.get(id));
    }

    @Override
    public List<SCode> findAllByEmployeeId(String id) {
        return List.of(map.get(id));
    }

    @Override
    public Optional<SCode> saveSCode(SCode entity) {
        log.info("save SCode {}", entity);
        Objects.requireNonNull(entity, "Entity must not be null");
        if (!map.containsKey(entity.getId())) {
            map.put(entity.getId(), entity);
            return Optional.of(entity);
        }
        return Optional.ofNullable(map.computeIfPresent(entity.getId(), (id, oldT) -> entity));
    }

    @Override
    public boolean deleteSCodeById(String id) {
        log.info("delete SCode {}", id);
        return map.remove(id) != null;
    }

    @Override
    public List<SCode> findAllSCodes() {
        log.info("getAll SCode");
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<SCode> deleteById(String id) {
        return Optional.of(map.remove(id));
    }

}
