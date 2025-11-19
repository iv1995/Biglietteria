package com.biglietteria.main.services;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface BigliettiRepository extends JpaRepository<Biglietti, String>, JpaSpecificationExecutor<Biglietti> {
    Slice<Biglietti> findAllBy(Pageable pageable);
}

