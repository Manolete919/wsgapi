package pnl.webservice.integracion;

import java.io.Serializable;


public class Generico implements Serializable {
	private static final long serialVersionUID = 1L;
	
	Object objetoX;
	int objetoY;
	public Generico(Object objetoX, int objetoY) {
		super();
		this.objetoX = objetoX;
		this.objetoY = objetoY;
	}
	public Object getObjetoX() {
		return objetoX;
	}
	public void setObjetoX(Object objetoX) {
		this.objetoX = objetoX;
	}
	public int getObjetoY() {
		return objetoY;
	}
	public void setObjetoY(int objetoY) {
		this.objetoY = objetoY;
	}
	
	
	
	

	
	

	
	
}
