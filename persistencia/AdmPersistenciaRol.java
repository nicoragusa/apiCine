package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import negocio.*;
import conexionBD.*;

public class AdmPersistenciaRol extends AdministradorPersistencia {
	
	private static AdmPersistenciaRol instancia=null;
	private Connection c=null;
	
	private AdmPersistenciaRol(){}

	
	public static AdmPersistenciaRol getInstancia(){
		if(instancia==null)
			instancia=new AdmPersistenciaRol();
		return instancia;
	}
	
	@Override
	public void insert(Object o) {
		// TODO Auto-generated method stub
		Rol rol=(Rol)o;
		c=PoolConnection.getPoolConnection().getConnection();
		try{
			String sql="Insert into "+PoolConnection.getNameDB()+".UsuarioRol values (?,?,?)";
			//Statement s=c.createStatement();
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setInt(1, this.getId(rol.getDescripcion()));
			ps.setString(2, rol.getUsuario().getDni());
			ps.setBoolean(3, true);
			ps.execute();
			//s.execute("Insert into "+PoolConnection.getNameDB()+".UsuarioRol values ('"
					//+this.getId(rol.getDescripcion())+"','"+rol.getUsuario().getDni()+"')");
		}catch(Exception e){
			System.out.println("Error en insertar rol "+e.getMessage()+"\n"+e.getStackTrace());
		}
		finally{
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
		c=PoolConnection.getPoolConnection().getConnection();
		Rol nuevo=(Rol)d;
		try {
			Statement s=c.createStatement();
			s.execute("Update "+PoolConnection.getNameDB()+".UsuarioRol set estado=0 where idRol="+ this.getId(nuevo.getDescripcion())
			+"and idUser="+nuevo.getUsuario().getDni());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error en delete Rol " + e.getMessage());
		}
		finally{
			PoolConnection.getPoolConnection().realeaseConnection(c);
		}
	}

	@Override
	public Vector<Object> select(Object o) {	
		// TODO Auto-generated method stub
			return null;
	}
	
	public Vector<Rol> selectAll(Usuario u){
		Vector<Rol>roles=new Vector<Rol>();
		try{			
			c=PoolConnection.getPoolConnection().getConnection();
				
			Statement s=c.createStatement();
			ResultSet rs=s.executeQuery("Select * from "+PoolConnection.getNameDB()+".UsuarioRol where estado=1 and idUser="+u.getDni());
									
			while(rs.next()){
				//Traigo la descripcion de cada rol del usuario
				String descripcion=this.buscarRol(rs.getInt("idRol"));
				Rol r=null;
				switch(descripcion){
					case "Cliente": r=new Cliente(u); break;
					case "Administrador": r=new Administrador(u); break;
					case "Operador": r=new Operador(u); break;
					case "Vendedor": r=new Vendedor(u); break;
					case "Agente Comercial": r=new AgenteComercial(u); break;
				}
				roles.add(r);
			}
		}catch(Exception e){
			System.out.println("ERROR: selectAllRol() "+e.getMessage());
		}
		finally{
			PoolConnection.getPoolConnection().realeaseConnection(c);
		}
		return roles;
	}
	/**Dado un codigo de rol obtiene la descripcion*/
	public String buscarRol(int codigo){
		String res="";
		try{
			c=PoolConnection.getPoolConnection().getConnection();
			Statement s=c.createStatement();
			ResultSet rs=s.executeQuery("Select * from "+PoolConnection.getNameDB()+".Rol where id="+codigo);
			while(rs.next())
				res=rs.getString("Descripcion");
			PoolConnection.getPoolConnection().realeaseConnection(c);
		}catch(Exception e){
			System.out.println("ERROR: buscarRol()"+e.getMessage());
		}
		return res;
	}
	
	
	/**Dado una descripcion de rol obtiene la ID. Devuelve
	 * -1 si el rol no fue encontrado*/
	public int getId(String descripcion) {
		// TODO Auto-generated method stub
		int res=-1;
		try{
			c=PoolConnection.getPoolConnection().getConnection();
			Statement s=c.createStatement();
			ResultSet rs=s.executeQuery("Select * from "+PoolConnection.getNameDB()+".Rol where descripcion='"+descripcion+"'");//where descripcion="+descripcion);
			while(rs.next())
				res=rs.getInt("id");
			PoolConnection.getPoolConnection().realeaseConnection(c);
		}catch(Exception e){
			System.out.println("ERROR: getIdRol() "+e.getMessage());
		}
		return res;
	}
}
