package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Vector;

import conexionBD.PoolConnection;
import negocio.*;

public class AdmPersistenciaVenta extends AdministradorPersistencia {
	private static AdmPersistenciaVenta instancia=null;
	private Connection c;
	
	public static AdmPersistenciaVenta getInstancia(){
		if(instancia==null)
			instancia=new AdmPersistenciaVenta();
		return instancia;
	}
	@Override
	public void insert(Object o) {
		Venta v=(Venta)o;
		// TODO Auto-generated method stub
		 c=PoolConnection.getPoolConnection().getConnection();
		try{
			String sql="INSERT INTO "+PoolConnection.getNameDB()+".Venta values (?,?)";
				PreparedStatement ps=c.prepareStatement(sql);
				
				//ps.setInt(1, 2);
				ps.setString(2,v.getFecha().toString());
				ps.setFloat(1,v.getMonto());
				ps.execute();
				PoolConnection.getPoolConnection().realeaseConnection(c);
			//////////////////////////INSERSION DE ENTRADAS///////////////////////////
				int idVenta=v.getCodigo();
				Vector<Entrada>entradas=v.getEntradas();
				for(Entrada entrada:entradas){
					int idEntrada=AdmPersistenciaEntrada.getInstancia().getIdEntrada(entrada);
					insertarEntradasVenta(idVenta,idEntrada);
				}
						
		}catch(Exception e){
			System.out.println("Error insertVenta "+ e.getMessage());
		}
		finally{
			PoolConnection.getPoolConnection().realeaseConnection(c);
		}
	}
	
