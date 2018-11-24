package negocio;

import java.time.LocalDate;
import java.util.Vector;

import persistencia.AdmPersistenciaVenta;

public class Online extends Venta implements ObservableVenta {
	
	private Cliente comprador;
	private TarjetaCredito tarjeta;
	private static Vector<ObserverTDA> ov= new Vector<ObserverTDA>();
	private boolean estadoRetiro;
	
	public Online(float monto, Vector<Entrada> entradas,LocalDate fecha, Cliente comprador, TarjetaCredito tarjeta,boolean estado) {
		super(entradas,monto,fecha);
		
		estadoRetiro=estado;
		this.comprador = comprador;
		this.tarjeta = tarjeta;			
	}
	
	public Online(float monto, Vector<Entrada> entradas, TarjetaCredito tarjeta, Cliente comprador){
		super(monto, entradas);
		this.estadoRetiro=true;
		this.comprador = comprador;
		this.tarjeta = tarjeta;
		AdmPersistenciaVenta.getInstancia().insertarOnline(this);
		Online.notifyAll(this);
	}

	public Cliente getComprador() {
		return comprador;
	}



	public void setComprador(Cliente comprador) {
		this.comprador = comprador;
	}



	public TarjetaCredito getTarjeta() {
		return tarjeta;
	}



	public void setTarjeta(TarjetaCredito tarjeta) {
		this.tarjeta = tarjeta;
	}





	public static void add(ObserverTDA vr) {
		// TODO Auto-generated method stub
		ov.add(vr);
	}



	
	public static void remove(ObserverTDA vr) {
		// TODO Auto-generated method stub
		ov.remove(vr);
	}



	
	public static void notifyAll(Online online) {
		// TODO Auto-generated method stub
		for(ObserverTDA o:ov){
			o.recibirOnline(online);
		}
	}

}
