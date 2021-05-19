package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

	public static final String  DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String IP = "127.0.0.1";
	public static final String PORT = "3306";
	public static final String USER = "root";
	public static final String PWD = "";
	public static final String DBNAME = "ecolanda";
	//public static 
	public static final String  URL = "jdbc:mysql://"+IP+":"+PORT+"/"+DBNAME;
	
	public Connection conexion;
	
	public Conexion() {
		if (Conectar() != false) {
			System.out.println("Ok");
		}
		
	}
	

	
	public  Connection getConexion() {
		return conexion;
	}
	
	
	
	public boolean Conectar()
	{
		boolean estado = false;
		conexion = null;
		
		try {
			Class.forName(DRIVER);
			conexion = DriverManager.getConnection(URL, USER, PWD);
			
			if (conexion != null) {
<<<<<<< HEAD
				System.out.println("Conexion establecida correctamente.");
=======
				System.out.println("Conexión establecida correctamente.");
>>>>>>> main
				estado = true;
			}
			
		} catch (ClassNotFoundException | SQLException  e) {
<<<<<<< HEAD
			System.out.println("Error en la conexion " + e);
=======
			System.out.println("Error en la conexión " + e);
>>>>>>> main
		}
		
		return estado;
	}
	
	
	public boolean Desconectar()
	{
		boolean estado = false;
		conexion = null;
		if (conexion == null) {
<<<<<<< HEAD
			System.out.println("Fin de sesion");
=======
			System.out.println("Fin de sesión");
>>>>>>> main
			estado = true;
		}
		
		return estado;
	}



}