	public void insertarEntradasVenta(int idVenta,int idEntrada){
		c=PoolConnection.getPoolConnection().getConnection();
		try{
			String sql="insert into "+PoolConnection.getNameDB()+".EntradaVenta values (?,?)";
			PreparedStatement s=c.prepareStatement(sql);
			s.setInt(1, idVenta);
			s.setInt(2, idEntrada);
			s.execute();
		}catch(Exception e){
			System.out.println("Error insertEntradasVenta "+ e.getMessage() +e.getStackTrace());
		}finally{
			PoolConnection.getPoolConnection().realeaseConnection(c);
		}
	}
	
	
	@Override
	public void update(Object o) {
		// TODO Auto-generated method stub
		Venta v=(Venta)o;
		c=PoolConnection.getPoolConnection().getConnection();
		try{
			String sql="UPDATE INTO "+PoolConnection.getNameDB()+".Online set estadoRetiro=0 where idVenta=?";
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setInt(1, v.getCodigo());
		}catch(Exception e){
			System.out.println("Error updateVenta "+ e.getMessage() +e.getStackTrace());
		}
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
	
	public Venta buscarVenta(int codigo){
		Venta v=null;
		String bd=PoolConnection.getNameDB();
		float monto = 0;
		LocalDate fecha=null;
		try{
			c=PoolConnection.getPoolConnection().getConnection();
			Statement s=c.createStatement();
			
			ResultSet rs=s.executeQuery("Select *  from "+bd+".Venta where codigoVenta="+codigo);
			if(rs.next()){
				 monto=rs.getFloat("monto");
				 fecha=rs.getDate("fecha").toLocalDate();
			}
			
			rs=s.executeQuery("Select *  from "+bd+".Online where codigoVenta="+codigo+"and estadoRetiro=1");
			if(rs.next()){
				/*Es una venta Online*/
				Cliente c=(Cliente) AdmPersistenciaUsuario.getInstancia().buscarUsuarioPorDni(rs.getString("idCliente")).getCliente();
				TarjetaCredito t=AdmPersistenciaTarjeta.getInstancia().buscarTarjeta(rs.getString("idTarjeta"));				
				Vector<Entrada>entradas=AdmPersistenciaEntrada.getInstancia().getEntradas(codigo);
				
				
				v=new Online(monto,entradas,fecha,c,t,true);
				
			}
			else{
				rs=s.executeQuery("Select *  from "+bd+".Boleteria where codigoVenta="+codigo);
				if(rs.next()){
					/*Es una venta en Boleteria en efectivo*/
					Vendedor vendedor=(Vendedor)AdmPersistenciaUsuario.getInstancia().buscarUsuarioPorDni(rs.getString("idVendedor")).getVendedor();
					Vector<Entrada>entradas=AdmPersistenciaEntrada.getInstancia().getEntradas(rs.getInt("codigoVenta"));
					v=new BoleteriaEfectivo(monto,entradas,fecha,vendedor);
				}
				else{
					rs=s.executeQuery("Select *  from "+bd+". BoleteriaTarjetaCredito where codigoVenta="+codigo);
					if(rs.next()){
						/*Es una venta en Boleteria en tarjeta*/
						TarjetaCredito t=AdmPersistenciaTarjeta.getInstancia().buscarTarjeta(rs.getString("idTarjeta"));
						Vendedor vendedor=(Vendedor)AdmPersistenciaUsuario.getInstancia().buscarUsuarioPorDni(rs.getString("idVendedor")).getVendedor();						
						Vector<Entrada>entradas=AdmPersistenciaEntrada.getInstancia().getEntradas(rs.getInt("codigoVenta"));
						v=new BoleteriaTarjetaCredito(monto,entradas,fecha,vendedor,t);
					}
				}
				
			}
			v.setCodigo(codigo);
			
		}catch(Exception e){
			System.out.println("Falla en buscarVenta()"+e.getMessage());
		}
		finally{
			PoolConnection.getPoolConnection().realeaseConnection(c);
		}
		return v;
	}
	
	public void insertarOnline(Online online) {
		// TODO Auto-generated method stub
		c=PoolConnection.getPoolConnection().getConnection();
		try{
			String sql="INSERT INTO "+PoolConnection.getNameDB()+".Online values (?,?,?)";
			PreparedStatement ps=c.prepareStatement(sql);
			
			ps.setInt(1, online.getCodigo());
			ps.setString(2,online.getComprador().getUsuario().getDni());
			ps.setString(3,online.getTarjeta().getNumero());			
			ps.execute();
			
		}catch(Exception e){
			System.out.println("Falla en insertarOnline()\n"+e.getMessage()+"\n"+e.getStackTrace());
		}
		finally{
			PoolConnection.getPoolConnection().realeaseConnection(c);
		}
	}
	
	public int getCodigoMaximo() {
		// TODO Auto-generated method stub
		Integer nro=null;
		c=PoolConnection.getPoolConnection().getConnection();
		try{
			Statement s=c.createStatement();
			String sql="Select max(codigoVenta) as codigo from "+PoolConnection.getNameDB()+".Venta";
			ResultSet rs=s.executeQuery(sql);
			while(rs.next()){
				nro=rs.getInt("codigo");
			}
		}catch(Exception e){
			System.out.println("Falla en getCodigoMaximo()\n"+e.getMessage()+"\n"+e.getStackTrace());
		}finally{
			PoolConnection.getPoolConnection().realeaseConnection(c);
		}
		return nro==null?0:nro;
	}
	
	public Vector<Online> selectAllOnline() {
		// TODO Auto-generated method stub
		Vector<Online>on=new Vector<Online>();
		c=PoolConnection.getPoolConnection().getConnection();
		try{
			Statement s=c.createStatement();
			String sql="Select * from "+PoolConnection.getNameDB()+".Online where estadoRetiro=1";
			ResultSet rs=s.executeQuery(sql);
			while(rs.next()){
				String idTarjeta=rs.getString("idTarjeta");
				String idCliente=rs.getString("idCliente");
				int idVenta=rs.getInt("codigoVenta");
				
				TarjetaCredito t=AdmPersistenciaTarjeta.getInstancia().buscarTarjeta(idTarjeta);
				Usuario u=AdmPersistenciaUsuario.getInstancia().buscarUsuarioPorDni(idCliente);
				Vector<Entrada> entradas=AdmPersistenciaEntrada.getInstancia().getEntradas(idVenta);
				float monto=this.getMonto(idVenta);
				Online o=new Online(monto, entradas,LocalDate.now(), (Cliente)u.getCliente(),t,true);
				
				on.add(o);
			}
		}catch(Exception e){
			System.out.println("Falla en selectAllOnline()\n"+e.getMessage()+"\n"+e.getStackTrace());
		}finally{
			PoolConnection.getPoolConnection().realeaseConnection(c);
		}
		return on;
	}
	private float getMonto(int idVenta) {
		// TODO Auto-generated method stub
		float monto=-1;
		c=PoolConnection.getPoolConnection().getConnection();
		try{
			Statement s=c.createStatement();
			String sql="Select monto from "+PoolConnection.getNameDB()+".Venta where codigoVenta="+idVenta;
			ResultSet rs=s.executeQuery(sql);
			while(rs.next()){
				monto=rs.getFloat(1);
			}
			PoolConnection.getPoolConnection().realeaseConnection(c);
		}
		catch(Exception e){
			System.out.println("Falla en getMonto(idVenta)\n"+e.getMessage()+"\n"+e.getStackTrace());
		}
		return 0;
	}
	
	
}
