package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.LogInControlador;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuCliente extends JFrame {
	
private static MenuCliente instancia;
	
	public static MenuCliente getInstancia(){
		if(instancia == null)
			instancia = new MenuCliente();
		return instancia;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuCliente frame = new MenuCliente();
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
	public MenuCliente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnVenta = new JMenu("Cliente");
		menuBar.add(mnVenta);
		
		JMenuItem mntmVentaNueva = new JMenuItem("Comprar entradas");
		mntmVentaNueva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SeleccionPelicula.getInstancia().setVisible(true);
				SeleccionPelicula.getInstancia().setLocationRelativeTo(null);
				
			}
		});
		mnVenta.add(mntmVentaNueva);
		
		JMenuItem mntmRetirarEntradas = new JMenuItem("Retirar Entradas");
		mntmRetirarEntradas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Swing_RetirarEntrada.getInstancia().setLocationRelativeTo(null);
				Swing_RetirarEntrada.getInstancia().setVisible(true);
			}
		});
		mnVenta.add(mntmRetirarEntradas);
		
		JMenu mnSalir = new JMenu("Salir");
		menuBar.add(mnSalir);
		
		JMenuItem mntmCerrarSesin = new JMenuItem("Cerrar Sesi\u00F3n");
		mntmCerrarSesin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
