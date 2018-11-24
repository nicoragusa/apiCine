package negocio;

import view.Pelicula_View;
import persistencia.AdmPersistenciaPelicula;

public class Pelicula {

	private String nombre;
	private String director;
	private String genero;
	private int duracion;
	private String idioma;
	private boolean subtitulos;
	private String clasificacion;
	private String observacion;
	private Operador op;
	private boolean estado;

	// constructor para dar de alta y persistir bd
	public Pelicula(String nombre, String director, String genero, int duracion, String idioma,
			boolean subtitulos,	String clasificacion, String observacion, Operador op) {
		this.nombre = nombre;
		this.director = director;
		this.genero = genero;
		this.duracion = duracion;
		this.idioma = idioma;
		this.subtitulos = subtitulos;
		this.clasificacion = clasificacion;
		this.observacion = observacion;
		this.op = op;
		this.estado = true;
		AdmPersistenciaPelicula.getInstancia().insert(this);
	}

	// constructor para recuperar desde bd
	public Pelicula(int duracion, boolean subtitulos, Operador op, boolean estado, String nombre, 
			String director, String genero, String idioma, String clasificacion, String observacion) {
		this.duracion = duracion;
		this.subtitulos = subtitulos;
		this.op = op;
		this.estado = estado;
		this.nombre = nombre;
		this.director = director;
		this.genero = genero;
		this.idioma = idioma;
		this.clasificacion = clasificacion;
		this.observacion = observacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public boolean isSubtitulos() {
		return subtitulos;
	}

	public void setSubtitulos(boolean subtitulos) {
		this.subtitulos = subtitulos;
	}

	public String getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Operador getOp() {
		return op;
	}

	public void setOp(Operador op) {
		this.op = op;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	public Pelicula_View getView() {
		return new Pelicula_View(nombre, director, genero, duracion, idioma, subtitulos, clasificacion,
				observacion,estado);
	}

	public boolean sosLaPelicula(String nombre, String idioma) {
		return (this.nombre.equalsIgnoreCase(nombre) && this.idioma.equalsIgnoreCase(idioma));
	}

	public void borrarPeliculaBD() {
		AdmPersistenciaPelicula.getInstancia().delete(this);
	}

	public void actualizarPeliculaBD() {
		AdmPersistenciaPelicula.getInstancia().update(this);
	}
}
