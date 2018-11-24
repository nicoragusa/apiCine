package persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import negocio.*;
import conexionBD.*;

public class AdmPersistenciaUsuario extends AdministradorPersistencia {
	private Connection c;
	private static AdmPersistenciaUsuario instancia=null;
	
	@Override
	public void insert(Object o) {
		// TODO Auto-generated method stub
		Usuario u=(Usuario)o;
		try{
			c=PoolConnection.getPoolConnection().getConnection();
			
			PreparedStatement s=c.prepareStatement(
					"INSERT INTO "+PoolConnection.getNameDB()+".Usuario values (?,?,?,?,?,?,?,?,1)");
			//Asignacion de valores
			s.setString(1, u.getDni());
			s.setString(2, u.getNombreUsuario());
			s.setString(3, u.getPassword());
			s.setString(4, u.getNombre());
			s.setString(5, u.getApellido());
			s.setString(6, u.getDom());			
			s.setString(7, u.getFechaNac().toString());	
			s.setString(8, u.getMail());
			s.execute();
			
		}catch(Exception e){
			System.out.println("ERROR: InsertUsuario");
		}
		finally{
			PoolConnection.getPoolConnection().realeaseConnection(c);
		}
	}
		
	@Override
	public void update(Object o) {
		// TODO Auto-generated method stub
		Usuario u=(Usuario)o;
		try{
			c=PoolConnection.getPoolConnection().getConnection();
			
			PreparedStatement s=c.prepareStatement(
					"UPDATE "+PoolConnection.getNameDB()+".Usuario SET nombreUsuario=?, contrasenia=?, nombre=?, apellido=?, "
							+ "domicilio=?, fechaNac=?, mail=? where dni=?");
			//Asignacion de valores			
			s.setString(1, u.getNombreUsuario());
			s.setString(2, u.getPassword());
			s.setString(3, u.getNombre());
			s.setString(4, u.getApellido());
			s.setString(5, u.getDom());			
			s.setDate(6, Date.valueOf(u.getFechaNac()));	
			s.setString(7, u.getMail());
			s.setString(8, u.getDni());
			s.execute();
			
		}catch(Exception e){
			System.out.println("ERROR: UpdateUsuario "+e.getMessage());
		}
		finally{
			PoolConnection.getPoolConnection().realeaseConnection(c);
		}
	}

	@Override
	public void delete(Object d) {
		// TODO Auto-generated method stub
		Usuario u=(Usuario)d;
		try{
			c=PoolConnection.getPoolConnection().getConnection();
			
			PreparedStatement s=c.prepareStatement(
					"update "+PoolConnection.getNameDB()+".Usuario set estado=0 where dni=?");
			//Asignacion de valores
			s.setString(1, u.getDni());
			s.execute();
			Vector<Rol> roles=u.getRoles();
			for(int i=0; i<roles.size(); i++)
				AdmPersistenciaRol.getInstancia().delete(roles.elementAt(i));
		}catch(Exception e){
			System.out.println("ERROR: DeleteUsuario" +e.getMessage());
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
	public Vector<Usuario> select() {
		Connection c=PoolConnection.getPoolConnection().getConnection();
		Vector<Usuario> vec=new Vector<Usuario>();
		try{
			PreparedStatement p=c.prepareStatement("Select * from "+PoolConnection.getNameDB()+".Usuario");
			ResultSet rs=p.executeQuery();
			if(rs.next()){
				Usuario u=
						new Usuario(
							rs.getString("mail"),
							rs.getString("nombreUsuario"),
							rs.getString("contrasenia"),
							rs.getString("nombre"),
							rs.getString("domicilio"),
							rs.getString("dni"),
							rs.getString("apellido"),
							rs.getDate("fechaNac").toLocalDate(),
							true
						);
				u.setRoles(AdmPersistenciaRol.getInstancia().selectAll(u));
				vec.add(u);
			}
									
		}catch(Exception e){
			System.out.println("Error en selectAll "+e.getMessage());
		}
		finally{
			PoolConnection.getPoolConnection().realeaseConnection(c);
		}
		return vec;
		
	}
	public static AdmPersistenciaUsuario getInstancia() {
		if(instancia==null){
			instancia=new AdmPersistenciaUsuario();
		}
		return instancia;
	}

	public Usuario buscarUsuarioPorDni(String dni) {
		// TODO Auto-generated method stub
		Connection c=PoolConnection.getPoolConnection().getConnection();
		Usuario u=null;
		try{
			PreparedStatement p=c.prepareStatement("Select * from "+PoolConnection.getNameDB()+".Usuario where estado=1 and dni=?");
			p.setString(1, dni);
			ResultSet rs=p.executeQuery();
			if(rs.next()){
				u=new Usuario(
						rs.getString("mail"),
						rs.getString("nombreUsuario"),
						rs.getString("contrasenia"),
						rs.getString("nombre"),
						rs.getString("domicilio"),
						rs.getString("dni"),
						rs.getString("apellido"),
						rs.getDate("fechaNac").toLocalDate(),						
						true
				);				
			}
						
			
		}catch(Exception e){
			System.out.println("Error en buscarUsuarioDni "+e.getMessage());
		}
		finally{
			PoolConnection.getPoolConnection().realeaseConnection(c);
		}
		return u;
		
	}

	public Usuario buscarUsuarioPorNombreUsuario(String us) {
		Connection c=PoolConnection.getPoolConnection().getConnection();
		Usuario u=null;
		try{
			PreparedStatement p=c.prepareStatement("Select * from "+PoolConnection.getNameDB()+".Usuario where nombreUsuario=?");
			p.setString(1, us);
			ResultSet rs=p.executeQuery();
			if(rs.next()){
				u=new Usuario(
						rs.getString("mail"),
						rs.getString("nombreUsuario"),
						rs.getString("contrasenia"),
						rs.getString("nombre"),
						rs.getString("domicilio"),
						rs.getString("dni"),
						rs.getString("apellido"),
						rs.getDate("fechaNac").toLocalDate(),						
						true
				);
				//u.setRoles(AdmPersistenciaRol.getInstancia().selectAll(u));				
			}
						
			
		}catch(Exception e){
			System.out.println("Error en buscarUsuarioUser "+e.getMessage());
		}
		finally{
			PoolConnection.getPoolConnection().realeaseConnection(c);
		}
		return u;
	}

}
