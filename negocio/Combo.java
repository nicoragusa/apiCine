package negocio;

import java.util.Vector;

import negocio.AgenteComercial;
import negocio.Online;
import persistencia.AdmPersistenciaPromocion;

public class Combo extends Promocion {
	
	private Vector<Promocion> promociones;
	
	
	public Combo(String detalle, boolean estado, AgenteComercial agente, float porcentaje,
			Vector<Promocion> promociones) {
		super(detalle, estado, agente, porcentaje);
		
	}
	
	//Constructor para recuperar
	public Combo(String detalle, boolean estado, AgenteComercial agente, float porcentaje) {
		super(detalle, estado, agente, porcentaje);
		this.promociones = getPromociones();
	}
	
	public void agregarPromocion(Promocion p){
		promociones.add(p);
		AdmPersistenciaPromocion.getInstancia().insert(p);
	}
	
	public void quitarPromocion(Promocion p){
		promociones.remove(p);
		AdmPersistenciaPromocion.getInstancia().update(p);
	}
	
	public Vector<Promocion> getPromociones(){
		if(promociones==null)
			promociones=AdmPersistenciaPromocion.getInstancia().selectAll();
		return promociones;
	}

	@Override
	public float aplicarPromocion(Online v) {
		// TODO Auto-generated method stub
		return 0;
	}

}
