package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.LogInControlador;
import vista.SeleccionAsientos;
import vista.SeleccionPelicula;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuVendedor extends JFrame {
	
private static MenuVendedor instancia;
	
	public static MenuVendedor getInstancia(){
		if(instancia == null)
			instancia = new MenuVendedor();
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
					MenuVendedor frame = new MenuVendedor();
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
	public MenuVendedor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnVenta = new JMenu("Venta");
		menuBar.add(mnVenta);
		
		JMenuItem mntmVentaNueva = new JMenuItem("Nueva Venta");
		mntmVentaNueva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SeleccionPelicula.getInstancia().setVisible(true);
				SeleccionPelicula.getInstancia().setLocationRelativeTo(null);
				
			}
		});
		mnVenta.add(mntmVentaNueva);
		
		JMenu mnSalir = new JMenu("Salir");
		menuBar.add(mnSalir);
		
		JMenuItem mntmCambiarRol = new JMenuItem("Cambiar Rol");
		mnSalir.add(mntmCambiarRol);
		
		JMenuItem mntmCerrarSesin = new JMenuItem("Cerrar Sesi\u00F3n");
		mntmCerrarSesin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LogInControlador.getInstancia().setUsuarioLogueado(null);
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
