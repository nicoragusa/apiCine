package view;

public class Sala_View {
	private String nombre;
	private int capacidad;
	
	public Sala_View(String nombre, int capacidad) {
		super();
		this.nombre = nombre;
		this.capacidad = capacidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
	
	
}
