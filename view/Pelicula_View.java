package view;

public class Pelicula_View {
	private String nombre, director, genero, idioma, observacion, clasificacion;
	private int duracion;
	private boolean estado, subtitulos;
	
	public Pelicula_View(String nombre, String director, String genero, int duracion, String idioma,
			boolean subtitulos,	String clasificacion, String observacion, boolean estado){
		
		this.nombre = nombre;
		this.director = director;
		this.genero = genero;
		this.duracion = duracion;
		this.idioma = idioma;
		this.subtitulos = subtitulos;
		this.observacion=observacion;
		this.clasificacion = clasificacion;
		this.estado=estado;
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

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public boolean isSubtitulos() {
		return subtitulos;
	}

	public void setSubtitulos(boolean subtitulos) {
		this.subtitulos = subtitulos;
	}
	
	public String getNombreIdioma(){
		if(isSubtitulos())
			return this.nombre+" "+"Subtitulada";
		
		return this.nombre+" "+this.idioma;
	}
}
