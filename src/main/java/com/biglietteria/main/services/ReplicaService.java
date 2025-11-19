package com.biglietteria.main.services;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReplicaService {

    private final ReplicaRepository replicaRepository;

    ReplicaService(ReplicaRepository  replicaRepository) {
        this.replicaRepository= replicaRepository;
    }

    @Transactional(readOnly = true)
    public List<Replica> list(Pageable pageable) {
        return replicaRepository.findAllBy(pageable).toList();
    }
    
    public void deleteById(String id) {
    	replicaRepository.deleteById(id);
    }
}
