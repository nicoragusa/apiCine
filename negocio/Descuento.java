package negocio;

import negocio.AgenteComercial;
import negocio.Online;
import persistencia.AdmPersistenciaPromocion;

/**
 * STRATEGY-2
 * @author alu802
 *
 */
public class Descuento extends Promocion {

	public Descuento(String detalle, boolean estado, AgenteComercial agente, float porcentaje) {
		super(detalle, estado, agente, porcentaje);
		
		AdmPersistenciaPromocion.getInstancia().insert(this);
	
	}

	/**
	 * Algoritmo para aplicar el descuento a una entrada
	 * @param precioEntrada
	 * @return float
	 */
	
	@Override
	public float aplicarPromocion(Online v) {
		return 0;
	}

}
