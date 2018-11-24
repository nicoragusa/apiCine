package vista;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.LogInControlador;

public class MenuOperador extends JFrame {
	
private static MenuOperador instancia;
	
	public static MenuOperador getInstancia(){
		if(instancia == null)
			instancia = new MenuOperador();
		return instancia;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.


	/**
	 * Create the frame.
	 */
	private MenuOperador() {
		setTitle("Menu Principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnPeliculas = new JMenu("Administracion");
		menuBar.add(mnPeliculas);
		
		JMenu mnPeliculas_1 = new JMenu("Peliculas");
		mnPeliculas.add(mnPeliculas_1);
		
		JMenuItem mntmAltaPelicula = new JMenuItem("Alta Pelicula");
		mntmAltaPelicula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AltaPelicula.getInstancia().setLocationRelativeTo(null);
				AltaPelicula.getInstancia().setVisible(true);
			}
		});
		mnPeliculas_1.add(mntmAltaPelicula);
		
		JMenuItem mntmBajaPelicula = new JMenuItem("Baja Pelicula");
		mntmBajaPelicula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				BajaPelicula.getInstancia().setLocationRelativeTo(null);
//				BajaPelicula.getInstancia().setVisible(true);
			}
		});
		mnPeliculas_1.add(mntmBajaPelicula);
		
		JMenu mnFunciones = new JMenu("Funciones");
		mnPeliculas.add(mnFunciones);
		
		JMenuItem mntmAltaFuncion = new JMenuItem("Alta Funcion");
		mntmAltaFuncion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				AltaFuncion.getInstancia().setLocationRelativeTo(null);
//				AltaFuncion.getInstancia().setVisible(true);
			}
		});
		mnFunciones.add(mntmAltaFuncion);
		
		JMenu mnSalir = new JMenu("Salir");
		mnSalir.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(1);
			}
		});
		menuBar.add(mnSalir);
		
		JMenuItem mntmCambiarRol = new JMenuItem("Cambiar Rol");
		mntmCambiarRol.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				SeleccionRolEmpleado.getInstancia().setLocationRelativeTo(null);
				SeleccionRolEmpleado.getInstancia().setVisible(true);
				dispose();
			}
		});
		mnSalir.add(mntmCambiarRol);
		
		JMenuItem mntmCerrarSesin = new JMenuItem("Cerrar Sesi\u00F3n");
		mntmCerrarSesin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LogInControlador.getInstancia().setUsuarioLogueado(null);
				LogInControlador.getInstancia().logOut();
				LogInEmpleado.getInstancia().setLocationRelativeTo(null);
				LogInEmpleado.getInstancia().setVisible(true);				
				dispose();
			}
		});
		mnSalir.add(mntmCerrarSesin);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
}
