package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controlador.LogInControlador;
import negocio.Rol;
import negocio.Usuario;

public class CrearEmpleado extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static CrearEmpleado instancia = null;
	private JPanel contentPane;
	private JTextField dni;
	private JTextField nom;
	private JTextField ape;
	private JTextField nac;
	private JTextField dom;
	private JTextField mail;
	private JTextField us;
	private JLabel lblContrasena;
	private JButton btnCrear;
	private JButton btnCancelar;
	private JPanel panel;
	private JPasswordField pass;
	private JRadioButton rdbtnAdministrador;
	private JRadioButton rdbtnOperador;
	private JRadioButton rdbtnVendedor;
	private JRadioButton rdbtnAgenteComercial;
	private boolean existeUsuario=false;
	private Usuario u;
	private Vector<String> vRolesString = new Vector<String>();
	/**
	 * Launch the application.
	 */
	public static CrearEmpleado getInstancia(){
		if(instancia==null)
			instancia=new CrearEmpleado();
		return instancia;
	}
	
	/**
	 * Create the frame.
	 */
	private CrearEmpleado() {
		setTitle("Crear Empleado");
		setType(Type.UTILITY);
		reiniciarComponentes();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 330);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		dni = new JTextField();
		dni.setBounds(92, 11, 203, 20);
		contentPane.add(dni);
		dni.setColumns(10);
		
		panel = new JPanel();
		panel.setBounds(10, 56, 464, 224);
		contentPane.add(panel);
		panel.setLayout(null);
		panel.setVisible(false);
		
		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setBounds(10, 11, 53, 14);
		panel.add(lblNombre);
		
		nom = new JTextField();
		nom.setBounds(82, 6, 134, 20);
		panel.add(nom);
		nom.setColumns(10);
		
		JLabel lblApellido = new JLabel("Apellido: ");
		lblApellido.setBounds(226, 11, 53, 14);
		panel.add(lblApellido);
		
		ape = new JTextField();
		ape.setBounds(320, 6, 134, 20);
		panel.add(ape);
		ape.setColumns(10);
		
		nac = new JTextField();
		nac.setBounds(82, 36, 134, 20);
		panel.add(nac);
		nac.setColumns(10);
		
		JLabel lblNacimiento = new JLabel("Nacimiento:");
		lblNacimiento.setBounds(10, 41, 70, 14);
		panel.add(lblNacimiento);
		
		dom = new JTextField();
		dom.setBounds(320, 36, 134, 20);
		panel.add(dom);
		dom.setColumns(10);
		
		JLabel lblDomicilio = new JLabel("Domicilio: ");
		lblDomicilio.setBounds(226, 41, 61, 14);
		panel.add(lblDomicilio);
		
		JLabel lblMail = new JLabel("Mail: ");
		lblMail.setBounds(10, 101, 46, 14);
		panel.add(lblMail);
		
		mail = new JTextField();
		mail.setBounds(82, 96, 372, 20);
		panel.add(mail);
		mail.setColumns(10);
		
		us = new JTextField();
		us.setBounds(82, 66, 134, 20);
		panel.add(us);
		us.setColumns(10);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(10, 71, 53, 14);
		panel.add(lblUsuario);
		
		lblContrasena = new JLabel("Contrase\u00F1a: ");
		lblContrasena.setBounds(226, 71, 77, 14);
		panel.add(lblContrasena);
		
		pass = new JPasswordField();
		pass.setBounds(320, 66, 134, 20);
		panel.add(pass);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(82, 190, 99, 23);
		panel.add(btnCancelar);
		
		btnCrear = new JButton("Crear");
		btnCrear.setBounds(355, 190, 99, 23);
		panel.add(btnCrear);
		
		JLabel lblRoles = new JLabel("Roles:");
		lblRoles.setBounds(10, 131, 46, 14);
		panel.add(lblRoles);
		
		rdbtnAdministrador = new JRadioButton("Administrador");
		rdbtnAdministrador.setBounds(82, 126, 109, 23);
		panel.add(rdbtnAdministrador);
		
		rdbtnOperador = new JRadioButton("Operador");
		rdbtnOperador.setBounds(226, 126, 109, 23);
		panel.add(rdbtnOperador);
		
		rdbtnVendedor = new JRadioButton("Vendedor");
		rdbtnVendedor.setBounds(82, 156, 109, 23);
		panel.add(rdbtnVendedor);
		
		rdbtnAgenteComercial = new JRadioButton("Agente Comercial");
		rdbtnAgenteComercial.setBounds(226, 156, 142, 23);
		panel.add(rdbtnAgenteComercial);
		JButton btnValidarDni = new JButton("Validar DNI");
		btnValidarDni.setBounds(332, 10, 130, 23);
		contentPane.add(btnValidarDni);
		btnValidarDni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				u=LogInControlador.getInstancia().buscarUsuarioPorDni(dni.getText());
				if(u!=null){
					existeUsuario=true;
					nom.setText(u.getNombre());
					ape.setText(u.getApellido());
					nac.setText(u.getFechaNac().toString());
					mail.setText(u.getMail());
					dom.setText(u.getDom());
					us.setText(u.getNombreUsuario());
					pass.setText(u.getPassword());
					Vector<Rol> r=u.getVectorEmpleados();
					for(int i=0; i<r.size(); i++){
						if(r.elementAt(i).getDescripcion().equals("Administrador"))
							rdbtnAdministrador.setSelected(true);
						else{
							if(r.elementAt(i).getDescripcion().equals("Operador"))
								rdbtnOperador.setSelected(true);
							else
								if(r.elementAt(i).getDescripcion().equals("Vendedor"))
									rdbtnVendedor.setSelected(true);
								else
									rdbtnAgenteComercial.setSelected(true);
						}
					}
				}
				else{
					existeUsuario=false;
					vaciarCampos();
				}
				//JOptionPane.showMessageDialog(null, "EMPLEADO BUSCADO");
				panel.setVisible(true);
				btnCrear.setVisible(true);
			}
		});
		btnCrear.setVisible(false);
		btnCrear.addActionListener(new ActionListener() {
			@SuppressWarnings({ "deprecation" })
			public void actionPerformed(ActionEvent e) {
				/* Si existe el usuario como cliente, le asigno roles de empleado, 
				 * Si existe el usuario como empleado, mensaje que dice que hay que modificarlo, no crearlo
				 */
				if(existeUsuario){
					Rol c = u.getCliente();
					Vector<Rol> rolesEmp = u.getVectorEmpleados();
					if(c!=null && rolesEmp.isEmpty()){
						if(rdbtnAdministrador.isSelected())
							LogInControlador.getInstancia().crearAdmin(u);
						if(rdbtnOperador.isSelected())
							LogInControlador.getInstancia().crearOp(u);
						if(rdbtnVendedor.isSelected())
							LogInControlador.getInstancia().crearVendedor(u);
						if(rdbtnAgenteComercial.isSelected())
							LogInControlador.getInstancia().crearAgenteComercial(u);
					}
					else if(!rolesEmp.isEmpty()){
						JOptionPane.showMessageDialog(null, "Ya existe un empleado con ese DNI."
								+ " Modificarlo o borrarlo.");
					}
					
				}
				else{
					/* No existe el usuario, lo construyo*/
					if(rdbtnAdministrador.isSelected())
						vRolesString.add("Administrador");
					if(rdbtnOperador.isSelected())
						vRolesString.add("Operador");
					if(rdbtnVendedor.isSelected())
						vRolesString.add("Vendedor");
					if(rdbtnAgenteComercial.isSelected())
						vRolesString.add("Agente Comercial");
					LogInControlador.getInstancia().altaEmpleado(mail.getText(), us.getText(), pass.getText(),
							nom.getText(), dom.getText(), dni.getText(), ape.getText(), nac.getText(),
							vRolesString);
				}
				
				vaciarCampos();
				reiniciarComponentes();
				dispose();
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reiniciarComponentes();
				dispose();				
			}
		});
	}
	private void reiniciarComponentes() {
		// TODO Auto-generated method stub
		if(instancia!=null){
			dni.setText("");
			panel.setVisible(false);
		}
		
	}

	private void vaciarCampos(){
		mail.setText("");
		us.setText("");
		pass.setText("");
		nom.setText("");
		dom.setText("");							
		ape.setText("");
		nac.setText("");
		rdbtnAdministrador.setSelected(false);
		rdbtnOperador.setSelected(false);
		rdbtnVendedor.setSelected(false);
		rdbtnAgenteComercial.setSelected(false);
		
	}
}
