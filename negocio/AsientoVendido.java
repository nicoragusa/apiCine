package negocio;

import persistencia.AdmPersistenciaAsientos;
import view.Venta_View;

public class AsientoVendido {
	
	private boolean estado;
	private AsientoFisico asientoF;
	
	public AsientoVendido(AsientoFisico f,boolean estado) {
		super();
		asientoF=f;
		this.estado = estado;
	}
	
	public AsientoVendido(AsientoFisico f){
		super();
		asientoF=f;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public AsientoFisico getAsientoF() {
		return asientoF;
	}

	public void setAsientoF(AsientoFisico asientoF) {
		this.asientoF = asientoF;
	}
	
	public static void insertarAsientoVendido(AsientoVendido av, Venta_View vta){
		AdmPersistenciaAsientos.getInstancia().insertAsientoVendido(av,vta);
	}
	
}
