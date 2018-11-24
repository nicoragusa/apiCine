package negocio;

public abstract class Rol {
	protected Usuario usuario;
	protected String descripcion;
	
	public Rol(Usuario usuario,String descripcion) {
		super();
		this.usuario = usuario;
		this.descripcion = descripcion;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	public abstract boolean sosElCliente();
	public abstract boolean sosElAdministrador();
	public abstract boolean sosElVendedor();
	public abstract boolean sosElAgenteComercial();
	public abstract boolean sosElOperador();
}
