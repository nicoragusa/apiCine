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

public class MenuAdministrador extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static MenuAdministrador instancia;
	
	public static MenuAdministrador getInstancia(){
		if(instancia == null)
			instancia = new MenuAdministrador();
		return instancia;
	}

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuAdministrador frame = new MenuAdministrador();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MenuAdministrador() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 424, 21);
		contentPane.add(menuBar);
		
		JMenu mnAdministracion = new JMenu("Administracion");
		menuBar.add(mnAdministracion);
		
		JMenu mnEstablecimentos = new JMenu("Establecimentos");
		mnAdministracion.add(mnEstablecimentos);
		
		JMenuItem mntmAltaEstablecimiento = new JMenuItem("Alta Establecimiento");
		mntmAltaEstablecimiento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AltaEstablecimiento.getInstancia().setLocationRelativeTo(null);
				AltaEstablecimiento.getInstancia().setVisible(true);
			}
		});
		mnEstablecimentos.add(mntmAltaEstablecimiento);
		
		JMenuItem mntmBajaEstablecimiento = new JMenuItem("Baja Establecimiento");
		mntmBajaEstablecimiento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BajaEstablecimiento.getInstancia().setLocationRelativeTo(null);
				BajaEstablecimiento.getInstancia().setVisible(true);
			}
		});
		mnEstablecimentos.add(mntmBajaEstablecimiento);
		
		JMenuItem mntmModificarEstablecimiento = new JMenuItem("Modificar Establecimiento");
		mntmModificarEstablecimiento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ModificarEstablecimiento.getInstancia().setLocationRelativeTo(null);
				ModificarEstablecimiento.getInstancia().setVisible(true);
			}
		});
		mnEstablecimentos.add(mntmModificarEstablecimiento);
		
		JMenu mnSalas = new JMenu("Salas");
		mnAdministracion.add(mnSalas);
		
		JMenuItem mntmAltaSala = new JMenuItem("Alta Sala");
		mntmAltaSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AltaSala.getInstancia().setLocationRelativeTo(null);
				AltaSala.getInstancia().setVisible(true);
			}
		});
		mnSalas.add(mntmAltaSala);
		
		JMenuItem mntmBajaSala = new JMenuItem("Baja Sala");
		mntmBajaSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BajaSala.getInstancia().setLocationRelativeTo(null);
				BajaSala.getInstancia().setVisible(true);
			}
		});
		mnSalas.add(mntmBajaSala);
		
		JMenuItem mntmModificarSala = new JMenuItem("Modificar Sala");
		mntmModificarSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		mnSalas.add(mntmModificarSala);
		
		JMenu mnEmpleados = new JMenu("Empleados");
		mnAdministracion.add(mnEmpleados);
		
		JMenuItem mntmAltaEmpleado = new JMenuItem("Alta Empleado");
		mntmAltaEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CrearEmpleado.getInstancia().setLocationRelativeTo(null);
				CrearEmpleado.getInstancia().setVisible(true);
			}
		});
		mnEmpleados.add(mntmAltaEmpleado);
		
		JMenuItem mntmBajaEmpleado = new JMenuItem("Baja Empleado");
		mntmBajaEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e){
				EliminarEmpleado.getInstancia().setLocationRelativeTo(null);
				EliminarEmpleado.getInstancia().setVisible(true);
			}
		});
		mnEmpleados.add(mntmBajaEmpleado);
		
		JMenuItem mntmModificarEmpleado = new JMenuItem("Modificar Empleado");
		mnEmpleados.add(mntmModificarEmpleado);
		
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
		mntmCerrarSesin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				LogInControlador.getInstancia().logOut();
				LogInEmpleado.getInstancia().setLocationRelativeTo(null);
				LogInEmpleado.getInstancia().setVisible(true);
				dispose();
			}
		});
		mnSalir.add(mntmCerrarSesin);
	}

}
