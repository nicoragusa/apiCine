package view;

public class Funcion_View {
	private int cod;
	private String dia, horario;
	private Pelicula_View p;
	private Sala_View s;
	
	public Funcion_View(Pelicula_View peli,Sala_View sala, String dia, String horario){
		p=peli;
		s=sala;
		this.dia=dia;
		this.horario=horario;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public Pelicula_View getP() {
		return p;
	}

	public void setP(Pelicula_View p) {
		this.p = p;
	}

	public Sala_View getS() {
		return s;
	}

	public void setS(Sala_View s) {
		this.s = s;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}
	
	
}
