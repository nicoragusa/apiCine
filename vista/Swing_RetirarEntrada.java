package vista;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controlador.TerminalEntradas;
import negocio.Online;

public class Swing_RetirarEntrada extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static Swing_RetirarEntrada instancia;
	private JTextField codEntr;

	public static Swing_RetirarEntrada  getInstancia() {
		// TODO Auto-generated method stub
		if(instancia==null)
			instancia=new Swing_RetirarEntrada();
		return instancia;
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Swing_RetirarEntrada frame = new Swing_RetirarEntrada();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Swing_RetirarEntrada() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 471, 365);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblIngreseCdigo = new JLabel("Ingrese c\u00F3digo:");
		lblIngreseCdigo.setBounds(32, 61, 111, 14);
		contentPane.add(lblIngreseCdigo);
		
		codEntr = new JTextField();
		codEntr.setBounds(125, 58, 143, 20);
		contentPane.add(codEntr);
		codEntr.setColumns(10);
		
		JButton btnImprimirEntradas = new JButton("Imprimir Entradas");
		btnImprimirEntradas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Online v=TerminalEntradas.getInstancia().buscarVenta(Integer.parseInt(codEntr.getText()));
				if(v!=null){
					v.actualizarEstado();
					JOptionPane.showMessageDialog(null, "IMPRIMIENDO ENTRADAS");
				}
				
				else{
					JOptionPane.showMessageDialog(null, "CODIGO INCORRECTO");
				};
			}
		});
		btnImprimirEntradas.setBounds(278, 57, 143, 23);
		contentPane.add(btnImprimirEntradas);
	}
}
