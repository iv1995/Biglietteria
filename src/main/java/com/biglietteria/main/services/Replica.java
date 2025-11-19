package com.biglietteria.main.services;

import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "replica")
public class Replica {
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "codicereplica")
    private String codicereplica;

    @Column(name = "codicespettacolo", nullable = false)
    private String codicespettacolo;

    @Column(name = "datareplica")
    @Nullable
    private LocalDate datareplica;

    protected Replica() { // To keep Hibernate happy
    }
    
    public String getCodiceReplica() {
    	return codicereplica;
    }
    
    public LocalDate newDataora() {
    	return datareplica;
    }
    
    public LocalDate getDataReplica() {
    	return this.datareplica;
    }
    
    public String getCodiceSpettacolo() {
    	return codicespettacolo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !getClass().isAssignableFrom(obj.getClass())) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Replica other = (Replica) obj;
        return getCodiceReplica() != null && getCodiceReplica().equals(other.getCodiceReplica());
    }

    @Override
    public int hashCode() {
        // Hashcode should never change during the lifetime of an object. Because of
        // this we can't use getId() to calculate the hashcode. Unless you have sets
        // with lots of entities in them, returning the same hashcode should not be a
        // problem.
        return getClass().hashCode();
    }
}

