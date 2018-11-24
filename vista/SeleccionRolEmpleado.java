package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import negocio.Rol;

public class SeleccionRolEmpleado extends JFrame {

	private static final long serialVersionUID = 1L;
	private static SeleccionRolEmpleado instancia=null;
	private JPanel contentPane;
	private static Vector<Rol> roles;
	JComboBox<String> comboBox;
	/**
	 * Launch the application.
	 */
	
	/**Metodo singleton tradicional*/
	public static SeleccionRolEmpleado getInstancia(){
		if(instancia==null)
			instancia=new SeleccionRolEmpleado();
		return instancia;
	}
	
	/**Metodo singleton pero con los roles a mostrar en el combo*/
	public static SeleccionRolEmpleado getInstancia(Vector<Rol> vEmpleado) {
		// TODO Auto-generated method stub
		roles=vEmpleado;
		if(instancia==null)
			instancia=new SeleccionRolEmpleado();
		return instancia;
	}
	
	/**
	 * Create the frame.
	 */
	public SeleccionRolEmpleado() {
		setTitle("Rol del Usuario");
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 363, 171);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSeleccioneElRol = new JLabel("Seleccione el rol");
		lblSeleccioneElRol.setBounds(43, 25, 107, 14);
		contentPane.add(lblSeleccioneElRol);
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(154, 22, 169, 20);
		
		for(Rol r:roles){
			comboBox.addItem(r.getDescripcion());
		}
		
		contentPane.add(comboBox);			
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int pos=comboBox.getSelectedIndex();
				mostrarRol(roles.get(pos));
				dispose();
			}
		});
		btnConfirmar.setBounds(216, 79, 107, 23);
		contentPane.add(btnConfirmar);
		
		JButton btnAtras = new JButton("Atr\u00E1s");
		btnAtras.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
			
		});
		btnAtras.setBounds(43, 79, 89, 23);
		contentPane.add(btnAtras);
		
		
	}

	void mostrarRol (Rol rol) {
		
		if(rol.sosElAdministrador()){
			 MenuAdministrador.getInstancia().setLocationRelativeTo(null);
			 MenuAdministrador.getInstancia().setVisible(true);
		 }
		 else if(rol.sosElOperador()){
			 MenuOperador.getInstancia().setLocationRelativeTo(null);
			 MenuOperador.getInstancia().setVisible(true);
		 }
		 else if(rol.sosElAgenteComercial()){
			 MenuAgenteComercial.getInstancia().setLocationRelativeTo(null);
			 MenuAgenteComercial.getInstancia().setVisible(true);
		 }
		 else if(rol.sosElVendedor()){
			 MenuVendedor.getInstancia().setLocationRelativeTo(null);
			 MenuVendedor.getInstancia().setVisible(true);
		 }	 

	}

	public void setRoles(Vector<Rol> vEmpleado) {
		// TODO Auto-generated method stub
		roles=vEmpleado;
	}


}
