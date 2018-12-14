package vista;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;

import controlador.LogInControlador;
import view.FormaDePago_View;
import view.Venta_View;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.awt.event.ActionEvent;

public class SeleccionFormaDePago extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nro;
	private JTextField cod;
	private static SeleccionFormaDePago instancia=null;
	private JButton btnConfirmar;
	JMonthChooser monthChooser;
	JYearChooser yearChooser;
	private Venta_View v;
	JPanel panel;
	private JTextField banco;
	private JTextField titular;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SeleccionFormaDePago frame = new SeleccionFormaDePago();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public static SeleccionFormaDePago getInstancia(){
		if(instancia==null)
			instancia=new SeleccionFormaDePago();
		return instancia;
	}
	

	
	public void setVenta(Venta_View v) {
		// TODO Auto-generated method stub
		this.v=v;
	}
	
	/**
	 * Create the frame.
	 */
	public SeleccionFormaDePago() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 357);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblFormaDePago = new JLabel("Forma de pago: ");
		lblFormaDePago.setBounds(10, 21, 106, 14);
		contentPane.add(lblFormaDePago);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(100, 18, 162, 20);
		comboBox.addItem("-");
		if(!LogInControlador.getInstancia().getRol().sosElCliente())
			comboBox.addItem("Efectivo");
		comboBox.addItem("Tarjeta de credito");
		contentPane.add(comboBox);
		
		panel = new JPanel();
		panel.setBounds(10, 46, 414, 150);
		contentPane.add(panel);
		panel.setLayout(null);
		panel.setVisible(false);
		
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnConfirmar.setEnabled(true);
				if(comboBox.getSelectedItem().equals("Tarjeta de credito"))
					panel.setVisible(true);
				else{ 
					panel.setVisible(false);
					if(comboBox.getSelectedItem().equals("-"))
						btnConfirmar.setEnabled(false);
				}	
			}
		});
		JLabel lblNroTarjeta = new JLabel("Nro Tarjeta: ");
		lblNroTarjeta.setBounds(10, 11, 91, 14);
		panel.add(lblNroTarjeta);
		
		nro = new JTextField();
		nro.setBounds(119, 8, 173, 20);
		panel.add(nro);
		nro.setColumns(10);
		
		monthChooser = new JMonthChooser();
		monthChooser.setBounds(92, 88, 116, 20);
		panel.add(monthChooser);
		
		yearChooser = new JYearChooser();
		yearChooser.setBounds(218, 88, 47, 20);
		panel.add(yearChooser);
		
		JLabel lblVencimiento = new JLabel("Vencimiento:");
		lblVencimiento.setBounds(10, 94, 91, 14);
		panel.add(lblVencimiento);
		
		cod = new JTextField();
		cod.setBounds(108, 122, 54, 20);
		panel.add(cod);
		cod.setColumns(10);
		
		JLabel lblCodSeg = new JLabel("Cod Seguridad:");
		lblCodSeg.setBounds(10, 125, 146, 14);
		panel.add(lblCodSeg);
		
		JLabel lblEntidadBancaria = new JLabel("Entidad Bancaria:");
		lblEntidadBancaria.setBounds(10, 36, 116, 14);
		panel.add(lblEntidadBancaria);
		
		banco = new JTextField();
		banco.setBounds(119, 33, 173, 20);
		panel.add(banco);
		banco.setColumns(10);
		
		JLabel lblTitular = new JLabel("Titular:");
		lblTitular.setBounds(10, 61, 46, 14);
		panel.add(lblTitular);
		
		titular = new JTextField();
		titular.setBounds(119, 57, 173, 20);
		panel.add(titular);
		titular.setColumns(10);
		
		JComboBox<String> tipo = new JComboBox<String>();
		tipo.setBounds(288, 122, 99, 20);
		tipo.addItem("Visa");
		tipo.addItem("MasterCard");
		panel.add(tipo);
		
		JLabel lblTipoDeTarjeta = new JLabel("Tipo de tarjeta:");
		lblTipoDeTarjeta.setBounds(193, 125, 99, 14);
		panel.add(lblTipoDeTarjeta);
		
		 btnConfirmar = new JButton("Siguiente");
		 btnConfirmar.setEnabled(false);
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FormaDePago_View formaPago;
				if(comboBox.getSelectedItem().equals("Tarjeta de credito")){
					
					String bancoT=banco.getText();
					String titularT=titular.getText();
					String nroT=nro.getText();
					String codT=cod.getText();
					int mesT=monthChooser.getMonth(); 
					int anioT=yearChooser.getYear();
					String tipoTarjeta=(String) tipo.getSelectedItem();
					
					formaPago=new FormaDePago_View(titularT,nroT,codT,bancoT,anioT,mesT,tipoTarjeta);
					
				}else{
					formaPago=new FormaDePago_View();
				}
				v.setFormaPago(formaPago);
				ConfirmarVenta.setVenta(v);
				ConfirmarVenta.getInstancia().setLocationRelativeTo(null);
				ConfirmarVenta.getInstancia().setVisible(true);
				
				dispose();
			}
		});
		btnConfirmar.setBounds(234, 279, 99, 23);
		contentPane.add(btnConfirmar);
		
		JButton btnAtras = new JButton("Atras");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				SeleccionAsientos.getInstancia().setLocationRelativeTo(null);
				SeleccionAsientos.getInstancia().setVisible(true);
				dispose();
			}
		});
		btnAtras.setBounds(84, 279, 89, 23);
		contentPane.add(btnAtras);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 207, 414, 49);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblPromocin = new JLabel("Promoci\u00F3n:");
		lblPromocin.setBounds(10, 11, 84, 14);
		panel_1.add(lblPromocin);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(87, 8, 165, 20);
		panel_1.add(comboBox_1);
		this.setTitle("Venta de entradas-Forma de pago");
	}
}
