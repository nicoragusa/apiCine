package negocio;

public class Administrador extends Rol {
	public Administrador(Usuario usuario) {
		super(usuario, "Administrador");
		// TODO Auto-generated constructor stub
		//usuario.asignarRol(this);
	}
	
	public boolean sosElCliente(){
		return false;
	}
	public boolean sosElAdministrador(){
		return true;
	}
	public boolean sosElVendedor(){
		return false;
	}
	public boolean sosElAgenteComercial(){
		return false;
	}
	public boolean sosElOperador(){
		return false;
	}
}
