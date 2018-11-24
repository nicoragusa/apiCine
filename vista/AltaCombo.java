package vista;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;

import negocio.Promocion;
import javax.swing.JList;

public class AltaCombo extends JFrame {

	private JPanel contentPane;
	private Vector<Promocion>promociones;
	JList<String> promoslista;
	JList<String> comboLista;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AltaCombo frame = new AltaCombo();
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
	public AltaCombo() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		promoslista = new JList<String>();
		promoslista.setBounds(10, 22, 133, 228);
		contentPane.add(promoslista);
		cargarPromos();
		
		comboLista = new JList<String>();
		comboLista.setBounds(283, 22, 133, 228);
		contentPane.add(comboLista);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				agregarAlCombo(promoslista.getSelectedValue());
				//int indice=promoslista.getSelectedIndex();
				
			}
			
		});
		btnAgregar.setBounds(160, 97, 89, 23);
		contentPane.add(btnAgregar);
		
		JButton btnQuitar = new JButton("Quitar");
		btnQuitar.setBounds(160, 142, 89, 23);
		contentPane.add(btnQuitar);
		
	}
	
	private void agregarAlCombo(String selectedValue) {
		/*DefaultListModel<String> dlm=new DefaultListModel<String>();
		dlm.addElement(selectedValue);
		comboLista.setModel(dlm);*/
		ListModel<String> list=comboLista.getModel();
		DefaultListModel<String> dlm2=(DefaultListModel<String>) list;
		if(dlm2==null)dlm2=new DefaultListModel<String>();
		dlm2.addElement(selectedValue);
	}
	
	private void cargarPromos() {
		DefaultListModel<String> dlm=new DefaultListModel<String>();
		dlm.addElement("asd");
		dlm.addElement("sad");
		promoslista.setModel(dlm);
	}
}
