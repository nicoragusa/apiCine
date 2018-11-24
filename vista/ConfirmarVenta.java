package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Controlador_Cine;
import controlador.VentaControlador;
import view.Venta_View;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.awt.event.ActionEvent;

public class ConfirmarVenta extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField pelicula;
	private JTextField cantEntradas;
	private JTextField fecha;
	private JTextField formaPago;
	private JTextField total;
	private static ConfirmarVenta instancia=null;

	
	private static Venta_View v;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfirmarVenta frame = new ConfirmarVenta();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public static ConfirmarVenta  getInstancia() {
		// TODO Auto-generated method stub
		if(instancia==null)
			instancia=new ConfirmarVenta ();
		return instancia;
	}
	/**
	 * Create the frame.
	 */
	public ConfirmarVenta() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDetalleEntradas = new JLabel("Detalle entradas:");
		lblDetalleEntradas.setBounds(24, 11, 137, 14);
		contentPane.add(lblDetalleEntradas);
				
		JLabel lblPelicula = new JLabel("Pelicula: ");
		lblPelicula.setBounds(24, 39, 68, 14);
		contentPane.add(lblPelicula);
		
		JLabel lblCantidad = new JLabel("Cantidad:");
		lblCantidad.setBounds(252, 11, 59, 14);
		contentPane.add(lblCantidad);
		
		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setBounds(24, 88, 52, 14);
		contentPane.add(lblFecha);
		
		JLabel lblFormaDePago = new JLabel("Forma de pago:");
		lblFormaDePago.setBounds(24, 131, 96, 14);
		contentPane.add(lblFormaDePago);
		
		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setBounds(252, 88, 52, 14);
		contentPane.add(lblTotal);
		
		JLabel lblPromociones = new JLabel("Promociones:");
		lblPromociones.setBounds(24, 166, 96, 14);
		contentPane.add(lblPromociones);

////////////////////////////////////////////////////////////////////////////////////
		pelicula = new JTextField();
		pelicula.setEditable(false);
		pelicula.setBounds(75, 36, 338, 20);
		contentPane.add(pelicula);
		pelicula.setColumns(10);
		
		cantEntradas = new JTextField();
		cantEntradas.setEditable(false);
		cantEntradas.setBounds(306, 8, 40, 20);
		contentPane.add(cantEntradas);
		cantEntradas.setColumns(10);
		
		fecha = new JTextField();
		fecha.setEditable(false);
		fecha.setBounds(75, 85, 166, 20);
		contentPane.add(fecha);
		fecha.setColumns(10);
		
		formaPago = new JTextField();
		formaPago.setEditable(false);
		formaPago.setBounds(112, 128, 171, 20);
		contentPane.add(formaPago);
		formaPago.setColumns(10);
		
		total = new JTextField();
		total.setEditable(false);
		total.setBounds(285, 85, 75, 20);
		contentPane.add(total);
		total.setColumns(10);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancelar.setBounds(24, 227, 89, 23);
		contentPane.add(btnCancelar);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentaControlador.getInstancia().crearVenta(v);
				JOptionPane.showMessageDialog(null, "Venta creada");
				dispose();
			}
		});
		btnConfirmar.setBounds(285, 227, 89, 23);
		contentPane.add(btnConfirmar);
		
		cargarTextFields();
	}
/*
 ***********************************************************
 *						NEGOCIO							   *
 ***********************************************************/
	public static void setVenta(Venta_View venta) {
		v=venta;
	}

	public void cargarTextFields(){
		pelicula.setText(v.getFuncion().getP().getNombreIdioma());
		cantEntradas.setText(String.valueOf(v.getCantidadEntradas()));
		fecha.setText(v.getFuncion().getDia() +": "+v.getFuncion().getHorario());
		formaPago.setText(v.getFormaPago().getResumenDePago());
		total.setText(String.valueOf(v.getTotal()));
	}
}
