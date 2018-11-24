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

public class MenuAgenteComercial extends JFrame {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private static MenuAgenteComercial instancia;
	
	public static MenuAgenteComercial getInstancia(){
		if(instancia == null)
			instancia = new MenuAgenteComercial();
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
					MenuAgenteComercial frame = new MenuAgenteComercial();
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
	public MenuAgenteComercial() {
		setTitle("Menu Agente Comercial");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnPromociones = new JMenu("Promociones");
		menuBar.add(mnPromociones);
		
		JMenu mnx = new JMenu("2x1");
		mnPromociones.add(mnx);
		
		JMenuItem mntmCrear = new JMenuItem("Crear");
		mntmCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				Alta2x1.getInstancia().setLocationRelativeTo(null);
//				Alta2x1.getInstancia().setVisible(true);
			}
			
		});
		mnx.add(mntmCrear);
		
		JMenuItem mntmModificar = new JMenuItem("Modificar");
		mnx.add(mntmModificar);
		
		JMenu mnDescuentos = new JMenu("Descuentos");
		mnPromociones.add(mnDescuentos);
		
		JMenuItem mntmCrear_1 = new JMenuItem("Crear");
		mntmCrear_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				AltaDescuento.getInstancia().setLocationRelativeTo(null);
//				AltaDescuento.getInstancia().setVisible(true);
			}
		});
		mnDescuentos.add(mntmCrear_1);
		
		JMenuItem mntmModificar_1 = new JMenuItem("Modificar");
		mnDescuentos.add(mntmModificar_1);
		
		JMenu mnSalir = new JMenu("Salir");
		menuBar.add(mnSalir);
		
		JMenuItem mntmCambiarRol = new JMenuItem("Cambiar Rol");
		mnSalir.add(mntmCambiarRol);
		
		JMenuItem mntmCerrarSesin = new JMenuItem("Cerrar Sesi\u00F3n");
		mnSalir.add(mntmCerrarSesin);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
}
