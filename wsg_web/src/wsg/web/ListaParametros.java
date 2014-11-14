package wsg.web;

import java.util.ArrayList;
import java.util.List;

public class ListaParametros {
	
	

	public ListaParametros() {
		parametros = new ArrayList<String>();
	}

	List<String> parametros;

	public List<String> getParametros() {
		return parametros;
	}

	public void setParametros(List<String> parametros) {
		this.parametros = parametros;
	}
	
	
}
