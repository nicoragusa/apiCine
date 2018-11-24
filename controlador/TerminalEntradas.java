package controlador;

import java.util.Vector;

import negocio.ObserverTDA;
import negocio.Online;
import persistencia.AdmPersistenciaVenta;

public class TerminalEntradas implements ObserverTDA {
	private Vector<Online> ventas;
	private static TerminalEntradas instancia=null;
	
	private TerminalEntradas(){
		ventas=new Vector<Online>();
	}
	
	public static TerminalEntradas getInstancia(){
		if(instancia==null)
			instancia=new TerminalEntradas();
		return instancia;
	}
	
	@Override
	public void recibirOnline(Online o) {
		// TODO Auto-generated method stub
		ventas.add(o);
	}

	public Vector<Online> getVentas() {
		return ventas;
	}

	public void setVentas(Vector<Online> ventas) {
		this.ventas = ventas;
	}
	
	public Online buscarVenta(int codigoVenta){
		return (Online) AdmPersistenciaVenta.getInstancia().buscarVenta(codigoVenta);
	}
	

}
