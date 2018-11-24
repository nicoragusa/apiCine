package negocio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Vector;

import view.*;
import persistencia.*;

public class Cine {
	
	private String cuit;
	private String nombre;
	private String domicilio;
	private int cantSalas;
	private int capTotal;
	private Administrador admin;
	private Vector<Sala> salas;
	private Vector<Funcion> funciones;
	private boolean estado;
	
	// constructor para dar de alta y persistir bd
	public Cine(String cuit, String nombre, String domicilio, int cantSalas, int capTotal, 
			Administrador admin) {
		super();
		this.cuit = cuit;
		this.nombre = nombre;
		this.domicilio = domicilio;
		this.cantSalas = cantSalas;
		this.capTotal = capTotal;
		salas = new Vector<Sala>();
		funciones = new Vector<Funcion>();
		this.admin = admin;
		this.estado = true;
		AdmPersistenciaCine.getInstancia().insert(this);
	}

	// constructor para recuperar desde bd
	public Cine(String cuit, String nombre, String domicilio,int cantSalas, int capTotal,  
			Administrador admin, boolean estado) {
		this.cantSalas = cantSalas;
		this.capTotal = capTotal;
		this.cuit = cuit;
		this.nombre = nombre;
		this.domicilio = domicilio;
		this.admin = admin;
		this.estado = estado;
		salas = new Vector<Sala>();
		funciones = new Vector<Funcion>();
	}

	
	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public Administrador getAdmin() {
		return admin;
	}

	public void setAdmin(Administrador admin) {
		this.admin = admin;
	}

	public Vector<Sala> getSalas() {
		return salas;
	}

	public void setSalas(Vector<Sala> salas) {
		this.salas = salas;
	}

	public Vector<Funcion> getFunciones() {
		return funciones;
	}

	public void setFunciones(Vector<Funcion> funciones) {
		this.funciones = funciones;
	}
	
	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////
	public Cine_View getView() {
		return new Cine_View(nombre, cuit, domicilio, cantSalas, capTotal, estado);
	}

	public boolean sosElCine(String cuitCine) {
		return cuit.equalsIgnoreCase(cuitCine);
	}

	public boolean sosElCineNombre(String n) {
		return nombre.equalsIgnoreCase(n);
	}

	public void borrarCineBD() {
		AdmPersistenciaCine.getInstancia().delete(this);
	}

	public void actualizarCineBD() {
		AdmPersistenciaCine.getInstancia().update(this);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	public void altaSala(String nombreSala, int capacidad, Administrador adm) {
		Sala s = buscarSala(nombreSala);
		if (s == null) {
			s = new Sala(nombreSala, capacidad, adm);
			Sala.insertarSala(nombre, s);
			AsientoFisico.insertarAsiento(nombre, nombreSala, s.getAsientosFi());
			
			salas.add(s);
		}
	}

	public Sala buscarSala(String nombre) {
		for (int i = 0; i < salas.size(); i++)
			if (salas.elementAt(i).sosLaSala(nombre))
				return salas.elementAt(i);
		Sala s = AdmPersistenciaSala.getInstancia().buscarSala(nombre, this.nombre);
		if (s != null)
			salas.add(s);
		return s;
	}
	
	public Sala_View getSalaView(String nombre) {
		for (int i = 0; i < salas.size(); i++)
			if (salas.elementAt(i).sosLaSala(nombre))
				return salas.elementAt(i).getView();
		Sala s = AdmPersistenciaSala.getInstancia().buscarSala(nombre, this.nombre);
		if (s != null)
			return s.getView();
		return null;
	}
	
	public void bajaSala(String nombreSala) {
		Sala s = buscarSala(nombreSala);
		if (s != null) {
			salas.removeElement(s);
			s.borrarSalaBD(nombre);
		}
	}	
	
	public void modificarSala(String nombre, int capacidad, String nombreNuevo) {
		Sala s = buscarSala(nombre);
		if (s != null) {
			s.setNombre(nombreNuevo);
		//	s.setCapacidad(capacidad);
			s.actualizarSalaBD(nombreNuevo, capacidad, nombre, getCuit());
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	public void altaFuncion(Pelicula p, Sala s, Operador op, LocalTime horario, LocalDate dia) {
		Funcion f = buscarFuncion(s.getNombre(), horario, dia);
		if (f == null) {
			f = new Funcion(p, s, op, horario, dia);
			Funcion.insertarFuncion(f,this.nombre);
			funciones.add(f);
		}
	}

	public Funcion buscarFuncion(String nombreSala, LocalTime horario, LocalDate dia) {
		for (int i = 0; i < funciones.size(); i++)
			if (funciones.elementAt(i).sosLaFuncion(nombreSala, horario, dia))
				return funciones.elementAt(i);
		Funcion f = AdmPersistenciaFuncion.getInstancia().buscarFuncion(nombreSala, horario,
				dia, this.nombre);
		if (f != null)
			return f;
		return null;
	}
	
	public boolean bajaFuncion(String nombre, LocalTime horario, LocalDate dia){
		Funcion f= buscarFuncion(nombre, horario, dia);
		boolean res=false;
		if(f!=null && f.getEntradas().size()==0){
			funciones.remove(f);
			f.borrarFuncionBD(getCuit());
			res=true;
		}
		return res;
	}

	public void modificarFuncion(String nombre, LocalTime horario, LocalDate dia,
			String nombreNuevo, LocalTime horarioNuevo, LocalDate diaNuevo){
		Funcion f = buscarFuncion(nombre,horario,dia);
		if(f!=null){
			f.setDia(diaNuevo);
			f.setHorario(horarioNuevo);
			f.setSala(AdmPersistenciaSala.getInstancia().buscarSala(nombreNuevo, this.nombre));
			f.actualizarFuncionBD(diaNuevo,horarioNuevo,nombreNuevo,nombre,dia,horario, getCuit());
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////	
	/*public Vector<LocalDate> getDias(Pelicula p){		 
		return AdmPersistenciaFuncion.getInstancia().getDiasDePelicula(this,p);		
	}
	
	public Vector<LocalTime> getHorarios(Pelicula p, LocalDate dia){
		return AdmPersistenciaFuncion.getInstancia().getHorariosDePelicula(this,p,dia);		
	}*/

	public Funcion buscarFuncionPorPelicula(String pelicula, LocalTime horario, LocalDate dia) {
		// TODO Auto-generated method stub
		for (int i = 0; i < funciones.size(); i++)
			if (funciones.elementAt(i).sosLaFuncionConPelicula(pelicula, horario, dia))
				return funciones.elementAt(i);
		Funcion f = AdmPersistenciaFuncion.getInstancia().buscarFuncionPorPelicula(pelicula, horario,dia, this.nombre);
		if(f!=null) funciones.add(f);
		return f;
	}
	
}