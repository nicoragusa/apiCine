package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import conexionBD.PoolConnection;
import negocio.TarjetaCredito;

public class AdmPersistenciaTarjeta extends AdministradorPersistencia {
	private Connection c;
	private static AdmPersistenciaTarjeta instancia=null;
	
	@Override
	public void insert(Object o) {
		// TODO Auto-generated method stub
		TarjetaCredito t=(TarjetaCredito)o;
		if(buscarTarjeta(t.getNumero())==null){
			String bd=PoolConnection.getNameDB();
			String sql="insert into "+bd+".Tarjeta values(?,?,?,?,?,?)";
			c=PoolConnection.getPoolConnection().getConnection();
			try{
				PreparedStatement ps=c.prepareStatement(sql);
				ps.setString(1,t.getTipoTarjeta());
				ps.setString(2, t.getVencimiento());
				ps.setString(3, t.getNumero());
				ps.setString(4, t.getCodigoSeguridad());
				ps.setString(5,t.getTitular());
				ps.setString(6, t.getEntidadBancaria());
				ps.execute();
			}catch(Exception e){
				System.out.println("Error en insertTarjeta. Detalle:\n"+e.getMessage()+"\nStackTrace:\n"+e.getStackTrace());
				
			}finally{
				PoolConnection.getPoolConnection().realeaseConnection(c);
			}
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

	@Override
	public Vector<Object> select(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	public static AdmPersistenciaTarjeta getInstancia() {
		// TODO Auto-generated method stub
		return instancia==null? instancia=new AdmPersistenciaTarjeta(): instancia;
	}

	public TarjetaCredito buscarTarjeta(String nro) {
		// TODO Auto-generated method stub
		c=PoolConnection.getPoolConnection().getConnection();
		TarjetaCredito t=null;
		try{
			PreparedStatement ps=c.prepareStatement("SELECT * FROM "+PoolConnection.getNameDB()+".Tarjeta where numero=?");
			ps.setString(1, nro);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				String cod=rs.getString("codigo");
				String titular=rs.getString("titular");
				String entidad=rs.getString("entidadBancaria");
				String tipo=rs.getString("tipo");
				String vencimiento=rs.getString("vencimiento");
				
				t=new TarjetaCredito();
				t.setCodigoSeguridad(cod);
				t.setNumero(nro);
				t.setTitular(titular);
				t.setVencimiento(vencimiento);
				t.setEntidadBancaria(entidad);
				t.setTipoTarjeta(tipo);
			}
		}catch(Exception e){
			System.out.println("Error en buscarTarjeta\n Detalle del error:\n"+e.getMessage()+"\nStackTrace:\n"+e.getStackTrace());
		}
		return t;
	}

}
