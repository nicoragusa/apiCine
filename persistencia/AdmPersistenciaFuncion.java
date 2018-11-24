package persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Vector;

import conexionBD.PoolConnection;
import negocio.Cine;
import negocio.Funcion;
import negocio.Operador;
import negocio.Pelicula;
import negocio.Sala;
import negocio.Usuario;
import view.Cine_View;
import view.Funcion_View;
import view.Pelicula_View;
import view.Sala_View;

public class AdmPersistenciaFuncion extends AdministradorPersistencia {

	private static AdmPersistenciaFuncion instancia;

	public AdmPersistenciaFuncion() {

	}

	public static AdmPersistenciaFuncion getInstancia() {
		if (instancia == null)
			instancia = new AdmPersistenciaFuncion();
		return instancia;
	}

	@Override
	public void insert(Object o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Object o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Object d) {
		// TODO Auto-generated method stub

	}

	@Override
	public Vector<Object> select(Object o) {
		// TODO Auto-generated method stub
		return null;
	}	
	
	public void insert1(Funcion f, String cine) {
		try {
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("insert into "+PoolConnection.getNameDB()+".funcion "
					+ "values (?,?,?,?,?,?,?,?)");
			s.setString(1, f.getPelicula().getNombre());
			s.setString(2, f.getPelicula().getIdioma());
			s.setString(3, f.getOp().getUsuario().getDni());
			s.setString(4, f.getSala().getNombre());
			s.setString(5, cine);
			s.setTime(6, Time.valueOf(f.getHorario()));
			s.setDate(7, Date.valueOf(f.getDia()));
			s.setBoolean(8, f.isEstado());
			s.execute();
			PoolConnection.getPoolConnection().realeaseConnection(con);
		} catch (Exception e) {
			System.out.println("error en insert1 de funcion "+e.getMessage()+e.getStackTrace());
		}
	}
	
	public void update1(LocalDate diaNuevo,LocalTime horarioNuevo, String nombreSalaNuevo ,
			String nombreSala, LocalDate dia,LocalTime horario, String cuitCine) {
		try{
			
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("update "+PoolConnection.getNameDB()+".Funcion " +
					"nombreSala = ?, " +
					"horario = ?, " +
					"dia = ? " +
					" where nombreSala = ? and idCine = ? and horario = ? and dia = ?");
			s.setString(1, nombreSalaNuevo);//nombre sala no se modifica
			s.setTime(2, Time.valueOf(horarioNuevo));
			s.setDate(3,Date.valueOf(diaNuevo));
			s.setString(4,nombreSala);
			s.setInt(5, AdmPersistenciaCine.getInstancia().getId(cuitCine));
			s.setTime(6, Time.valueOf(horario));
			s.setDate(7, Date.valueOf(dia));
			s.execute();
			PoolConnection.getPoolConnection().realeaseConnection(con);
		}
		catch (Exception e) {
			System.out.println("error en update1 de funcion "+e.getMessage()+e.getStackTrace());
		}

	}


