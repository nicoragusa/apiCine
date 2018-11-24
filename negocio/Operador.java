package negocio;

public class Operador extends Rol {

	public Operador(Usuario usuario) {
		super(usuario, "Operador");
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
		return false;
	}
	public boolean sosElAgenteComercial(){
		return false;
	}
	public boolean sosElOperador(){
		return true;
	}
}
