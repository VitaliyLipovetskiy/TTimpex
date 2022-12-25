package com.lvv.ttimpex2.repository.inmemory;

import com.lvv.ttimpex2.molel.AbstractBaseEntity;
import com.lvv.ttimpex2.molel.Department;
import com.lvv.ttimpex2.molel.HasId;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryBaseRepository<T extends HasId> {

    final Map<String, T> map = new ConcurrentHashMap<>();

    public Optional<T> save(T entity) {
        Objects.requireNonNull(entity, "Entity must not be null");
        if (entity.isNew()) {
            entity.setId(UUID.randomUUID().toString());
            map.put(entity.getId(), entity);
            return Optional.of(entity);
        }
        return Optional.of(map.computeIfPresent(entity.getId(), (id, oldT) -> entity));
    }

    public Optional<T> deleteById(String id) {
        Optional<T> optionalT = getById(id);
        if (optionalT.isPresent()) {
            map.remove(id, optionalT.get());
            return optionalT;
        }
        return Optional.empty();
    }

    public Optional<T> getById(String id) {
        return Optional.of(map.get(id));
    }

    Collection<T> getCollection() {
        return map.values();
    }

    void put(T entity) {
        Objects.requireNonNull(entity, "Entity must not be null");
        map.put(entity.id(), entity);
    }
}