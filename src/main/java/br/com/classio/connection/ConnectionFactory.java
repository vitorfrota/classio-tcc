/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.classio.connection;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
	
	public static Connection getConexao(){
		Connection conn = null;
		
		String url = "jdbc:mysql://localhost:3306/classio_teste";
		String user = "root";
		String senha = "1234";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection(url, user, senha);
			//System.out.println("Conexão ok  !");
			
		} catch (Exception e) {
			System.out.println("Erro na conexao !" +e.getMessage());
		}
		return conn;
	}
}
