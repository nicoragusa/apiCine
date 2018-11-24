package view;

public class Cine_View {
	private String nombre, cuit, domicilio;
	private int cantSalas, capTotal;
	private boolean estado;
	public Cine_View(String nombre, String cuit, String domicilio, int cantSalas, int capTotal, boolean estado) {
		super();
		this.nombre = nombre;
		this.cuit = cuit;
		this.domicilio = domicilio;
		this.cantSalas = cantSalas;
		this.capTotal = capTotal;
		this.estado = estado;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCuit() {
		return cuit;
	}
	public void setCuit(String cuit) {
		this.cuit = cuit;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public int getCantSalas() {
		return cantSalas;
	}
	public void setCantSalas(int cantSalas) {
		this.cantSalas = cantSalas;
	}
	public int getCapTotal() {
		return capTotal;
	}
	public void setCapTotal(int capTotal) {
		this.capTotal = capTotal;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
}
