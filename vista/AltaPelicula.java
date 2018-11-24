package vista;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controlador.Controlador_Cine;
import controlador.LogInControlador;
import negocio.Operador;
import negocio.Usuario;

public class AltaPelicula extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static AltaPelicula instancia;
	private JPanel contentPane;
	private JTextField textFieldNombre;
	private JTextField textFieldDirector;
	private JTextField textFieldGenero;
	private JTextField textFieldDuracion;
	private JTextField textFieldIdioma;
	private JTextField textFieldCalificacion;
	private JTextField textFieldDescripcion;
	private ButtonGroup botones = new ButtonGroup();

	public static AltaPelicula getInstancia() {
		if (instancia == null)
			instancia = new AltaPelicula();
		return instancia;
	}

	private AltaPelicula() {
		setTitle("Alta Pelicula");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 477, 345);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel etiqNombre = new JLabel("Nombre:");
		etiqNombre.setBounds(20, 20, 70, 14);
		contentPane.add(etiqNombre);

		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(124, 17, 301, 20);
		contentPane.add(textFieldNombre);
		textFieldNombre.setColumns(10);

		JLabel etiqDirector = new JLabel("Director:");
		etiqDirector.setBounds(20, 55, 70, 14);
		contentPane.add(etiqDirector);

		JLabel etiqGenero = new JLabel("Genero:");
		etiqGenero.setBounds(20, 90, 70, 14);
		contentPane.add(etiqGenero);

		JLabel etiqDuracion = new JLabel("Duracion:");
		etiqDuracion.setBounds(20, 160, 70, 14);
		contentPane.add(etiqDuracion);

		JLabel etiqIdioma = new JLabel("Idioma:");
		etiqIdioma.setBounds(20, 125, 70, 14);
		contentPane.add(etiqIdioma);

		textFieldDirector = new JTextField();
		textFieldDirector.setBounds(124, 52, 301, 20);
		contentPane.add(textFieldDirector);
		textFieldDirector.setColumns(10);

		textFieldGenero = new JTextField();
		textFieldGenero.setBounds(124, 87, 301, 20);
		contentPane.add(textFieldGenero);
		textFieldGenero.setColumns(10);

		textFieldDuracion = new JTextField();
		textFieldDuracion.setBounds(124, 157, 105, 20);
		contentPane.add(textFieldDuracion);
		textFieldDuracion.setColumns(10);

		textFieldIdioma = new JTextField();
		textFieldIdioma.setBounds(124, 122, 301, 20);
		contentPane.add(textFieldIdioma);
		textFieldIdioma.setColumns(10);

		JLabel etiqSubtitulos = new JLabel("Subtitulos:");
		etiqSubtitulos.setBounds(20, 230, 70, 14);
		contentPane.add(etiqSubtitulos);

		JRadioButton radioBtnSubtitulosSi = new JRadioButton("Si");
		radioBtnSubtitulosSi.setBounds(124, 226, 53, 23);
		contentPane.add(radioBtnSubtitulosSi);

		JRadioButton radioBtnSubtitulosNo = new JRadioButton("No");
		radioBtnSubtitulosNo.setBounds(179, 226, 50, 23);
		contentPane.add(radioBtnSubtitulosNo);
		
		botones.add(radioBtnSubtitulosSi);//para que seleccione o uno u otro
		botones.add(radioBtnSubtitulosNo);

		JLabel etiqCalificacion = new JLabel("Calificacion:");
		etiqCalificacion.setBounds(232, 160, 70, 14);
		contentPane.add(etiqCalificacion);

		textFieldCalificacion = new JTextField();
		textFieldCalificacion.setBounds(312, 157, 113, 20);
		contentPane.add(textFieldCalificacion);
		textFieldCalificacion.setColumns(10);

		JButton BotonCrearPelicula = new JButton("Crear Pelicula");
		BotonCrearPelicula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Usuario u = LogInControlador.getInstancia().buscarUsuarioPorDni("3");//getUsuarioLogueado();
				
				Operador op = (Operador)u.getOperador();
				
				Controlador_Cine.getInstanciaCine().altaPelicula(textFieldNombre.getText(),
						textFieldDirector.getText(), textFieldGenero.getText(), 
						Integer.parseInt(textFieldDuracion.getText()), textFieldIdioma.getText(),
						radioBtnSubtitulosSi.isSelected(), textFieldCalificacion.getText(),
						textFieldDescripcion.getText(), op);
				JOptionPane.showMessageDialog(null, "Pelicula creada con exito");
				dispose();
			}
		});
		BotonCrearPelicula.setBounds(260, 230, 165, 47);
		contentPane.add(BotonCrearPelicula);

		JLabel etiqDescripcion = new JLabel("Descripcion:");
		etiqDescripcion.setBounds(20, 195, 94, 14);
		contentPane.add(etiqDescripcion);

		textFieldDescripcion = new JTextField();
		textFieldDescripcion.setBounds(124, 192, 301, 20);
		contentPane.add(textFieldDescripcion);
		textFieldDescripcion.setColumns(10);
	}
}
