package controlador;

import java.util.Vector;

import negocio.Pelicula;
import negocio.Administrador;
import negocio.Cine;
import negocio.Operador;
import persistencia.*;
import view.Cine_View;
import view.Funcion_View;
import view.Pelicula_View;
import view.Sala_View;

public class Controlador_Cine {

	private Vector<Cine> cines;
	private Vector<Pelicula> peliculas;
	private static Controlador_Cine instanciaCine;
	private static int precioEntradas;
	
	private Controlador_Cine() {
		super();
		cines = new Vector<Cine>();
		peliculas = new Vector<Pelicula>();
		precioEntradas=100;
	}
	
	public static int getPrecioEntradas(){
		return precioEntradas;
	}
	
	public Vector<Cine_View> getCines(){
		
		return AdmPersistenciaCine.getInstancia().select1();
	}
	
	public Vector<Pelicula_View> getPeliculas(){
		return AdmPersistenciaPelicula.getInstancia().select1();
	}
	
	
	public static Controlador_Cine getInstanciaCine() {
		if (instanciaCine == null)
			instanciaCine = new Controlador_Cine();
		return instanciaCine;
	}

	////////////////////////////////////////////////////////////////////////////////////////////
	public void altaCine(String nombre, String domicilio, int cantSalas, int capTotal, String cuit,
			Administrador admin) {
		Cine c = buscarCine(nombre);
		if (c == null) {
			c = new Cine(cuit, nombre, domicilio, cantSalas, capTotal, admin);
			cines.add(c);
		}
	}

	public Cine buscarCine(String nombre) {
		for (int i = 0; i < cines.size(); i++)
			if (cines.elementAt(i).sosElCineNombre(nombre))
				return cines.elementAt(i);
		Cine c = AdmPersistenciaCine.getInstancia().buscarCinePorNombre(nombre);
		if (c != null)
			cines.add(c);
		return c;
	}

	public Cine_View getCineView(String cuit) {
		for (int i = 0; i < cines.size(); i++)
			if (cines.elementAt(i).sosElCine(cuit))
				return cines.elementAt(i).getView();
		Cine c = AdmPersistenciaCine.getInstancia().buscarCine(cuit);
		if (c != null)
			return c.getView();
		return null;
	}

	public Cine buscarCineNombre(String nombre) {
		for (int i = 0; i < cines.size(); i++)
			if (cines.elementAt(i).sosElCineNombre(nombre))
				return cines.elementAt(i);
		Cine c = AdmPersistenciaCine.getInstancia().buscarCinePorNombre(nombre);
		if (c != null)
			cines.add(c);
		return c;
	}

	public void bajaCine(String cuit) {
		Cine c = buscarCine(cuit);
		if (c != null) {
			c.borrarCineBD();
			cines.removeElement(c);
		}
	}

	public void modificarCine(String cuit, String cuitNuevo, String nombre, String domicilio,
			int cantSalas, int capTotal) {
		Cine c = buscarCine(cuit);
		if (c != null) {
			c.setCuit(cuitNuevo);
			c.setNombre(nombre);
			c.setDomicilio(domicilio);
			c.setCapTotal(capTotal);
			c.setCantSalas(cantSalas);
			c.actualizarCineBD();
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	public void altaPelicula(String nombre, String director, String genero, int duracion, String idioma,
			boolean subtitulos, String clasificacion, String observacion, Operador op) {
		Pelicula p = buscarPelicula(nombre, idioma);
		if (p == null) {
			p = new Pelicula(nombre, director, genero, duracion, idioma, subtitulos,
					clasificacion, observacion, op);
			peliculas.add(p);
		}
	}

	public Pelicula buscarPelicula(String nombre, String idioma) {
		for (int i = 0; i < peliculas.size(); i++)
			if (peliculas.elementAt(i).sosLaPelicula(nombre, idioma))
				return peliculas.elementAt(i);
		Pelicula p = AdmPersistenciaPelicula.getInstancia().buscarPelicula(nombre, idioma);
		if (p != null)
			peliculas.add(p);
		return p;
	}

	public Pelicula_View getPeliculaView(String nombre, String idioma) {
		for (int i = 0; i < peliculas.size(); i++)
			if (peliculas.elementAt(i).sosLaPelicula(nombre, idioma))
				return peliculas.elementAt(i).getView();
		Pelicula p = AdmPersistenciaPelicula.getInstancia().buscarPelicula(nombre, idioma);
		if (p != null)
			return p.getView();
		return null;
	}

	public void bajaPelicula(String nombre, String idioma) {
		Pelicula p = buscarPelicula(nombre, idioma);
		if (p != null) {
			p.borrarPeliculaBD();
			peliculas.remove(p);
			
		}
	}

	public void modificarPelicula(String nombre, String idioma, int duracion, boolean subtitulos,
			String genero, String clasificacion, String observacion, 
			String director) {
		Pelicula p = buscarPelicula(nombre, idioma);
		if (p != null) {
			p.setClasificacion(clasificacion);
			p.setDuracion(duracion);
			p.setGenero(genero);
			p.setObservacion(observacion);
			p.setSubtitulos(subtitulos);
			p.setDirector(director);
			p.actualizarPeliculaBD();
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////

	public Vector<Sala_View> getSalas(String nombreCine){	
		return AdmPersistenciaSala.getInstancia().getSalas(nombreCine);
	}

	public Vector<Funcion_View> getFuncionesView(String cine, Pelicula_View pelicula) {
		// TODO Auto-generated method stub
		return AdmPersistenciaFuncion.getInstancia().getFunciones(cine, pelicula);
	}
}
