package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import conexionBD.PoolConnection;
import negocio.AsientoVendido;
import negocio.Entrada;
import negocio.Funcion;

public class AdmPersistenciaEntrada extends AdministradorPersistencia{
	private static AdmPersistenciaEntrada instancia=null;
	private Connection c;
	
	public static AdmPersistenciaEntrada getInstancia() {
		// TODO Auto-generated method stub
		if(instancia==null)
			instancia= new AdmPersistenciaEntrada();
		return instancia;
	}
	
	@Override
	public void insert(Object o) {
		Entrada en=(Entrada)o;
		c=PoolConnection.getPoolConnection().getConnection();
		try{
			String bd=PoolConnection.getNameDB();
			String sql="INSERT INTO "+bd+".Entrada values(?,?,?,?,?,?)";
			/////////////////////////////////////////////////
			String hFuncion=en.getFuncion().getHorario().toString();
			String dFuncion=en.getFuncion().getDia().toString();
			String nPelicula=en.getFuncion().getPelicula().getNombre();
			String iPelicula=en.getFuncion().getPelicula().getIdioma();	
						
			int idFuncion=AdmPersistenciaFuncion.getInstancia().getIdFuncion(nPelicula,iPelicula,hFuncion,dFuncion);
			int fila=en.getAsiento().getAsientoF().getFila();
			int columna=en.getAsiento().getAsientoF().getColumna();
			int idVendido=AdmPersistenciaAsientos.getInstancia().getIdAsientoVendido(fila,columna,idFuncion);
			/////////////////////////////////////////////////
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setInt(1, idFuncion);
			ps.setString(2, hFuncion);
			ps.setString(3, dFuncion);
			ps.setBoolean(4, false);
			ps.setInt(5, idVendido);
			ps.setFloat(6, en.getPrecio());
			ps.execute();
		}catch(Exception e){
			System.out.println("Error en insert. Detalle:\n"+e.getMessage()+"\nStackTrace:\n"+e.getStackTrace());
		}finally{
			PoolConnection.getPoolConnection().realeaseConnection(c);
		}
	}
	@Override
	public void update(Object o) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void delete(Object d) {
		// TODO Auto-generated method stub
	}
	public void delete(Entrada e,Long codigoVta) {
		try{
			c=PoolConnection.getPoolConnection().getConnection();
		
			ResultSet rs=c.createStatement().executeQuery("SELECT * FROM "+PoolConnection.getNameDB()+". EntradaVenta where idVenta="+codigoVta);
			while(rs.next()){
				int idEntrada=rs.getInt("idEntrada");
				c.createStatement().execute("UPDATE FROM "+PoolConnection.getNameDB()+".Entrada set estadoRetiro=0 where idEntrada="+idEntrada);
			}
		}
		catch(Exception ex){
			System.out.println("Error delete entrada");
		}
	}
	
	@Override
	public Vector<Object> select(Object o) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Vector<Entrada> getEntradas(Funcion f,int idFuncion){
		Vector<Entrada> res=new Vector<Entrada>();
		c=PoolConnection.getPoolConnection().getConnection();
		try{
			Statement s=c.createStatement();
			ResultSet rs=s.executeQuery("SELECT * FROM "+PoolConnection.getNameDB()+".Entrada where idFuncion="+idFuncion);
			while(rs.next()){
				AsientoVendido av=AdmPersistenciaAsientos.getInstancia().getAsientoVendido(rs.getInt("idAsientoV"));
				Entrada e=new Entrada(f,av,rs.getFloat("precio"),rs.getBoolean("estadoRetiro"));
				res.add(e);
			}
		}catch(Exception e){
			System.out.println("Error en getEntradas(idfuncion). Detalle:\n"+e.getMessage()+"\nStackTrace:\n"+e.getStackTrace());
		}finally{
			PoolConnection.getPoolConnection().realeaseConnection(c);
		}
		return res;
	}
	
	/**Obtengo todas las entradas vendidas asociadas al idVenta*/
	public Vector<Entrada> getEntradas(int idVenta) {
		// TODO Auto-generated method stub
		Vector<Entrada>entradas=new Vector<Entrada>();
		c=PoolConnection.getPoolConnection().getConnection();
		try{
			Statement s=c.createStatement();
			ResultSet rs=s.executeQuery("SELECT * FROM EntradaVenta where idVenta="+idVenta);
			while(rs.next()){
				Entrada e=buscarEntrada(rs.getInt("idEntrada"));
				entradas.add(e);
			}
		}catch(Exception e){
			System.out.println("Error en getEntradas(idVenta). Detalle:\n"+e.getMessage()+"\nStackTrace:\n"+e.getStackTrace());
		}finally{
			PoolConnection.getPoolConnection().realeaseConnection(c);
		}
		
		return entradas;
	}
	
	public Entrada buscarEntrada(int idEntrada){
		Entrada res=null;
		c=PoolConnection.getPoolConnection().getConnection();
		try{
			Statement s=c.createStatement();
			ResultSet rs=s.executeQuery("SELECT * FROM "+PoolConnection.getNameDB()+".Entrada where idEntrada="+idEntrada);
			while(rs.next()){
				Funcion f=AdmPersistenciaFuncion.getInstancia().buscarFuncion(rs.getInt("idFuncion"));
				AsientoVendido av=AdmPersistenciaAsientos.getInstancia().getAsientoVendido(rs.getInt("idAsientoV"));
				res=new Entrada(f,av,rs.getFloat("precio"),rs.getBoolean("estadoRetiro"));
			}
		}catch(Exception e){
			System.out.println("Error en getEntradas(idfuncion). Detalle:\n"+e.getMessage()+"\nStackTrace:\n"+e.getStackTrace());
		}finally{
			PoolConnection.getPoolConnection().realeaseConnection(c);
		}
		return res;
	}
	
	public int getIdEntrada(Entrada en){
		int idEntrada=-1;
		c=PoolConnection.getPoolConnection().getConnection();
		try{
			int idFuncion=AdmPersistenciaFuncion.getInstancia().getIdFuncion(en.getFuncion().getPelicula().getNombre(),en.getFuncion().getPelicula().getIdioma(),en.getFuncion().getHorario().toString(),en.getFuncion().getDia().toString());
			String sql="SELECT * FROM "+PoolConnection.getNameDB()+".Entrada where idFuncion=? and horarioFuncion=? and diaFuncion=?";
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setInt(1, idFuncion);
			ps.setString(2, en.getFuncion().getHorario().toString());
			ps.setString(3, en.getFuncion().getDia().toString());
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				idEntrada=rs.getInt("idEntrada");
			}
		}catch(Exception e){
			System.out.println("Error en getEntradas(idfuncion). Detalle:\n"+e.getMessage()+"\nStackTrace:\n"+e.getStackTrace());
		}finally{
			PoolConnection.getPoolConnection().realeaseConnection(c);
		}
		return idEntrada;
	}

}
