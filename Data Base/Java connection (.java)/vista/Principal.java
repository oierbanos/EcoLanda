package vista;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.*;

import conexion.Conexion;

public class Principal {
	
	Conexion conexion;
	Connection connection;
	//Statement statement;
	//Resultset resultset;
	
	public Principal() {
		
		conexion = new Conexion();
	}
	
	
	public void db()
	{
		connection = conexion.getConexion();
		try {
			Statement  statement= (Statement) connection.createStatement();
			ResultSet resultSet = (ResultSet) statement.executeQuery("SELECT * FROM usuario");
			
			while(resultSet.next())
			{
				System.out.println(resultSet.getString("username") + " "+
						resultSet.getString("nombre"));
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		
		Principal principal = new Principal();
		principal.db();
		

	}

}
