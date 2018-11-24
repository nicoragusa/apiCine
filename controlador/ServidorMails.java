package controlador;

import java.util.Vector;

import javax.swing.JOptionPane;

import negocio.ObserverTDA;
import negocio.Online;

public class ServidorMails implements ObserverTDA {
	private Vector<Online> ventas;
	private static ServidorMails instancia=null;
	
	private ServidorMails(){
		ventas=new Vector<Online>();
	}
	
	public static ServidorMails getInstancia(){
		if(instancia==null)
			instancia=new ServidorMails();
		return instancia;
	}
	@Override
	public void recibirOnline(Online o) {
		// TODO Auto-generated method stub
		enviarMail();
	}
	
	public void enviarMail(){
		JOptionPane.showMessageDialog(null, "Mail Enviado");
	}

}
