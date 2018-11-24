package negocio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Vector;

import view.Cine_View;
import view.Funcion_View;
import view.Pelicula_View;
import persistencia.AdmPersistenciaFuncion;

public class Funcion {
	
	private Pelicula pelicula;
	private Sala sala;
	private Operador op;
	private LocalTime horario;
	private LocalDate dia;
	private Vector<Entrada> entradas; 
	private boolean estado;
	
										
	// constructor para dar de alta y persistir bd
	public Funcion(Pelicula pelicula, Sala sala, Operador op, LocalTime horario, LocalDate dia) {
		super();
		this.pelicula = pelicula;
		this.sala = sala;
		this.op = op;
		this.horario = horario;
		this.dia = dia;
		entradas = new Vector<Entrada>();
		this.estado = true;
	}

	// constructor para recuperar desde bd
	public Funcion(Sala sala, Pelicula pelicula, LocalTime horario, LocalDate dia,
			Operador op, Boolean estado) {
		this.pelicula = pelicula;
		this.sala = sala;
		this.horario = horario;
		this.dia = dia;
		this.estado = estado;
		this.op=op;
		entradas = new Vector<Entrada>();
	}

	public Pelicula getPelicula() {
		return pelicula;
	}

	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public Operador getOp() {
		return op;
	}

	public void setOp(Operador op) {
		this.op = op;
	}

	public LocalTime getHorario() {
		return horario;
	}

	public void setHorario(LocalTime horario) {
		this.horario = horario;
	}

	public LocalDate getDia() {
		return dia;
	}

	public void setDia(LocalDate dia) {
		this.dia = dia;
	}

	public Vector<Entrada> getEntradas() {
		return entradas;
	}

	public void setEntradas(Vector<Entrada> entradas) {
		this.entradas = entradas;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	public Funcion_View getView() {
		return new Funcion_View(pelicula.getView(), sala.getView(), dia.toString(),horario.toString());
	}

	public void borrarFuncionBD(String cuitCine) {
		AdmPersistenciaFuncion.getInstancia().delete1(this, cuitCine);
	}

	public void actualizarFuncionBD(LocalDate diaNuevo,LocalTime horarioNuevo, 
			String nombreSalaNuevo ,String nombreSala, LocalDate dia,LocalTime horario, String cuitCine) {
		AdmPersistenciaFuncion.getInstancia().update1(diaNuevo, horarioNuevo, nombreSalaNuevo
				,nombreSala,dia,horario,cuitCine);
	}

	public boolean sosLaFuncion(String nombreSala, LocalTime horario2, LocalDate dia2) {
		return (sala.getNombre().equalsIgnoreCase(nombreSala) && horario.equals(horario2)
				&& dia.equals(dia2));
	}

	public static Vector<LocalDate> getDias(Cine_View cine_View, Pelicula_View pelicula_View) {
		// TODO Auto-generated method stub
		return AdmPersistenciaFuncion.getInstancia().getDiasDePelicula(cine_View, pelicula_View);
	}

	public static Vector<LocalTime> getHorarios(Cine_View cine_View, Pelicula_View pelicula_View, LocalDate dia) {
		// TODO Auto-generated method stub
		return AdmPersistenciaFuncion.getInstancia().getHorariosDePelicula(cine_View, pelicula_View, dia);
	}

	public boolean sosLaFuncionConPelicula(String pelicula, LocalTime horario, LocalDate dia) {
		// TODO Auto-generated method stub
		return this.pelicula.getNombre().equals(pelicula) && this.horario.equals(horario) && this.dia.equals(dia);
	}
	
	public static void  insertarFuncion(Funcion f, String cine){
		AdmPersistenciaFuncion.getInstancia().insert1(f, cine);
	}

	public void agregarEntradas(Vector<Entrada> entradas) {
		// TODO Auto-generated method stub
		this.entradas.addAll(entradas);
	}

}
