package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import conexionBD.PoolConnection;
import negocio.AsientoFisico;
import negocio.AsientoVendido;
import negocio.Sala;
import view.Venta_View;

public class AdmPersistenciaAsientos extends AdministradorPersistencia {
	private static AdmPersistenciaAsientos instancia=null;
	
	public static AdmPersistenciaAsientos getInstancia(){
		if(instancia==null)
			instancia=new AdmPersistenciaAsientos();
		return instancia;
	}
	
	private AdmPersistenciaAsientos(){
		
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

	public int getCantAsientos(String nombreSala, String cine) {
		// TODO Auto-generated method stub
		int res=-1;
		Connection c=PoolConnection.getPoolConnection().getConnection();
		try{
			PreparedStatement ps=c.prepareStatement("Select count(*) from "+PoolConnection.getNameDB()+
					".AsientoFisico where nombreSala=? and nombreCineSala=?");
			ps.setString(1, nombreSala);
			ps.setString(2, cine);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
				res=rs.getInt(1);
		}catch(Exception e){
			System.out.println("Error en getCantAsientos: "+e.getStackTrace());
		}
		finally{
			PoolConnection.getPoolConnection().realeaseConnection(c);
		}
		return res;
	}

	public AsientoVendido getAsientoVendido(int idAsientoV) {
		AsientoVendido res=null;
		Connection c=PoolConnection.getPoolConnection().getConnection();
		try{
			PreparedStatement ps=c.prepareStatement("Select * from "+PoolConnection.getNameDB()+".AsientoVendido where idASientoV=?");
			ps.setInt(1, idAsientoV);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				res=new AsientoVendido(new AsientoFisico(rs.getInt("filaFisico"),rs.getInt("columnaFisico")),rs.getBoolean("estado"));
			}
		}catch(Exception e){
			System.out.println("Error en getAsientoVendido: "+e.getStackTrace());
		}
		finally{
			PoolConnection.getPoolConnection().realeaseConnection(c);
		}
		return res;
	}
	
	public void insert1(String nombreCine, String nombreSala, Vector<AsientoFisico> asFis) {
		try{
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement ps = con.prepareStatement("insert into "+PoolConnection.getNameDB()+".AsientoFisico values (?,?,?,?,?)");
			ps.setString(1, nombreSala);
			ps.setString(2, nombreCine);
			ps.setBoolean(5, true);
			for(AsientoFisico af : asFis){
				ps.setInt(3, af.getFila());
				ps.setInt(4, af.getColumna());
				ps.execute();
			}
			
			PoolConnection.getPoolConnection().realeaseConnection(con);
		}
		catch(Exception e){
			System.out.println("Error insertar Asientos Fisicos" + e.getMessage() + e.getStackTrace());
		}
		
	}

	public Vector<AsientoFisico> asientosDeLaSala(Sala s, String nombreCine) {
		Vector<AsientoFisico> afi = new Vector<AsientoFisico>();
		try{
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement ps = con.prepareStatement("select * from "+PoolConnection.getNameDB()+".AsientoFisico where nombreCineSala = ? and nombreSala = ? ");
			ps.setString(1, nombreCine);
			ps.setString(2, s.getNombre());
			ResultSet res = ps.executeQuery();
			while(res.next()){
				AsientoFisico af = new AsientoFisico(res.getInt(3), res.getInt(4), res.getBoolean(5));
				afi.add(af);	
			}
			
			PoolConnection.getPoolConnection().realeaseConnection(con);
		}
		catch(Exception e){
			System.out.println("Error traerme de la BD los Asientos Fisicos de una Sala " + e.getMessage() + e.getStackTrace());
		}
		if(afi.size()==0)
			return null;
		return afi;
	}

	public void insertAsientoVendido(AsientoVendido av, Venta_View vta) {
		// TODO Auto-generated method stub
		Connection c=PoolConnection.getPoolConnection().getConnection();
		try{
			
			String bd=PoolConnection.getNameDB();
			String sql="INSERT INTO "+bd+".AsientoVendido values (?,?,?,?,?,?,?,?)";
			PreparedStatement ps=c.prepareStatement(sql);
			////////////////////////////////////////
			String nPelicula=vta.getFuncion().getP().getNombre();
			String iPelicula=vta.getFuncion().getP().getIdioma();
			String hFuncion=vta.getFuncion().getHorario();
			String dFuncion=vta.getFuncion().getDia();
			String sala= vta.getFuncion().getS().getNombre();
			int idFuncion=AdmPersistenciaFuncion.getInstancia().getIdFuncion(nPelicula,iPelicula,hFuncion,dFuncion);
			////////////////////////////////////////			
			ps.setInt(1, av.getAsientoF().getFila());
			ps.setInt(2, av.getAsientoF().getColumna());
			ps.setString(3,sala);
			ps.setString(4, vta.getEstablecimiento());
			ps.setInt(5, idFuncion);
			ps.setString(6, hFuncion);
			ps.setString(7, dFuncion);
			ps.setBoolean(8, true);
			ps.execute();
			//me salteo uno por el identity
		}catch(Exception e){
			System.out.println("Error traerme de la BD los Asientos Fisicos de una Sala " + e.getMessage() + e.getStackTrace());			
		}finally{
			PoolConnection.getPoolConnection().realeaseConnection(c);
		}
		
	}

	public int getIdAsientoVendido(int fila, int columna, int idFuncion) {
		// TODO Auto-generated method stub
		Connection c=PoolConnection.getPoolConnection().getConnection();
		int idAsiento=-1;
		try{
			
			String bd=PoolConnection.getNameDB();
			String sql="SELECT * FROM "+bd+".AsientoVendido where idFuncion=? and filaFisico=? and columnaFisico=?";
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setInt(1,idFuncion);
			ps.setInt(2, fila);
			ps.setInt(3, columna);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				idAsiento=rs.getInt("idAsientoV");
			}
		}catch(Exception e){
			System.out.println("Error traerme de la BD los Asientos Fisicos de una Sala " + e.getMessage() + e.getStackTrace());	
		}finally{
			PoolConnection.getPoolConnection().realeaseConnection(c);
		}
		return idAsiento;
	}

}
