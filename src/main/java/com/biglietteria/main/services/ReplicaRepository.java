package com.biglietteria.main.services;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface ReplicaRepository extends JpaRepository<Replica, String>, JpaSpecificationExecutor<Replica> {
    Slice<Replica> findAllBy(Pageable pageable);
}

