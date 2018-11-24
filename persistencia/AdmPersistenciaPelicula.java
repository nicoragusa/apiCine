package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import conexionBD.PoolConnection;
import view.Pelicula_View;
import negocio.Operador;
import negocio.Pelicula;
import negocio.Usuario;

public class AdmPersistenciaPelicula extends AdministradorPersistencia {

	private static AdmPersistenciaPelicula instancia;

	public AdmPersistenciaPelicula() {

	}

	public static AdmPersistenciaPelicula getInstancia() {
		if (instancia == null)
			instancia = new AdmPersistenciaPelicula();
		return instancia;
	}

	public void insert(Object o) {
		try {
			Pelicula p = (Pelicula) o;
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("insert into "+PoolConnection.getNameDB()+".Pelicula values "
					+ "(?,?,?,?,?,?,?,?,?,?)");
			s.setString(1, p.getNombre());
			s.setString(2, p.getDirector());
			s.setString(3, p.getGenero());
			s.setInt(4, p.getDuracion());
			s.setString(5, p.getIdioma());
			s.setBoolean(6, p.isSubtitulos());
			s.setString(7, p.getClasificacion());
			s.setString(8, p.getObservacion());
			s.setString(9, p.getOp().getUsuario().getDni());
			s.setBoolean(10, p.isEstado());
			s.execute();
			PoolConnection.getPoolConnection().realeaseConnection(con);
		} catch (Exception e) {
			System.out.println("Error insertPelicula "+e.getMessage());
		}

	}

	public void update(Object o) {
		/*
		 * 
		 */

	}

	public void delete(Object d) {
		try {
			Pelicula p = (Pelicula) d;
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con
					.prepareStatement("update "+PoolConnection.getNameDB()+".Pelicula set estado = ? "
							+ "where nombre = ? and idioma = ? ");
			s.setBoolean(1, false);
			s.setString(2, p.getNombre());
			s.setString(3, p.getIdioma());
			s.execute();
			PoolConnection.getPoolConnection().realeaseConnection(con);
		} catch (Exception e) {
			System.out.println("Error deletePelicula "+e.getMessage());
		}
	}

	public Vector<Object> select(Object o) {

		return null;
	}
	
	public void update1(int duracion,String director, String genero,
			String clasificacion,String observacion,boolean subtitulos,String nombre,String idioma) {
		try{
				Connection con = PoolConnection.getPoolConnection().getConnection();
				PreparedStatement s = con.prepareStatement("update "+PoolConnection.getNameDB()+".Pelicula " +
						
				" set director = ?,"+
				" genero = ?," +
				" duracion =?," +
				" subtitulos=?,"+
				" clasificacion=?,"+ 
				" observacion=?"+
				" where nombre = ? and idioma = ? ");
				s.setString(1,director);
				s.setString(2, genero);
				s.setInt(3, duracion);
				s.setBoolean(4, subtitulos);
				s.setString(5, clasificacion);
				s.setString(6, observacion);
				s.setString(7, nombre);
				s.setString(8, idioma);
				s.execute();
				PoolConnection.getPoolConnection().realeaseConnection(con);
		}
		catch (Exception e)
			{
			System.out.println("Error en update1Pelicula "+e.getMessage());
			}
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	public Pelicula buscarPelicula(String nombre, String idioma) {
		try {
			Pelicula p = null;
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con
					.prepareStatement("select * from "+PoolConnection.getNameDB()+".Pelicula where nombre = ? and idioma = ? and estado = ?");
			s.setString(1, nombre);
			s.setString(2, idioma);
			s.setBoolean(3, true);
			ResultSet result = s.executeQuery();
			while (result.next()) {
				String nombrebd = result.getString(1);
				String director = result.getString(2);
				String genero = result.getString(3);
				int duracion = result.getInt(4);
				String idiomabd = result.getString(5);
				boolean subtitulos = result.getBoolean(6);
				String clasificacion = result.getString(7);
				String observacion = result.getString(8);
				String dniOp = result.getString(9);
				boolean estado = result.getBoolean(10);
				Usuario u = AdmPersistenciaUsuario.getInstancia().buscarUsuarioPorDni(dniOp);
				Operador op = (Operador)u.getOperador();
				p = new Pelicula(duracion, subtitulos, op, estado, nombrebd, director, genero,
						idiomabd, clasificacion, observacion);
			}
			PoolConnection.getPoolConnection().realeaseConnection(con);
			return p;
		} catch (Exception e) {
			System.out.println("Error buscarPelicula "+e.getMessage());
		}
		return null;
	}
	public Vector<Pelicula_View> select1() {
		try {
			Vector<Pelicula_View> peliculas = new Vector<Pelicula_View>();
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("select * from "+PoolConnection.getNameDB()+".Pelicula where estado = ?");
			s.setBoolean(1, true);
			ResultSet result = s.executeQuery();
			while (result.next()) {
				Pelicula_View p = new Pelicula_View(
							result.getString("nombre"),
							result.getString("director"),
							result.getString("genero"),
							result.getInt("duracion"),
							result.getString("idioma"),
							result.getBoolean("subtitulos"),
							result.getString("calificacion"),
							result.getString("observacion"),
							true
						);
				peliculas.add(p);
			}
			PoolConnection.getPoolConnection().realeaseConnection(con);
			return peliculas;
		} catch (Exception e) {
			System.out.println("Error en select1Pelicula "+e.getMessage());
		}
		return null;
	}
	
	public Vector<Pelicula> getPeliculas() {
		// TODO Auto-generated method stub
		Vector<Pelicula> peliculas = null;
		try {
			 peliculas = new Vector<Pelicula>();
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("select * from "+PoolConnection.getNameDB()+".Pelicula where estado = ?");
			s.setBoolean(1, true);
			ResultSet result = s.executeQuery();
			while (result.next()) {
				Operador op=(Operador)AdmPersistenciaUsuario.getInstancia().buscarUsuarioPorDni(result.getString("idOperador")).getOperador();
				Pelicula p=new Pelicula(
					result.getInt("duracion"),
					result.getBoolean("subtitulos"),
					op,
					result.getBoolean("estado"),
					result.getString("nombre"),
					result.getString("director"),
					result.getString("genero"),
					
					result.getString("idioma"),
					
					result.getString("calificacion"),
					result.getString("observacion")
					);
				peliculas.add(p);
			}
			PoolConnection.getPoolConnection().realeaseConnection(con);
		} catch (Exception e) {
			System.out.println("Error en getPeliculas() "+e.getMessage());
		}
		return peliculas;
	}

}
