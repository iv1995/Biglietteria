package com.biglietteria.main.services;

import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "biglietti")
public class Biglietti {
	
    @Id
    @Column(name = "codiceoperazione")
    private String codiceoperazione;

    @Column(name = "codicecliente", nullable = false)
    private String codicecliente;

    @Column(name = "codicereplica")
    @Nullable
    private String codicereplica;
    
    @Column(name = "dataora")
    private LocalDate dataora;
    
    @Column(name = "tipopagamento")
    private String tipopagamento;
    
    @Column(name = "quantita")
    private int quantita;

    protected Biglietti() { // To keep Hibernate happy
    }
    
    
    public Biglietti(String codiceoperazione, String codicecliente, String codicereplica, LocalDate dataora, String tipopagamento, int quantita) {
    	this.codiceoperazione = codiceoperazione;
    	this.codicecliente = codicecliente;
    	this.codicereplica = codicereplica;
    	this.dataora = dataora;
    	this.tipopagamento = tipopagamento;
    	this.quantita = quantita;
    }
    
    public String getCodiceOperazione() {
    	return this.codiceoperazione;
    }
    
    public String getCodiceCliente() {
    	return this.codicecliente;
    }
    
    public String getCodiceReplica() {
    	return this.codicereplica;
    }
    
    public int getQuantita() {
    	return this.quantita;
    }
    
    public LocalDate getDataOra() {
    	return this.dataora;
    }
    
    public String getTipoPagamento() {
    	return this.tipopagamento;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !getClass().isAssignableFrom(obj.getClass())) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Biglietti other = (Biglietti) obj;
        return getCodiceOperazione() != null && getCodiceCliente().equals(other.getCodiceCliente()) 
        		&& getCodiceReplica().equals(other.getCodiceReplica()) && getDataOra().equals(other.getDataOra())
        		&& getTipoPagamento().equals(other.getTipoPagamento()) && getQuantita() == other.getQuantita();
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

