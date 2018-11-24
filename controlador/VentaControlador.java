package controlador;

import java.time.LocalDate;

import java.time.LocalTime;
import java.util.Vector;

import javax.swing.JOptionPane;

import persistencia.*;
import view.Funcion_View;
import view.Venta_View;
import negocio.*
;public class VentaControlador {
	private static VentaControlador instancia=null;
	private Vector<Venta> ventas;
	//private Vector<Promocion>promociones;
	
	private VentaControlador(){
		ventas=new Vector<Venta>();
		//promociones=new Vector<Promocion>();
		Online.add(TerminalEntradas.getInstancia());
		Online.add(ServidorMails.getInstancia());
		
	}
	
	public static VentaControlador getInstancia(){
		if(instancia==null)
			instancia=new VentaControlador();
		return instancia;
	}
	
	public Venta buscarVenta(int codigo){
		for(Venta v:ventas){
			if(v.getCodigo()==codigo)
				return v;
		}
		return AdmPersistenciaVenta.getInstancia().buscarVenta(codigo);
	}

	public Vector<Venta> getVentas(){
		return ventas;
	}

	public Vector<AsientoVendido> getAsientosVendidos(Venta_View vta) {
		Vector<AsientoVendido> vendidos=new Vector<AsientoVendido>();
		Funcion f=getFuncionPorView(vta);
		for(Entrada e:f.getEntradas())
			vendidos.add(e.getAsiento());
		
		return vendidos;
	}

	public void crearVenta(Venta_View v) {
		Venta venta=null;
		Rol logueado=LogInControlador.getInstancia().getRol();
		float precio=v.getTotal();
		Vector<AsientoFisico> pedidos=v.getAsientosSeleccionados();
		Vector<Entrada>entradas=new Vector<Entrada>();
		Funcion f=getFuncionPorView(v);
		
		for(AsientoFisico af:pedidos){
			AsientoVendido av=new AsientoVendido(af);
			AsientoVendido.insertarAsientoVendido(av, v);
			Entrada e=new Entrada(f,av,precio,true);
			entradas.add(e);
		}
		/*Si el rol iniciado es de un cliente, es una venta online*/
		if(logueado.sosElCliente()){
			TarjetaCredito t=v.getFormaPago().toTarjeta();
			t.insertarBD();
			venta=new Online(precio,entradas,t,(Cliente) logueado);
			
		}
		else{/*De lo contrario, es una venta por boleteria*/
			if(v.getFormaPago().sosTarjeta())
				venta=new BoleteriaTarjetaCredito(precio, entradas,(Vendedor)logueado,v.getFormaPago().toTarjeta());
			
			else
				venta=new BoleteriaEfectivo(precio, entradas, (Vendedor)logueado);
		}
		ventas.add(venta);
		f.agregarEntradas(entradas);
		
	}
	
	private Funcion getFuncionPorView(Venta_View vta){
		String sala,horario,dia,cine;
		sala=vta.getFuncion().getS().getNombre();
		horario=vta.getFuncion().getHorario();
		dia=vta.getFuncion().getDia();
		cine=vta.getEstablecimiento();
		LocalTime h=LocalTime.parse(horario);
		LocalDate d=LocalDate.parse(dia);
		return AdmPersistenciaFuncion.getInstancia().buscarFuncion(sala, h , d, cine);
		
	}
}
