package negocio;

import java.time.LocalDate;
import java.util.Vector;

import persistencia.AdmPersistenciaRol;
import persistencia.AdmPersistenciaUsuario;

public class Usuario{ 
	/**Datos del usuario*/
	private String mail,nombreUsuario,password,nombre,dom,dni,apellido;
	
	private boolean estado;
	
	private LocalDate fechaNac;
	
	private Vector<Rol> roles;

	public Usuario(String mail, String nombreUsuario, String password, String nombre, String dom, String dni,
			String apellido, LocalDate fechaNac, boolean estado) {
		super();
		this.mail = mail;
		this.nombreUsuario = nombreUsuario;
		this.password = password;
		this.nombre = nombre;
		this.dom = dom;
		this.dni = dni;
		this.apellido = apellido;
		this.fechaNac = fechaNac;
		this.estado=estado;	
		roles=AdmPersistenciaRol.getInstancia().selectAll(this);
	}

	public Usuario(String mail, String nombreUsuario, String password, String nombre, String dom, String dni,
			String apellido, LocalDate fechaNac) {
		super();
		this.mail = mail;
		this.nombreUsuario = nombreUsuario;
		this.password = password;
		this.nombre = nombre;
		this.dom = dom;
		this.dni = dni;
		this.apellido = apellido;
		this.fechaNac = fechaNac;
		this.roles=new Vector<Rol>();
		this.estado=true;
		
		AdmPersistenciaUsuario.getInstancia().insert(this);
		System.out.println("Usuario registrado en la BD");
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDom() {
		return dom;
	}

	public void setDom(String dom) {
		this.dom = dom;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public LocalDate getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(LocalDate fechaNac) {
		this.fechaNac = fechaNac;
	}

	public Vector<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Vector<Rol> roles) {
		this.roles = roles;
	}
	
	/**Asigna un nuevo rol al usuario. Verifica que el mismo no exista previamente
	 * y lo agrega a la collection y a la persistencia
	 * @param Nuevo Rol a insertar
	 */
	public void asignarRol(Rol newRol){
		for(Rol r:roles)
			if(r.getDescripcion().equals(newRol.getDescripcion())){
				System.out.println("Ya existe el usuario "+this.nombre+" con el rol "+newRol.getDescripcion());
				return;
			}
						
		AdmPersistenciaRol.getInstancia().insert(newRol);
		this.roles.addElement(newRol);
		
		System.out.println("Nuevo rol asignado");
	}
	
	/**Modifica el rol de un usuario. Devuelve falso
	 * si alguno de los roles ingresados no coinciden 
	 * con los pre establecidos o si el rol a 
	 * modificar no lo tiene asignado.
	 * @param descripcion del rol a modificar
	 * @param descripcion del nuevo rol
	 * @return boolean*/
	public boolean modificarRol(Rol rolViejo, Rol rolNuevo){
			
		return false;
	}
	
	public Rol getCliente(){
		for(Rol r:roles)
			if(r.sosElCliente())
				return r;	
		return null;
	}
	public Rol getAdministrador(){
		for(Rol r:roles)
			if(r.sosElAdministrador())
				return r;
		return null;
	}
	public Rol getVendedor(){
		for(Rol r:roles)
			if(r.sosElVendedor())
				return r;	
		return null;
	}
	public Rol getAgenteComercial(){
		for(Rol r:roles)
			if(r.sosElAgenteComercial())
				return r;	
		return null;
	}
	public Rol getOperador(){
		for(Rol r:roles)
			if(r.sosElOperador())
				return r;
		return null;
	}
	
	public void eliminarUsuario(){
		AdmPersistenciaUsuario.getInstancia().delete(this);
	}
	
	public void actualizarDatos(){
		AdmPersistenciaUsuario.getInstancia().update(this);
	}

	/**Es un metodo que devuelve el filtro de todos los roles de empleado que tenga un usuario*/
	public Vector<Rol> getVectorEmpleados() {
		Vector<Rol>res=new Vector<Rol>();
		for(Rol r:roles){
			if(!r.sosElCliente())
				res.add(r);
		}
		return res;
	}

	public void borrarRolesEmpleado() {
		int i=0;
		while(i<roles.size()){
			if(roles.elementAt(i).getDescripcion().equalsIgnoreCase("Cliente") == false){
				AdmPersistenciaRol.getInstancia().delete(roles.elementAt(i));
				roles.remove(roles.elementAt(i));
				i--;
			}
			i++;
		}
	}
		
}
