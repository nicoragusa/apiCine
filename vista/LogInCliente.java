package vista;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controlador.LogInControlador;
import javax.swing.SwingConstants;

public class LogInCliente extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private static LogInCliente instancia=null;
	
	public static LogInCliente getInstancia()
	{
		if (instancia == null)
			instancia = new LogInCliente();
		return instancia;
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogInCliente frame = new LogInCliente();
					frame.setLocationRelativeTo(null);
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
	public LogInCliente() {
		setTitle("Iniciar Sesi\u00F3n");
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 403, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(131, 84, 204, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(131, 135, 204, 20);
		contentPane.add(passwordField);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUsuario.setBounds(58, 87, 63, 14);
		contentPane.add(lblUsuario);
		
		JLabel lblContrasena = new JLabel("Contrase\u00F1a:");
		lblContrasena.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblContrasena.setBounds(58, 138, 72, 14);
		contentPane.add(lblContrasena);
		
		JButton btnIniciarSesion = new JButton("Iniciar Sesi\u00F3n");
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				@SuppressWarnings("deprecation")
				String us=textField.getText(),pass=passwordField.getText();
				if(us.isEmpty() || pass.isEmpty())
					JOptionPane.showMessageDialog(null, "ERROR: COMPLETE LOS CAMPOS PARA INGRESAR");
				else{
					if(LogInControlador.getInstancia().IniciarSesionCliente(us, pass)){
						MenuCliente.getInstancia().setLocationRelativeTo(null);
						MenuCliente.getInstancia().setVisible(true);
						JOptionPane.showMessageDialog(null, "INICIANDO SESION COMO CLIENTE");
					}
					else
						JOptionPane.showMessageDialog(null, "ERROR AL INICIAR SESION COMO CLIENTE");
				}
			}
		});
		btnIniciarSesion.setBounds(216, 197, 119, 35);
		contentPane.add(btnIniciarSesion);
		
		JButton btnCrearCuenta = new JButton("Crear cuenta");
		btnCrearCuenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				CrearCliente.getInstancia().setVisible(true);
				CrearCliente.getInstancia().setLocationRelativeTo(null);
			}
		});
		btnCrearCuenta.setBounds(58, 197, 119, 35);
		contentPane.add(btnCrearCuenta);
		
		JLabel lblCine = new JLabel("CINE");
		lblCine.setHorizontalAlignment(SwingConstants.CENTER);
		lblCine.setFont(new Font("ISOCPEUR", Font.PLAIN, 33));
		lblCine.setBounds(58, 11, 277, 51);
		contentPane.add(lblCine);
	}
}
