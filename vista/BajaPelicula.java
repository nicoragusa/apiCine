package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controlador.Controlador_Cine;

public class BajaPelicula extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static BajaPelicula instancia;
	private JTextField textFieldPeli;
	private JTextField textFieldIdioma;

	
	public static BajaPelicula getInstancia() {
		if (instancia == null)
			instancia = new BajaPelicula();
		return instancia;
	}

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	private BajaPelicula() {
		setTitle("Baja Pelicula");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 260);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel etiqPeli = new JLabel("Pelicula:");
		etiqPeli.setBounds(47, 54, 65, 14);
		contentPane.add(etiqPeli);
		
		JButton botonEliminar = new JButton("Eliminar");
		botonEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Controlador_Cine.getInstanciaCine().bajaPelicula(textFieldPeli.getText(), 
						textFieldIdioma.getText());
				JOptionPane.showMessageDialog(null, "Pelicula borrada");
				dispose();
			}
		});
		botonEliminar.setBounds(182, 144, 125, 49);
		contentPane.add(botonEliminar);
		
		textFieldPeli = new JTextField();
		textFieldPeli.setBounds(122, 51, 259, 20);
		contentPane.add(textFieldPeli);
		textFieldPeli.setColumns(10);
		
		JLabel etiqIdioma = new JLabel("Idioma:");
		etiqIdioma.setBounds(47, 94, 65, 14);
		contentPane.add(etiqIdioma);
		
		textFieldIdioma = new JTextField();
		textFieldIdioma.setBounds(122, 91, 259, 20);
		contentPane.add(textFieldIdioma);
		textFieldIdioma.setColumns(10);
		
		
	}
}