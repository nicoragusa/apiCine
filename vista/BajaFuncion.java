package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
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
import negocio.Cine;
import persistencia.AdmPersistenciaCine;
import view.Cine_View;

public class BajaFuncion extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static BajaFuncion instancia;
	private JPanel contentPane;
	private JTextField textFieldSala;
	private JTextField textFieldHorario;
	private JTextField textFieldDia;
	private Vector<Cine_View> cv;
	
	public static BajaFuncion getInstancia() {
		if (instancia == null)
			instancia = new BajaFuncion();
		return instancia;
	}
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	private BajaFuncion() {
		setTitle("Baja Funcion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 254);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel etiqCine = new JLabel("Cine:");
		etiqCine.setBounds(30, 35, 46, 14);
		contentPane.add(etiqCine);
		
		JComboBox<String> comboBoxCine = new JComboBox<String>();
		comboBoxCine.setBounds(86, 30, 308, 20);
		contentPane.add(comboBoxCine);
		cv = AdmPersistenciaCine.getInstancia().select1();
		for (int i = 0; i < cv.size(); i++) {
			comboBoxCine.addItem(cv.elementAt(i).getNombre());
		}
		
		JLabel etiqSala = new JLabel("Sala:");
		etiqSala.setBounds(30, 85, 46, 14);
		contentPane.add(etiqSala);
		
		textFieldSala = new JTextField();
		textFieldSala.setBounds(86, 80, 121, 20);
		contentPane.add(textFieldSala);
		textFieldSala.setColumns(10);
		
		JLabel etiqHorario = new JLabel("Horario:");
		etiqHorario.setBounds(217, 85, 58, 14);
		contentPane.add(etiqHorario);
		
		textFieldHorario = new JTextField();
		textFieldHorario.setBounds(273, 80, 121, 20);
		contentPane.add(textFieldHorario);
		textFieldHorario.setColumns(10);
		
		JLabel etiqDia = new JLabel("Dia:");
		etiqDia.setBounds(30, 135, 46, 14);
		contentPane.add(etiqDia);
		
		textFieldDia = new JTextField();
		textFieldDia.setBounds(86, 130, 121, 20);
		contentPane.add(textFieldDia);
		textFieldDia.setColumns(10);
		
		JButton botonEliminarFuncion = new JButton("Eliminar");
		botonEliminarFuncion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Cine c = Controlador_Cine.getInstanciaCine()
						.buscarCineNombre((String) comboBoxCine.getSelectedItem());
				
				boolean res=c.bajaFuncion(textFieldSala.getText(), LocalTime.parse(textFieldHorario.getText()),
						LocalDate.parse(textFieldDia.getText()));
				if(res)
					JOptionPane.showMessageDialog(null, "Funcion eliminada");
				else
					JOptionPane.showMessageDialog(null, "No se puede eliminar la funcion");
				dispose();
			}
		});
		botonEliminarFuncion.setBounds(273, 130, 121, 47);
		contentPane.add(botonEliminarFuncion);
	}

}
