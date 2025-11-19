package com.biglietteria.main.connectinos;

import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.cj.jdbc.MysqlDataSource;

public class ConnectionSingleton {
   
   private static ConnectionSingleton instance;
   
   
   private ConnectionSingleton() {}
   
   public static ConnectionSingleton getInstance() {
       if (instance == null) {
           instance = new ConnectionSingleton();
       }
       return instance;
   }
   
   public Connection getConnection() throws SQLException {
       MysqlDataSource ds = new MysqlDataSource();
           ds.setServerName("localhost");
           ds.setPortNumber(3306);
           ds.setUser("root");
           ds.setPassword("");
           ds.setDatabaseName("app");
           ds.setUseSSL(false); // Secure Sockets Layer è un protocollo di sicurezza per una
                                // connessione crittografata tra applicazione e DB
           ds.setAllowPublicKeyRetrieval(true); // ottenere la chiave pubblica del server per
                                                // stabilire una connessione sicura
       
       return ds.getConnection();
   }
}
