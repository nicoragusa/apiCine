package negocio;

import java.time.LocalDate;
import java.util.Vector;

import persistencia.AdmPersistenciaVenta;

public class BoleteriaTarjetaCredito extends Boleteria {
private TarjetaCredito tarjeta;

	public BoleteriaTarjetaCredito(float monto, Vector<Entrada> entradas, Vendedor vendedor, TarjetaCredito t) {
	super(monto, entradas, vendedor);
	this.tarjeta=t;
	//AdmPersistenciaVenta.getInstancia().insert(this);
	}

	public BoleteriaTarjetaCredito(float monto, Vector<Entrada> entradas,LocalDate d, Vendedor vendedor, TarjetaCredito t) {
	super(monto, entradas, vendedor);
	this.tarjeta=t;
	//AdmPersistenciaVenta.getInstancia().insert(this);
	}

	public TarjetaCredito getTarjeta() {
		// TODO Auto-generated method stub
		return tarjeta;
	}

}
