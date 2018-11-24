package negocio;

public class AgenteComercial extends Rol {

	public AgenteComercial(Usuario usuario) {
		super(usuario, "Agente Comercial");
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
		return true;
	}
	public boolean sosElOperador(){
		return false;
	}
}
