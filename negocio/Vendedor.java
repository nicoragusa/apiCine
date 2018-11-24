package negocio;

public class Vendedor extends Rol {
	public Vendedor(Usuario usuario) {
		super(usuario, "Vendedor");
		// TODO Auto-generated constructor stub
		//usuario.asignarRol(this);
	}
	
	public boolean sosElCliente(){
		return false;
	}
	public boolean sosElAdministrador(){
		return false;
	}
	public boolean sosElVendedor(){
		return true;
	}
	public boolean sosElAgenteComercial(){
		return false;
	}
	public boolean sosElOperador(){
		return false;
	}
}
