package conexionBD;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

/**Esta clase maneja la cantidad de conexiones simultaneas a un sistema*/
public class PoolConnection
{
	private Vector <Connection> connections = new Vector<Connection>();
	protected String jdbc;
	protected String servidor;
	protected String usuario;
	protected String password;
	protected int cantCon;
	private static PoolConnection pool;
	private static String nameDB;
	
	
	private PoolConnection()
	{
		getConfiguration();
		for (int i= 0; i< cantCon;i++)
			connections.add(connect());
	}
	
	public static PoolConnection getPoolConnection()
	{
		if (pool== null)
			pool =new PoolConnection();
		return pool;
	}
	private Connection connect()
	{
		try
		{
			  Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbConnectString = jdbc + servidor; 
            Connection con = DriverManager.getConnection (dbConnectString, usuario, password);
            
            return con;
		}
		catch (SQLException e)
		{
			System.out.println("Mensaje Error: " + e.getMessage());
			System.out.println("Stack Trace: " + e.getStackTrace());
			return null;
		}
		catch (Exception ex)
		{
			System.out.println("Mensaje Error: " + ex.getMessage());
			System.out.println("Stack Trace: " + ex.getStackTrace());
			return null;
		}
	}
	public void getConfiguration()
	{
		String configuracion = "ConfigDB.txt";
	    Properties propiedades;
	    
	    try 
	    {
	       FileInputStream f = new FileInputStream(configuracion);
	       propiedades = new Properties();
	       propiedades.load(f);
	       f.close();
	 
	       jdbc = propiedades.getProperty("jdbc"); 
	       servidor = propiedades.getProperty("servidor");
	       usuario = propiedades.getProperty("usuario");
	       password = propiedades.getProperty("password");
	       cantCon = Integer.parseInt(propiedades.getProperty("conexiones"));
	       nameDB= propiedades.getProperty("nombreDB");
	     } 
	    catch (Exception e) 
	     {
				System.out.println("Mensaje Error: " + e.getMessage());
				System.out.println("Stack Trace: " + e.getStackTrace());
	     }
	}
	public void closeConnections()
	{
		for (int i=0; i<connections.size();i++)
		{
			try
			{
				connections.elementAt(i).close();
			}
			catch(Exception e)
			{
				System.out.println("Mensaje Error: " + e.getMessage());
				System.out.println("Stack Trace: " + e.getStackTrace());
			}
		}
	}
	public  Connection getConnection()
	{
		Connection c = null;
		if (connections.size()>0)
			c = connections.remove(0);
		else
		{
			c = connect();
			System.out.println("Se ha creado una nueva conexion fuera de los parametros de configuracion");
		}
		return c;
	}
	public void realeaseConnection(Connection c)
	{
		connections.add(c);
	}
	
	public static String getNameDB(){
		return nameDB;
	}
}
