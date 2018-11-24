package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

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
import negocio.Cine;
import negocio.Usuario;
import persistencia.AdmPersistenciaCine;
import view.Cine_View;

public class AltaSala extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static AltaSala instancia;
	private JPanel contentPane;
	private JTextField textFieldNombre;
	private JTextField textFieldCapacidad;
	private Vector<Cine_View> cv;

	public static AltaSala getInstancia() {
		if (instancia == null)
			instancia = new AltaSala();
		return instancia;
	}

	private AltaSala() {
		setTitle("Alta Sala");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 412, 245);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel etiqCine = new JLabel("Cine:");
		etiqCine.setBounds(10, 30, 46, 14);
		contentPane.add(etiqCine);

		JComboBox<String> comboBoxCine = new JComboBox<String>();
		comboBoxCine.setBounds(83, 27, 282, 20);
		contentPane.add(comboBoxCine);
		cv = AdmPersistenciaCine.getInstancia().select1();
		for (int i = 0; i < cv.size(); i++) {
			comboBoxCine.addItem(cv.elementAt(i).getNombre());
		}

		JLabel etiqNombre = new JLabel("Nombre:");
		etiqNombre.setBounds(10, 80, 63, 14);
		contentPane.add(etiqNombre);

		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(83, 77, 282, 20);
		contentPane.add(textFieldNombre);
		textFieldNombre.setColumns(10);

		JLabel etiqCapacidad = new JLabel("Capacidad:");
		etiqCapacidad.setBounds(10, 130, 67, 14);
		contentPane.add(etiqCapacidad);

		textFieldCapacidad = new JTextField();
		textFieldCapacidad.setBounds(83, 127, 86, 20);
		contentPane.add(textFieldCapacidad);
		textFieldCapacidad.setColumns(10);

		JButton botonCrearSala = new JButton("Crear Sala");
		botonCrearSala.setBounds(249, 126, 116, 39);
		botonCrearSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Usuario u = LogInControlador.getInstancia().getUsuarioLogueado();
				
				Administrador ad = new Administrador(u);
				
				Cine c = Controlador_Cine.getInstanciaCine().
						buscarCineNombre((String) comboBoxCine.getSelectedItem());
				
				c.altaSala(textFieldNombre.getText(), Integer.parseInt(textFieldCapacidad.getText()), ad);
				JOptionPane.showMessageDialog(null, "Sala creada con exito");
				dispose();
			}
		});
		contentPane.add(botonCrearSala);
	}
}
