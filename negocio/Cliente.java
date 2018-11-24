package negocio;

public class Cliente extends Rol {
	
	public Cliente(Usuario usuario) {
		super(usuario, "Cliente");
		
		//usuario.asignarRol(this);
	}
	public boolean sosElCliente(){
		return true;
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
		return false;
	}
}
