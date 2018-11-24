package vista;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Controlador_Cine;
import controlador.VentaControlador;
import negocio.*
;
import view.Venta_View;public class SeleccionAsientos extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel asientos;
	private static SeleccionAsientos instancia=null;
	
	//Vector de vecotres de JButton, es para crear la matriz de botones
	private Vector<Vector<JButton>>matrizAsientos;
	
	private JButton botonSeleccionado;
	
	//son los asientos vendidos de la funcion actual
	private Vector<AsientoVendido> ocupados;
	
	//matriz de coordenadas (fila,columna) de un asiento
	private Vector<AsientoFisico> asientosSeleccionados=new Vector<AsientoFisico>();
	
	static Venta_View v;
	
	private int capSala, columna=10;
	/**
	 * Launch the application.
	 */
	public static SeleccionAsientos getInstancia(){
		if(instancia==null)
			instancia=new SeleccionAsientos();
		return instancia;
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SeleccionAsientos frame = new SeleccionAsientos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void setVenta(Venta_View venta){
		v=venta;
	}
	
	/**
	 * Create the frame.
	 */
	public SeleccionAsientos() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 392);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCantidadDeEntradas = new JLabel("Cantidad de entradas");
		lblCantidadDeEntradas.setBounds(10, 31, 128, 14);
		contentPane.add(lblCantidadDeEntradas);
		this.setTitle("Venta de entradas-Seleccion de Asientos");
		asientos=new JPanel();
		asientos.setBounds(72, 110, 274, 179);
		contentPane.add(asientos);
		asientos.setLayout(null);
	
		
		JComboBox<Integer> comboBox = new JComboBox<Integer>();
		comboBox.setBounds(136, 28, 48, 20);
		contentPane.add(comboBox);
		
		JLabel lblAsientos = new JLabel("Asientos:");
		lblAsientos.setBounds(10, 73, 59, 14);
		contentPane.add(lblAsientos);
		
		JButton btnOcupados = new JButton("Ocupados");
		btnOcupados.setBackground(Color.GRAY);
		btnOcupados.setEnabled(false);
		btnOcupados.setBounds(83, 69, 101, 23);
		contentPane.add(btnOcupados);
		
		JButton btnSeleccionado = new JButton("Seleccionado");
		btnSeleccionado.setEnabled(false);
		btnSeleccionado.setBackground(Color.GREEN);
		btnSeleccionado.setBounds(235, 69, 111, 23);
		contentPane.add(btnSeleccionado);
		
		JButton btnSiguiente = new JButton("Siguiente");
		btnSiguiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//CANTIDAD DE ASIENTOS INDICADOS
				int cantAsientos=(int) comboBox.getSelectedItem(),cantSelect;
				//CANTIDAD DE ASIENTOS SELECCIONADOS, RECORRO LA MATRIZ
				cantSelect=getCantAsientos();
				
				if(cantAsientos!=cantSelect)
					JOptionPane.showMessageDialog(null, "Error, seleccione la cantidad de entradas indicadas");
				
				else{
					//Hago el pasa mano de la informacion recopilada en esta ventana y la anterior
					v.setAsientosSeleccionados(asientosSeleccionados);
					v.setCantidadEntradas((int)comboBox.getSelectedItem());
					v.setTotal(v.getCantidadEntradas()*Controlador_Cine.getPrecioEntradas());
					SeleccionFormaDePago.getInstancia().setVenta(v);	
					
					//visualizo la ventana siguiente
					SeleccionFormaDePago.getInstancia().setLocationRelativeTo(null);
					SeleccionFormaDePago.getInstancia().setVisible(true);
					SeleccionAsientos.getInstancia().setVisible(false);
				}
			}

			
		});
		btnSiguiente.setBounds(289, 319, 111, 23);
		contentPane.add(btnSiguiente);
		
		JButton btnAtras = new JButton("Atras");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SeleccionPelicula.getInstancia().setLocationRelativeTo(null);
				SeleccionPelicula.getInstancia().setVisible(true);
				dispose();
			}
		});
		btnAtras.setBounds(36, 319, 102, 23);
		contentPane.add(btnAtras);
		
		capSala=v.getFuncion().getS().getCapacidad();
	
		/*Obtengo las entradas vendidas*/
		ocupados=VentaControlador.getInstancia().getAsientosVendidos(v);
		
		/*
		 * Agrego una cantidad maxima de entradas, este varia segun la cantidad de asientos disponibles en la sala
		 */
		capSala=v.getFuncion().getS().getCapacidad();
		for(int i=1;i<=capSala;i++)
			comboBox.addItem(i);
		
		generarMatriz((capSala/columna),columna);
		agregarListeners(capSala/columna,columna);
		
	}
	
	private void agregarListeners(int f, int c){
		for(int i=0;i<f;i++){
			Vector<JButton>fila=matrizAsientos.get(i);
			for(int j=0;j<c;j++){
				botonSeleccionado=fila.get(j);
				botonSeleccionado.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg){
						JButton boton=(JButton)arg.getSource();
						if(boton.getBackground().equals(Color.GREEN)){
							boton.setBackground(new Color(238,238,238));
							for(int i=0;i<matrizAsientos.size();i++){
								Vector<JButton> linea=matrizAsientos.elementAt(i);
								int pos=linea.indexOf(boton);
								if(pos!=-1)
									borrarPosicion(i,pos);
									
								
							}
						}
						else{
							boton.setBackground(Color.GREEN);
							for(int i=0;i<matrizAsientos.size();i++){
								Vector<JButton> linea=matrizAsientos.elementAt(i);
								int pos=linea.indexOf(boton);
								if(pos!=-1){
									asientosSeleccionados.add(new AsientoFisico(i,pos));
								}
								
							}							
							
						}
					}

					
				});
			}
		}
	}
	
	private void borrarPosicion(int x, int pos) {
		// TODO Auto-generated method stub
		/*for(AsientoFisico a:asientosSeleccionados)
			if(a.sosElAsiento(i, pos))
				asientosSeleccionados.remove(a);*/
		for(int i=0;i<asientosSeleccionados.size();i++)
			if(asientosSeleccionados.get(i).sosElAsiento(x,pos))
				asientosSeleccionados.remove(i);
		
	}
	private void generarMatriz(int fila, int columna) {
		//cargo el panel
		matrizAsientos=new Vector<Vector<JButton>>();
		int fil=0;
		if(fila==0){
			Vector<JButton> linea=new Vector<JButton>();
			int col=0;
			for(int j=0;j<columna;j++){
				JButton boton=new JButton();
				boton.setBounds(col, fil, 29, 29);
				boton.setVisible(true);
				if(isOcupado(0,j)){
					boton.setEnabled(false);
					boton.setBackground(Color.GRAY);
				}
				else{
					boton.setBackground(new Color(238,238,238));
				}
				linea.add(boton);
				asientos.add(boton);
				col+=29;
			}
			matrizAsientos.add(linea);
		}
		for(int i=0;i<fila;i++){
			Vector<JButton> linea=new Vector<JButton>();
			int col=0;
			for(int j=0;j<columna;j++){
				JButton boton=new JButton();
				boton.setBounds(col, fil, 29, 29);
				boton.setVisible(true);
				if(isOcupado(i,j)){
					boton.setEnabled(false);
					boton.setBackground(Color.GRAY);
				}
				else{
					boton.setBackground(new Color(238,238,238));
				}
				linea.add(boton);
				asientos.add(boton);
				col+=29;
			}
			matrizAsientos.add(linea);
			fil+=29;
		}
	}
	
	private boolean isOcupado(int i, int j) {
		// TODO Auto-generated method stub
		for(AsientoVendido a:ocupados){
		 	if(a.getAsientoF().sosElAsiento(i,j))
		  		return a.isEstado();
		  }
		
		return false;
	}
	
	private int getCantAsientos() {
		// TODO Auto-generated method stub
		return asientosSeleccionados.size();
	}
}
