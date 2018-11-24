package negocio;

import java.time.LocalDate;
import java.util.Vector;

import persistencia.AdmPersistenciaVenta;

public class BoleteriaEfectivo extends Boleteria {

	public BoleteriaEfectivo(float monto, Vector<Entrada> entradas, Vendedor vendedor) {
		super(monto, entradas, vendedor);
		AdmPersistenciaVenta.getInstancia().insert(this);
	}
	
	public BoleteriaEfectivo(float monto, Vector<Entrada> entradas,LocalDate d, Vendedor vendedor) {
		super(monto, entradas,d, vendedor);
		AdmPersistenciaVenta.getInstancia().insert(this);
	}

}
