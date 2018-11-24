package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Vector;

import conexionBD.PoolConnection;
import negocio.*;

public class AdmPersistenciaPromocion extends AdministradorPersistencia {
	
	private static AdmPersistenciaPromocion instancia;
	
	private AdmPersistenciaPromocion()
	{
		
	}
	public static AdmPersistenciaPromocion getInstancia()
	{
		if (instancia == null)
			instancia = new AdmPersistenciaPromocion();
		return instancia;
	}

	@Override
	public void insert(Object o) {
		try
		{
			DosPorUno a = (DosPorUno)o;
			Connection con = PoolConnection.getPoolConnection().getConnection();
			PreparedStatement s = con.prepareStatement("insert into "+PoolConnection.getNameDB()+".Promociones values (?,?,?,?)");
			s.setString(1, a.getAgenteComercial().getUsuario().getDni());
			s.setBoolean(2, a.isEstado());
			s.setString(3, a.getDetalle());
			s.setFloat(4, a.getPorcentaje());
			s.execute();
			
			System.out.println("Promoción agregada");
			
			PoolConnection.getPoolConnection().realeaseConnection(con);
		}
		catch (Exception e)
		{
			System.out.println("Error: "+e.getMessage()+e.getStackTrace());
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
	
	/**Devuelve todas las promociones activas
	 * @param void
	 * @return Vector*/
	public Vector<Promocion> selectAll() {
		// TODO Auto-generated method stub
		
		//Select where estado=1
		
		return null;
	}
	@Override
	public Vector<Object> select(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

}
