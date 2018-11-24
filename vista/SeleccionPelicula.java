package vista;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JSeparator;
import javax.swing.ListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;

import controlador.Controlador_Cine;
import negocio.Cine;
import negocio.Funcion;
import negocio.Operador;
import negocio.Pelicula;
import persistencia.AdmPersistenciaCine;
import persistencia.AdmPersistenciaPelicula;
import persistencia.AdmPersistenciaSala;
import persistencia.AdmPersistenciaUsuario;
import view.Cine_View;
import view.Funcion_View;
import view.Pelicula_View;
import view.Venta_View;

import javax.swing.event.ListSelectionEvent;


public class SeleccionPelicula extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static SeleccionPelicula instancia;
	private JButton btnSiguiente;
	private Vector<Pelicula_View>pView;
	private Vector<Funcion_View>fView;
	private JComboBox<String> listaEstablecimientos;
	private JComboBox<String> listaPeliculas;
	private JComboBox<String> listaDias;
	private JComboBox<String> listaHorarios;
	private Vector<String>dias;
	private Venta_View venta=new Venta_View();

	public static SeleccionPelicula getInstancia()
	{
		if (instancia == null){
			instancia = new SeleccionPelicula();
		}
		return instancia;
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SeleccionPelicula frame = new SeleccionPelicula();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					SeleccionPelicula.getInstancia();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public SeleccionPelicula() {

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		setTitle("Venta de entradas-Seleccion de pelicula");
		setBounds(100, 100, 548, 382);

		JLabel lblEstablecimiento = new JLabel("Establecimiento:");
		lblEstablecimiento.setBounds(20, 30, 97, 14);
		getContentPane().add(lblEstablecimiento);
		
		JLabel lblPelicula = new JLabel("Pelicula: ");
		lblPelicula.setBounds(20, 91, 97, 14);
		getContentPane().add(lblPelicula);
		
		JLabel lblDia = new JLabel("Dia: ");
		lblDia.setBounds(104, 194, 97, 14);
		getContentPane().add(lblDia);
		
		JLabel lblHorario = new JLabel("Horario:");
		lblHorario.setBounds(349, 194, 46, 14);
		getContentPane().add(lblHorario);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(59, 297, 127, 38);
		getContentPane().add(btnCancelar);
		
		JButton btnSiguiente = new JButton("Siguiente");
		btnSiguiente.setEnabled(false);
		
		btnSiguiente.setBounds(328, 297, 127, 38);
		getContentPane().add(btnSiguiente);

		listaEstablecimientos = new JComboBox<String>();
		listaEstablecimientos.setBounds(127, 30, 252, 20);
		getContentPane().add(listaEstablecimientos);

	    listaPeliculas = new JComboBox<String>();
		listaPeliculas.setBounds(127, 88, 379, 20);
		getContentPane().add(listaPeliculas);
		
		listaDias = new JComboBox<String>();
		listaDias.setBounds(35, 219, 164, 20);
		getContentPane().add(listaDias);
		
		listaHorarios = new JComboBox<String>();
		listaHorarios.setBounds(312, 219, 115, 20);
		getContentPane().add(listaHorarios);
		
		JButton btnBuscarFuncion = new JButton("Buscar Funcion");
		btnBuscarFuncion.setBounds(185, 140, 143, 23);
		getContentPane().add(btnBuscarFuncion);
		
		/////////////////////////////////////////////////////////////////
		llenarListaEstablecimientos();
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});	
		
		btnSiguiente.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				SeleccionAsientos.setVenta(venta);
				SeleccionAsientos.getInstancia().setVisible(true);
				SeleccionAsientos.getInstancia().setLocationRelativeTo(null);
				dispose();
			}			
		});
		
		btnBuscarFuncion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dias=new Vector<String>();
				llenarListaDias();
			}
		});
		
		listaEstablecimientos.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(listaEstablecimientos.getSelectedIndex()<=0){
					btnSiguiente.setEnabled(false);
				}
				else{
					//Se deberia buscar las peliculas de ese cine, pero mostramos todos
					llenarListaPeliculas();
				}
			}
		});
		
		listaPeliculas.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(listaPeliculas.getSelectedIndex()<=0){
					btnSiguiente.setEnabled(false);
				}
				
				else{
					//recupero los datos
					String cine=(String) listaEstablecimientos.getSelectedItem();
					int pos=listaPeliculas.getSelectedIndex();
					Pelicula_View pelicula=pView.get(pos-1);
					
					//busco funciones que tengas estos datos
					fView=Controlador_Cine.getInstanciaCine().getFuncionesView(cine, pelicula);
										
				}
			}

		});
		
		listaDias.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(listaDias.getSelectedIndex()<=0){
					listaHorarios.removeAllItems();
					btnSiguiente.setEnabled(false);
				}
				else{
					llenarListaHorarios();
				}
				
			}
			
		});
		
		listaHorarios.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				buscarFuncion();
				btnSiguiente.setEnabled(true);
				
			}

			private void buscarFuncion() {
				// TODO Auto-generated method stub
				String dia,horario;
				dia=(String)listaDias.getSelectedItem();
				horario=(String)listaHorarios.getSelectedItem();
				
				for(Funcion_View f:fView){
					if(f.getDia().equals(dia)&&f.getHorario().equals(horario)){
						venta.setFuncion(f);
						venta.setEstablecimiento((String)listaEstablecimientos.getSelectedItem());
					}
				}
				
			}
		});
}
		
	private void llenarListaEstablecimientos() {
		Vector<Cine_View>establecimientos=Controlador_Cine.getInstanciaCine().getCines();
		listaEstablecimientos.addItem("-");
		for(Cine_View c:establecimientos)
			listaEstablecimientos.addItem(c.getNombre());
	}
	
	private void llenarListaPeliculas() {
		pView=Controlador_Cine.getInstanciaCine().getPeliculas();
		listaPeliculas.addItem("-");
		for(Pelicula_View p:pView)
			listaPeliculas.addItem(p.getNombreIdioma());
	}
	
	private void llenarListaDias() {
		dias=new Vector<String>();
		//System.out.println("dias tiene "+dias.size()+" elementos");
		listaDias.addItem("-");
		for(Funcion_View f:fView){
			
			String dia=f.getDia();
			
			if(!dias.contains(dia)){
				dias.add(dia);
				listaDias.addItem(dia);
				System.out.println("agregando "+dia);
			}
			
		}
	}
	
	private void llenarListaHorarios(){
		String diaSeleccionado=(String)listaDias.getSelectedItem();
		listaHorarios.addItem("-");
		for(Funcion_View f:fView)
			if(f.getDia().equals(diaSeleccionado))
			listaHorarios.addItem(f.getHorario());
	}
}
