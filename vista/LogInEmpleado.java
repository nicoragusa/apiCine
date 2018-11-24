package vista;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controlador.LogInControlador;
import negocio.Rol;
import negocio.Usuario;
public class LogInEmpleado extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static LogInEmpleado instancia=null;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static LogInEmpleado getInstancia(){
		if(instancia==null)
			instancia=new LogInEmpleado();
		return instancia;
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogInEmpleado frame = new LogInEmpleado();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LogInEmpleado() {
		setTitle("Iniciar Sesi\u00F3n");
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 401, 268);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsuario = new JLabel("Usuario: ");
		lblUsuario.setBounds(60, 52, 56, 14);
		contentPane.add(lblUsuario);
		
		textField = new JTextField();
		textField.setBounds(140, 49, 194, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblContrasena = new JLabel("Contraseña: ");
		lblContrasena.setBounds(60, 107, 72, 14);
		contentPane.add(lblContrasena);
		
		JButton btnIniciarSesion = new JButton("Iniciar Sesi\u00F3n");
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String us=textField.getText();
				@SuppressWarnings("deprecation")
				String pw=passwordField.getText();
				
				if(us.isEmpty()||pw.isEmpty())
					JOptionPane.showMessageDialog(null,"Complete los campos solicitados");
				
				else{
					Usuario user=LogInControlador.getInstancia().IniciarSesionEmpleado(us, pw);
					if(user!=null){
						Vector<Rol> vEmpleado=user.getVectorEmpleados();
						if(vEmpleado.size()>1){
						
							SeleccionRolEmpleado.getInstancia(vEmpleado);
							SeleccionRolEmpleado.getInstancia(vEmpleado).setLocationRelativeTo(null);
							SeleccionRolEmpleado.getInstancia(vEmpleado).setVisible(true);
							dispose();
						}
						else{
							SeleccionRolEmpleado.getInstancia(vEmpleado).mostrarRol(vEmpleado.get(0));
							dispose();
						}
						
					}
					else{
					 JOptionPane.showMessageDialog(null, "Credenciales incorrectas");
					}
						
				}
			}
		});
		btnIniciarSesion.setBounds(140, 161, 194, 28);
		contentPane.add(btnIniciarSesion);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(140, 104, 194, 20);
		contentPane.add(passwordField);
	}

}
