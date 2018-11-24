package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controlador.Controlador_Cine;
import controlador.LogInControlador;
import negocio.Cine;
import negocio.Operador;
import negocio.Pelicula;
import negocio.Sala;
import negocio.Usuario;
import view.Cine_View;
import view.Pelicula_View;
import view.Sala_View;

public class AltaFuncion extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static AltaFuncion instancia;
	private JPanel contentPane;
	private JTextField textFieldHorario;
	private JTextField textFieldDia;
	private Vector<Cine_View> cv=new Vector<Cine_View>();
	private Vector<Pelicula_View> pv=new Vector<Pelicula_View>();
	private Vector<Sala_View>sv=new Vector <Sala_View>();
	private JComboBox<String> comboBoxPelicula;
	private JComboBox<String> comboBoxSala;
	private JComboBox<String> comboBoxCine;
	public static AltaFuncion getInstancia() {
		if (instancia == null)
			instancia = new AltaFuncion();
		return instancia;
	}


	private AltaFuncion() {
		getContentPane().setLayout(null);
		setTitle("Alta Funcion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textFieldHorario = new JTextField();
		textFieldHorario.setBounds(264, 145, 128, 20);
		contentPane.add(textFieldHorario);
		textFieldHorario.setColumns(10);

		textFieldDia = new JTextField();
		textFieldDia.setText("a\u00F1o-mes-dia");
		textFieldDia.setBounds(76, 145, 128, 20);
		contentPane.add(textFieldDia);
		textFieldDia.setColumns(10);
		
		comboBoxCine = new JComboBox<String>();
		comboBoxCine.addItem("-");
		comboBoxCine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int pos=comboBoxCine.getSelectedIndex();
				
				if(comboBoxPelicula!=null){
					comboBoxPelicula.removeAllItems();
					comboBoxSala.removeAllItems();				
				}
				if(pos>0){
					String nombre=cv.get(pos-1).getNombre();
					fillPeliculas();
					fillSalas(nombre);
				}
				
			}
		});
		comboBoxCine.setBounds(76, 25, 316, 20);
		fillCines();
		contentPane.add(comboBoxCine);
		
		JLabel etiqCine = new JLabel("Cine:");
		etiqCine.setBounds(20, 30, 46, 14);
		contentPane.add(etiqCine);

		JLabel etiqSala = new JLabel("Sala:");
		etiqSala.setBounds(20, 110, 46, 14);
		contentPane.add(etiqSala);

		JLabel etiqPelicula = new JLabel("Pelicula:");
		etiqPelicula.setBounds(20, 70, 53, 14);
		contentPane.add(etiqPelicula);

		JLabel etiqDia = new JLabel("Dia:");
		etiqDia.setBounds(20, 150, 46, 14);
		contentPane.add(etiqDia);

		JLabel etiqHorario = new JLabel("Horario:");
		etiqHorario.setBounds(214, 150, 46, 14);
		contentPane.add(etiqHorario);
		
		comboBoxPelicula = new JComboBox<String>();
		comboBoxPelicula.setBounds(76, 67, 316, 20);
		contentPane.add(comboBoxPelicula);
		
		comboBoxSala = new JComboBox<String>();
		comboBoxSala.setBounds(76, 107, 316, 20);
		contentPane.add(comboBoxSala);
		
		
		JButton botonCrearFuncion = new JButton("Crear Funcion");
		botonCrearFuncion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Usuario u = LogInControlador.getInstancia().buscarUsuarioPorDni("3");//.getUsuarioLogueado();
				
				Operador op = new Operador(u);
				
				Cine c = Controlador_Cine.getInstanciaCine().
						buscarCineNombre((String) comboBoxCine.getSelectedItem());
				System.out.println(pv.size());
				Pelicula_View aux=pv.get(comboBoxPelicula.getSelectedIndex());
				Pelicula p = Controlador_Cine.getInstanciaCine().buscarPelicula(aux.getNombre(),aux.getIdioma());
				
				if(p!=null){
					
				Sala s = c.buscarSala(sv.get(comboBoxSala.getSelectedIndex()).getNombre());
					if(s!=null){
					
						c.altaFuncion(p, s, op, LocalTime.parse(textFieldHorario.getText()),
								LocalDate.parse(textFieldDia.getText()));
						
					}
				}
			}
		});
		botonCrearFuncion.setBounds(264, 188, 128, 50);
		contentPane.add(botonCrearFuncion);				

	}
	
	private void fillSalas(String cine){

		sv=Controlador_Cine.getInstanciaCine().getSalas(cine);
		for(Sala_View s:sv)
			comboBoxSala.addItem(s.getNombre());
	}
	
	private void fillPeliculas(){
		pv=Controlador_Cine.getInstanciaCine().getPeliculas();
		for(Pelicula_View p:pv)
			comboBoxPelicula.addItem(p.getNombreIdioma());
	}
	
	private void fillCines(){
		if(cv.isEmpty())
			cv = Controlador_Cine.getInstanciaCine().getCines();
		for (int i = 0; i < cv.size(); i++) {
			comboBoxCine.addItem(cv.elementAt(i).getNombre());
		}
	}
}
