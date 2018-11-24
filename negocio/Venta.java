package negocio;

import java.time.LocalDate;
import java.util.Vector;

import persistencia.AdmPersistenciaVenta;


public abstract class Venta { 
	
	/*Cuando se compra una entrada, es siempre del mismo tipo*/
	
	protected float monto;
	protected LocalDate fecha;
	protected Vector<Entrada> entradas;
	protected int codigo=AdmPersistenciaVenta.getInstancia().getCodigoMaximo();
	public Venta(float monto, Vector<Entrada> entradas) {
		super();
		this.monto = monto;
		fecha=LocalDate.now();
		this.entradas=entradas;
		codigo=getProximoNumero();
		AdmPersistenciaVenta.getInstancia().insert(this);
	}

	public Venta(Vector<Entrada> entradas,float monto,LocalDate fecha) {
		// TODO Auto-generated constructor stub
		this.monto = monto;
		this.entradas=entradas;
		this.fecha=fecha;
	}

	private int getProximoNumero() {
		// TODO Auto-generated method stub
		return ++codigo;
	}
	public void setCodigo(int codigo){
		this.codigo=codigo;
	}
	
	public float getMonto(){
		return monto;
	}
	
	public void setMonto(float monto){
		this.monto=monto;
	}
	public int getCodigo(){
		return codigo;
	}
	
	public LocalDate getFecha(){
		return fecha;
	}

	public Vector<Entrada> getEntradas() {
		// TODO Auto-generated method stub
		return entradas;
	}

	public void actualizarEstado() {
		// TODO Auto-generated method stub
		AdmPersistenciaVenta.getInstancia().update(this);
	}
}