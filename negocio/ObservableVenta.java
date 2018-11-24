package negocio;


public interface ObservableVenta {//clase que avisa a todos sus observers
//	static ArrayList<ObserverTDA> observadores=new ArrayList<ObserverTDA>();
	
	public static void add(ObserverTDA vr) {}
	
	public  static void remove(ObserverTDA vr){}

	public static void notifyAll(Online o){}
	 
}
