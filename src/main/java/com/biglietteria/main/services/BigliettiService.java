package com.biglietteria.main.services;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biglietteria.main.connectinos.ConnectionSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import java.sql.ResultSet;

@Service
public class BigliettiService {

    private final BigliettiRepository bigliettiRepository;

    BigliettiService(BigliettiRepository  bigliettiRepository) {
        this.bigliettiRepository= bigliettiRepository;
    }

    @Transactional(readOnly = true)
    public List<Biglietti> list(Pageable pageable) {
        return bigliettiRepository.findAllBy(pageable).toList();
    }
    
    public void deleteById(String id) {
    	bigliettiRepository.deleteById(id);
    }
    
    public void buyTicket(String codiceoperazione, 
    					  String codicecliente, 
    					  String codicereplica, 
    					  LocalDate datareplica, 
    					  String tipoPagamento, 
    					  int quantita) {
    	
    	ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
    	Connection con;
    	
    	System.out.println("Codice replica : " + codicereplica);
    	
    	try {
			con = connectionSingleton.getConnection();
			//Available seats
			
			
			
			PreparedStatement ps = con.prepareStatement("SELECT teatri.posti FROM Biglietti "
					+ "INNER JOIN Replica USING(codicereplica)"
					+ "INNER JOIN Spettacolo USING(codicespettacolo)"
					+ "INNER JOIN Teatri USING(codiceteatro)"
					+ "WHERE Replica.codicereplica = ? AND Replica.datareplica = ?;");
			ps.setString(1, codicereplica);
			ps.setString(2, datareplica.toString());
			//The maximum number of seats available at the theater
			int max = 0;
			
			ResultSet result = ps.executeQuery();
			
			if(result.isBeforeFirst()) {
				result.next();
				max = result.getInt(1);
				System.out.println("THE MAXIMUM IS : " + max);
			} else {
				ps = con.prepareStatement("SELECT teatri.posti FROM Replica "
						+ "INNER JOIN Spettacolo USING(codicespettacolo)"
						+ "INNER JOIN Teatri USING(codiceteatro)"
						+ "WHERE Replica.codicereplica = ? AND Replica.datareplica = ?;");
				ps.setString(1, codicereplica);
				ps.setString(2, datareplica.toString());
				
				result = ps.executeQuery();
				
				result.next();
				max = result.getInt(1);
				
			}
			
			
			int used = 0;
			
			
			System.out.println("CODICE REPLICA : " + codicereplica);
			
			ps = con.prepareStatement("SELECT SUM(quantita) FROM Biglietti WHERE codicereplica = ?;");
			
			ps.setString(1, codicereplica);
			result = ps.executeQuery();
			
			if(result.isBeforeFirst()) {
				result.next();
				used = result.getInt(1);
			}
			
			//Find out if there already is a similar ticket
			ps = con.prepareStatement("SELECT quantita FROM Biglietti WHERE codiceoperazione = ? AND codicecliente = ? AND codicereplica = ?;");
			ps.setString(1, codiceoperazione + codicereplica);
			ps.setString(2, codicecliente);
			ps.setString(3, codicereplica);
			
			result = ps.executeQuery();
			
			//If there is a similar ticket just increment the number of tickets
			if(result.isBeforeFirst() && used + quantita <= max) {
				
				ps = con.prepareStatement("UPDATE Biglietti SET quantita = quantita + ? WHERE codiceoperazione = ? AND codicecliente = ? AND codicereplica = ?;");
				ps.setInt(1, quantita);
				ps.setString(2, codiceoperazione + codicereplica);
				ps.setString(3, codicecliente);
				ps.setString(4, codicereplica);
				
				ps.executeUpdate();
				
				System.out.println("USED + QUANTITA : " + (used+quantita));
				
			} else if(used + quantita <= max){
				//If a similar ticket does not exist add a new record
				
				System.out.println("INSERT NEW");
				Biglietti bought = new Biglietti(codiceoperazione + codicereplica, codicecliente, codicereplica, datareplica, tipoPagamento, quantita);
		    	bigliettiRepository.saveAndFlush(bought);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}
