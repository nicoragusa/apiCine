package view;
import negocio.AsientoFisico;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Vector;

import controlador.Controlador_Cine;

public class Venta_View {
	private String establecimiento;
	private Funcion_View funcion;
	private FormaDePago_View formaPago;
	private int cantidadEntradas;
	//private int [][] asientosSeleccionados;
	private Vector<AsientoFisico> asientosSeleccionados;
	private Promociones_View promos;
	private float total;
	
	public Venta_View(String e, Funcion_View f){
		establecimiento=e;
		funcion=f;
		asientosSeleccionados=new Vector<AsientoFisico>();
	}

	public Venta_View() {
		// TODO Auto-generated constructor stub
		asientosSeleccionados=new Vector<AsientoFisico>();
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public Funcion_View getFuncion() {
		return funcion;
	}

	public void setFuncion(Funcion_View funcion) {
		this.funcion = funcion;
	}

	public int getCantidadEntradas() {
		return cantidadEntradas;
	}

	public void setCantidadEntradas(int cantidadEntradas) {
		this.cantidadEntradas = cantidadEntradas;
	}

	/*public int[][] getAsientosSeleccionados() {
		return asientosSeleccionados;
	}*/

	public void setAsientosSeleccionados(Vector<AsientoFisico> asientosSeleccionados) {
		this.asientosSeleccionados=asientosSeleccionados;
	}
	
	public Vector<AsientoFisico> getAsientosSeleccionados(){
		return asientosSeleccionados;
	}

	public FormaDePago_View getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(FormaDePago_View formaPago) {
		this.formaPago = formaPago;
	}

	public Promociones_View getPromos() {
		return promos;
	}

	public void setPromos(Promociones_View promos) {
		this.promos = promos;
	}

	public float getTotal() {
		// TODO Auto-generated method stub
		return total;
	}
	
	public void setTotal(float t){
		this.total=t;
	}
	
	
}
