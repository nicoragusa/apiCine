package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import Negocio.Arreglo;
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
		Vector<Promocion> arr=new Vector<Promocion>();
		ResultSet rs;
		Connection c=PoolConnection.getPoolConnection().getConnection();
		String sql="Select * from dbo.api.Promocion";
		try{
			Statement s=c.createStatement();
			rs=s.executeQuery(sql);
			PoolConnection.getPoolConnection().realeaseConnection(c);
			while(rs.next()){
				int idArr=rs.getInt(1);
				Promocion arreglo = new Promocion();
				arreglo.setCostoArreglo(rs.getFloat(4));
				arreglo.setDescripcion(rs.getString(3));
				arreglo.setIdArreglo(idArr);
				arreglo.setNombre(rs.getString(2));
				arreglo.setEstado(rs.getBoolean(5));
				arr.add(arreglo);
			}
		}catch(Exception e){
			e.printStackTrace();			
		}
		return arr;
	}
	@Override
	public Vector<Object> select(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

}