	public void delete1(Object d, String cuitCine) {
		try {
			Funcion f = (Funcion) d;
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("update "+PoolConnection.getNameDB()+".Funcion set estado = ? "
					+ " where nombreSala = ? and idCine = ? and horario = ? and dia = ?");
			s.setBoolean(1, false);
			s.setString(2, f.getSala().getNombre());
			s.setInt(3, AdmPersistenciaCine.getInstancia().getId(cuitCine));
			s.setString(4, f.getHorario().toString());
			s.setDate(5, Date.valueOf(f.getDia()));
			s.execute();
			PoolConnection.getPoolConnection().realeaseConnection(con);
		} catch (Exception e) {
			System.out.println("Error delete funcion()"+e.getMessage()+e.getStackTrace());
		}

	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	public Funcion buscarFuncion(String nombreSala, LocalTime horario, LocalDate dia, String nombreCine) {
		try {
			Funcion f = null;
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("select * from "+PoolConnection.getNameDB()+".funcion where "
					+ "nombreSala = ? and nombreCineSala = ? and horario = ? and dia = ?");
			s.setString(1, nombreSala);
			s.setString(2, nombreCine);
			s.setString(3, horario.toString());
			s.setString(4, dia.toString());
			ResultSet result = s.executeQuery();
			while (result.next()) {
				String nombrePelicula = result.getString("nombrePelicula");
				String idiomaPelicula = result.getString("idiomaPelicula");
				String dniOp = result.getString("idOperador");
				boolean estado = result.getBoolean("estado");

				Pelicula p = AdmPersistenciaPelicula.getInstancia().buscarPelicula(nombrePelicula,	idiomaPelicula);

				Operador op =(Operador)AdmPersistenciaUsuario.getInstancia().buscarUsuarioPorDni(dniOp).getOperador();

				Sala sala = AdmPersistenciaSala.getInstancia().buscarSala(nombreSala, nombreCine);

				f = new Funcion(sala, p, horario, dia, op, estado);
				
				/*
				 * Recuperacion de las entradas vendidas
				 * 
				 */	
				f.setEntradas(AdmPersistenciaEntrada.getInstancia().getEntradas(f, result.getInt("idFuncion")));
			}
			PoolConnection.getPoolConnection().realeaseConnection(con);
			return f;

		} catch (Exception e) {
			System.out.println("Falla buscar funcion "+ e.getMessage()+e.getStackTrace());
		}
		return null;
	}
	
	/**
	 * Obtengo los dias de funciones que tiene asociada una pelicula en un establecimiento
	 * */
	public Vector<LocalDate> getDiasDePelicula(Cine_View c,Pelicula_View p){
		Vector<LocalDate> horarios=new Vector<LocalDate>();
		Connection con=PoolConnection.getPoolConnection().getConnection();
		
		try{
			String sql="Select dia from "+PoolConnection.getNameDB()+".Funcion where nombrePelicula= ? and idiomaPelicula= ? and nombreCineSala=?";
			PreparedStatement s=con.prepareStatement(sql);
			s.setString(1, p.getNombre());
			s.setString(2, p.getIdioma());
			s.setString(3, c.getNombre());
			ResultSet rs=s.executeQuery();
			while(rs.next()){
				LocalDate aux=rs.getDate(1).toLocalDate();
				if(!horarios.contains(aux))
					horarios.add(aux);
			}
			
		}catch(Exception e){
			System.out.println("Error getDiasPelicula"+e.getMessage());
		}
		finally{
			PoolConnection.getPoolConnection().realeaseConnection(con);
		}
		return horarios;
	}
	/**
	 * Obtengo los horarios de funciones que tiene asociada una pelicula en un establecimiento y un dia
	 * */
	public Vector<LocalTime> getHorariosDePelicula(Cine_View cine,Pelicula_View pelicula, LocalDate dia){
		Vector<LocalTime> horarios=new Vector<LocalTime>();
		Connection con=PoolConnection.getPoolConnection().getConnection();
		
		try{
			String sql="Select horario as horario from "+PoolConnection.getNameDB()+".Funcion where nombrePelicula=? and idiomaPelicula=? and nombreCineSala=? and dia=?";
			PreparedStatement s=con.prepareStatement(sql);
					s.setString(1,pelicula.getNombre());
					s.setString(2,pelicula.getIdioma());
					s.setString(3,cine.getNombre());
					s.setString(4,dia.toString());
			ResultSet rs=s.executeQuery();
			while(rs.next()){
				horarios.add(rs.getTime("horario").toLocalTime());
			}
			
		}catch(Exception e){
			System.out.println("Error getDiasPelicula"+e.getMessage());
		}
		finally{
			PoolConnection.getPoolConnection().realeaseConnection(con);
		}
		return horarios;
	}
/*
 * Sala sala, Pelicula pelicula, LocalTime horario, LocalDate dia,
			Operador op, Boolean estado*/
	public Funcion buscarFuncionPorPelicula(String pelicula, LocalTime horario, LocalDate dia, String c) {
		Funcion f=null;
		Connection con=PoolConnection.getPoolConnection().getConnection();
		
		try{
			Statement s=con.createStatement();
			ResultSet rs=s.executeQuery("Select * from "+PoolConnection.getNameDB()+".Funcion where"
					+ " nombrePelicula="+pelicula
					+ " dia="+dia
					+ " horario="+horario
					+ " nombreCineSala= "+c
					);
			while(rs.next()){
				Usuario u=AdmPersistenciaUsuario.getInstancia().buscarUsuarioPorDni(rs.getString("idOperador"));
				Operador op =(Operador)u.getOperador();
						
				f=new Funcion(
						AdmPersistenciaSala.getInstancia().buscarSala(rs.getString("nombreSala"), ""),
						AdmPersistenciaPelicula.getInstancia().buscarPelicula(rs.getString("nombrePelicula"), rs.getString("idiomaPelicula")),
						rs.getTime("horario").toLocalTime(),
						rs.getDate("dia").toLocalDate(),
						op,
						rs.getBoolean("estado")
					);
			}
			
		}catch(Exception e){
			System.out.println("Error buscarFuncionPorPelicula"+e.getMessage());
		}
		finally{
			PoolConnection.getPoolConnection().realeaseConnection(con);
		}
		return f;
	}

	public Vector<Funcion_View> getFunciones(String cine, Pelicula_View pelicula) {
		// TODO Auto-generated method stub
		Connection c=PoolConnection.getPoolConnection().getConnection();
		Vector<Funcion_View>funciones=new Vector<Funcion_View>();
		try{
			String sql="SELECT * FROM "+PoolConnection.getNameDB()+".Funcion where nombreCineSala=? and nombrePelicula=? and idiomaPelicula=?";
			PreparedStatement p=c.prepareStatement(sql);
			p.setString(1, cine);
			p.setString(2, pelicula.getNombre());
			p.setString(3, pelicula.getIdioma());
			
			ResultSet rs=p.executeQuery();
			while(rs.next()){
				String sala=rs.getString("nombreSala");
				int cant=AdmPersistenciaAsientos.getInstancia().getCantAsientos(sala, cine);
				Sala_View s=new Sala_View(sala,cant);
				LocalTime horario=rs.getTime("horario").toLocalTime();
				Funcion_View f=new Funcion_View(pelicula,s,rs.getString("dia"),horario.getHour()+":"+horario.getMinute());
				funciones.add(f);
			}
		}catch(Exception e){
			
		}finally{
			PoolConnection.getPoolConnection().realeaseConnection(c);
		}
		return funciones;
	}

	public Funcion buscarFuncion(int idFuncion) {
		// TODO Auto-generated method stub
		Connection c=PoolConnection.getPoolConnection().getConnection();
		Funcion f=null;
		String nombreSala = null,nombreCine = null;
		LocalTime horario = null;
		LocalDate dia = null;
		try{
			Statement s=c.createStatement();
			ResultSet rs=s.executeQuery("SELECT * FROM"+PoolConnection.getNameDB()+".Funcion where idFuncion="+idFuncion);
			while(rs.next()){
				nombreSala=rs.getString("nombreSala");
				nombreCine=rs.getString("nombreCineSala");
				horario=rs.getTime("horario").toLocalTime();
				dia=rs.getDate("dia").toLocalDate();
			}
			PoolConnection.getPoolConnection().realeaseConnection(c);
			
			f=this.buscarFuncion(nombreSala, horario, dia, nombreCine);
			
		}catch(Exception e){
			System.out.println("Error en buscarFuncion(idfuncion). Detalle:\n"+e.getMessage()+"\nStackTrace:\n"+e.getStackTrace());
		}
		
		return f;
	}

	public int getIdFuncion(String peli,String idioma, String horario, String dia) {
		// TODO Auto-generated method stub
		int id=-1;
		Connection c=PoolConnection.getPoolConnection().getConnection();
		try{
			String sql="Select * from "+PoolConnection.getNameDB()+".Funcion where nombrePelicula=? and idiomaPelicula=? and horario=? and dia=?";
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setString(1, peli);
			ps.setString(2,idioma);
			ps.setString(3,horario);
			ps.setString(4,dia);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
				id=rs.getInt("idFuncion");
		}catch(Exception e){
			System.out.println("Error en getIdFuncion(). Detalle:\n"+e.getMessage()+"\nStackTrace:\n"+e.getStackTrace());
			
		}finally{
			PoolConnection.getPoolConnection().realeaseConnection(c);
		}
		return id;
	}
	
	/*public Funcion_View buscarFuncionPorPelicula2(String pelicula, LocalTime horario, LocalDate dia, String c) {
		Funcion_View f=null;
		Connection con=PoolConnection.getPoolConnection().getConnection();
		
		try{
			Statement s=con.createStatement();
			ResultSet rs=s.executeQuery("Select * from "+PoolConnection.getNameDB()+".Funcion where"
					+ " nombrePelicula="+pelicula
					+ " dia="+dia
					+ " horario="+horario
					+ " nombreCineSala= "+c
					);
			while(rs.next()){
				Pelicula_View p=AdmPersistenciaPelicula.getInstancia().buscarPelicula(pelicula, rs.getString("idioma")).getView();
				Sala_View sv=AdmPersistenciaSala.getInstancia().getSalaView(rs.getString("nombreSala"));		
				f=new Funcion_View(p,sv,dia.toString(),horario.toString());
				f.setCod(rs.getInt("idFuncion"));
			}
			
		}catch(Exception e){
			System.out.println("Error buscarFuncionPorPelicula2"+e.getMessage());
		}
		finally{
			PoolConnection.getPoolConnection().realeaseConnection(con);
		}
		return f;
	}*/
	
	public Vector<Funcion> buscarFuncionesDeSala(Sala s, String nombreCine) {
		Vector<Funcion> funciones = new Vector<Funcion>();
		try{
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement ps = con.prepareStatement("select * from "+PoolConnection.getNameDB()+".Funcion where nombreCineSala = ? and nombreSala = ? and estado = ? ");
			ps.setString(1, nombreCine);
			ps.setString(2, s.getNombre());
			ps.setBoolean(3, true);
			ResultSet res = ps.executeQuery();
			while(res.next()){
				Usuario u=AdmPersistenciaUsuario.getInstancia().buscarUsuarioPorDni(res.getString("idOperador"));
				Operador op =(Operador)u.getOperador();
						
				Funcion f=new Funcion(s, 
						AdmPersistenciaPelicula.getInstancia().buscarPelicula(res.getString("nombrePelicula"), res.getString("idiomaPelicula")), 
						res.getTime("horario").toLocalTime(),
						res.getDate("dia").toLocalDate(),
						op,
						res.getBoolean("estado")
					);	
				funciones.add(f);
			}
			
			PoolConnection.getPoolConnection().realeaseConnection(con);
		}
		catch(Exception e){
			System.out.println("Error traer Funciones de una Sala desde la BD  |"+e.getMessage()+" | "+e.getStackTrace() );
		}
		if(funciones.size()==0)
			return null;
		return funciones;
	}

	public Vector<Funcion> buscarFuncionesDeUnCine(Cine c) {
		Vector<Funcion> funciones = new Vector<Funcion>();
		try{
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement ps = con.prepareStatement("select * from "+PoolConnection.getNameDB()+".Funcion where nombreCineSala = ? and estado = ? ");
			ps.setString(1, c.getNombre());
			ps.setBoolean(2, true);
			ResultSet res = ps.executeQuery();
			while(res.next()){
				Usuario u=AdmPersistenciaUsuario.getInstancia().buscarUsuarioPorDni(res.getString("idOperador"));
				Operador op =(Operador)u.getOperador();
						
				Funcion f=new Funcion(AdmPersistenciaSala.getInstancia().buscarSala(res.getString("nombreSala"), res.getString("nombreSalaCine")), 
						AdmPersistenciaPelicula.getInstancia().buscarPelicula(res.getString("nombrePelicula"), res.getString("idiomaPelicula")), 
						res.getTime("horario").toLocalTime(),
						res.getDate("dia").toLocalDate(),
						op,
						res.getBoolean("estado")
					);	
				funciones.add(f);
			}
			
			PoolConnection.getPoolConnection().realeaseConnection(con);
		}
		catch(Exception e){
			System.out.println("Error traer Funciones de una Sala desde la BD  |"+e.getMessage()+" | "+e.getStackTrace() );
		}
		if(funciones.size()==0)
			return null;
		return funciones;
	}
	
}

