package negocio;

import negocio.AgenteComercial;
import negocio.Online;
import persistencia.AdmPersistenciaPromocion;

/**
 * STRATEGY-1
 * @author alu802
 *
 */

public class DosPorUno extends Promocion {

	public DosPorUno(String detalle, boolean estado, AgenteComercial agente, float porcentaje) {
		super(detalle, estado, agente, porcentaje);
		
		AdmPersistenciaPromocion.getInstancia().insert(this);
		
	}

	/**
	 * Algoritmo para aplicar un 2x1 a una entrada
	 * @param precioEntrada
	 * @return float
	 */
	
	@Override
	public float aplicarPromocion(Online v) {
		return 0;
	}
	
}
