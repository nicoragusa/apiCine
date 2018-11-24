package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controlador.Controlador_Cine;
import controlador.LogInControlador;
import negocio.Administrador;
import negocio.Usuario;

public class AltaEstablecimiento extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static AltaEstablecimiento instancia;
	private JPanel contentPane;
	private JTextField textFieldCuit;
	private JTextField textFieldNombre;
	private JTextField textFieldDomicilio;
	private JTextField textFieldCapTotal;
	
	public static AltaEstablecimiento getInstancia(){
		if(instancia==null)
			instancia=new AltaEstablecimiento();
		return instancia;
	}

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	private AltaEstablecimiento() {
		setTitle("Alta Cine");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 482, 317);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel etiqCuit = new JLabel("CUIT:");
		etiqCuit.setBounds(12, 100, 56, 16);
		contentPane.add(etiqCuit);

		textFieldCuit = new JTextField();
		textFieldCuit.setBounds(88, 98, 121, 19);
		contentPane.add(textFieldCuit);
		textFieldCuit.setColumns(10);

		JLabel etiqNombre = new JLabel("Nombre:");
		etiqNombre.setBounds(12, 20, 77, 16);
		contentPane.add(etiqNombre);

		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(88, 17, 364, 22);
		contentPane.add(textFieldNombre);
		textFieldNombre.setColumns(10);

		JLabel etiqDomicilio = new JLabel("Domicilio:");
		etiqDomicilio.setBounds(12, 60, 56, 16);
		contentPane.add(etiqDomicilio);

		textFieldDomicilio = new JTextField();
		textFieldDomicilio.setBounds(88, 57, 364, 22);
		contentPane.add(textFieldDomicilio);
		textFieldDomicilio.setColumns(10);

		JComboBox<String> comboBoxCantSalas = new JComboBox<String>();
		comboBoxCantSalas
				.setModel(new DefaultComboBoxModel<String>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
		comboBoxCantSalas.setMaximumRowCount(10);
		comboBoxCantSalas.setEditable(true);
		comboBoxCantSalas.setBounds(158, 137, 51, 22);
		contentPane.add(comboBoxCantSalas);

		JLabel etiqCantidadDeSalas = new JLabel("Cantidad de salas:");
		etiqCantidadDeSalas.setBounds(12, 140, 121, 16);
		contentPane.add(etiqCantidadDeSalas);

		JLabel etiqCapacidadTotal = new JLabel("Capacidad total:");
		etiqCapacidadTotal.setBounds(221, 100, 106, 16);
		contentPane.add(etiqCapacidadTotal);

		textFieldCapTotal = new JTextField();
		textFieldCapTotal.setBounds(336, 97, 116, 22);
		contentPane.add(textFieldCapTotal);
		textFieldCapTotal.setColumns(10);
		
		
		JButton botonCrearCine = new JButton("Crear Cine");
		botonCrearCine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Usuario u=LogInControlador.getInstancia().getUsuarioLogueado();
				Administrador ad = (Administrador) u.getAdministrador();
					
				Controlador_Cine.getInstanciaCine().altaCine(textFieldNombre.getText(),
						textFieldDomicilio.getText(), 
						Integer.parseInt( (String) comboBoxCantSalas.getSelectedItem()),
						Integer.parseInt(textFieldCapTotal.getText()), textFieldCuit.getText(), ad);
				JOptionPane.showMessageDialog(null, "Cine creado con exito");
				dispose();
			}
		});
		botonCrearCine.setBounds(277, 183, 121, 46);
		contentPane.add(botonCrearCine);
	}
}
