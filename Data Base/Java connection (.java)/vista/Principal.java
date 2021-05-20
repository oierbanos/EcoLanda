package vista;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
//import java.sql.*;

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
			String sql = "select * from usuario where nombre=? and apellido=?";

			PreparedStatement preparedStatement =
					connection.prepareStatement(sql);
			preparedStatement.setString(1, "adam");
			preparedStatement.setString(2, "hanga");

			ResultSet result = preparedStatement.executeQuery();

			/*Statement  statement= (Statement) connection.createStatement();
			ResultSet resultSet = (ResultSet) statement.executeQuery("SELECT * FROM usuario");*/
			
			while(result.next())
			{
				System.out.println(result.getString("username") + " "+
						result.getString("nombre"));
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
