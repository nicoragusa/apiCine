package view;

import negocio.TarjetaCredito;

public class FormaDePago_View{
	private String opcionDePago;
	private String titular, nro,codSeg, banco,tipoTarjeta;
	private int anio,mes;
	
	public FormaDePago_View(String titular, String nro, String codSeg, String banco, int anio, int mes,String tipoTarjeta) {
		super();
		opcionDePago="Tarjeta de Credito";
		this.titular = titular;
		this.nro = nro;
		this.codSeg = codSeg;
		this.banco=banco;
		this.anio = anio;
		this.mes = mes;
		this.tipoTarjeta=tipoTarjeta;
	}
	
	public FormaDePago_View(){
		opcionDePago="Efectivo";
	}
	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public String getNro() {
		return nro;
	}

	public void setNro(String nro) {
		this.nro = nro;
	}

	public String getCodSeg() {
		return codSeg;
	}

	public void setCodSeg(String codSeg) {
		this.codSeg = codSeg;
	}

	public int getAnioo() {
		return anio;
	}

	public void setAnioo(int anio) {
		this.anio = anio;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public String getOpcionDePago() {
		return opcionDePago;
	}

	public void setOpcionDePago(String opcionDePago) {
		this.opcionDePago = opcionDePago;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getUltimosDigitos() {
		// TODO Auto-generated method stub
		return nro.substring(nro.length()-4);
	}

	public String getResumenDePago() {
		// TODO Auto-generated method stub
		if(opcionDePago.equals("Tarjeta de credito"))
			return getTipoTarjeta() +" terminada en "+getUltimosDigitos();
		
		return opcionDePago;
	}

	public TarjetaCredito toTarjeta() {
		// TODO Auto-generated method stub
		
		TarjetaCredito t=new TarjetaCredito();
		t.setCodigoSeguridad(codSeg);
		t.setNumero(nro);
		t.setTitular(titular);
		t.setVencimiento(mes,anio);
		t.setEntidadBancaria(banco);
		t.setTipoTarjeta(tipoTarjeta);
		return t;
	}

	public boolean sosTarjeta() {
		// TODO Auto-generated method stub
		return anio!=0;
	}
	
	public boolean sosEfectivo(){
		return !sosTarjeta();
	}

	public String getTipoTarjeta() {
		return tipoTarjeta;
	}

	public void setTipoTarjeta(String tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}
	
	
}
