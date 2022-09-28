package com.lvv.ttimpex2.repository.inmemory;

import com.lvv.ttimpex2.molel.SCode;
import com.lvv.ttimpex2.repository.SCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Vitalii Lypovetskyi
 */
@Repository
public class InMemorySCodeRepository implements SCodeRepository {
    private final Map<String, SCode> map = new ConcurrentHashMap<>();
    private static final Logger log = LoggerFactory.getLogger(InMemorySCodeRepository.class);

    public InMemorySCodeRepository() {
        save(new SCode("0012", "12345678"));
        save(new SCode("0035", "asdfghjk"));
        save(new SCode("0067", "ertyuioo"));
        save(new SCode("0039", "234ert56"));
        save(new SCode("0072", "fgh678yu"));
        save(new SCode("0006", "r5t6y7u8"));
    }

    @Override
    public SCode save(SCode entity) {
        log.info("save SCode {}", entity);
        Objects.requireNonNull(entity, "Entity must not be null");
        if (!map.containsKey(entity.getId())) {
            map.put(entity.getId(), entity);
            return entity;
        }
        return map.computeIfPresent(entity.getId(), (id, oldT) -> entity);
    }

    @Override
    public boolean delete(String id) {
        log.info("delete SCode {}", id);
        return map.remove(id) != null;
    }

    @Override
    public SCode get(String id) {
        log.info("get SCode {}", id);
        return map.get(id);
    }

    @Override
    public Collection<SCode> getCollection() {
        log.info("getAll SCode");
        return map.values();
    }

}
