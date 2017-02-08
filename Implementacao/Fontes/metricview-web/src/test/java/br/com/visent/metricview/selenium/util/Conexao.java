package br.com.visent.metricview.selenium.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

	/**
	 * Classe que faz a conexao com o banco.
	 * @return
	 * 		retorna a conexao.
	 */
	public Connection getConnection() {
		// TODO Auto-generated method stub
		Connection connection = null;    
        try {    
            // Load the JDBC driver    
            String driverName = "oracle.jdbc.driver.OracleDriver";    
            Class.forName(driverName);    

            // Create a connection to the database    
            String serverName = "192.168.200.73";    
            String portNumber = "1521";    
            String sid 		  = "metricview";    
            String url 		  = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;    
            String username   = "metricview";    
            String password   = "metricview";    
            connection 		  = DriverManager.getConnection(url, username, password);    

        } catch (ClassNotFoundException e) {    
            System.out.println("ClassNotFoundException" + e.getMessage());  
        } catch (SQLException e) {    
            System.out.println("SQLException" + e.getMessage());  
        } catch (Exception e) {  
            System.out.println("Exception = " + e.getMessage());  
        }
		return connection;  
       
	}

}
