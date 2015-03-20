package pnl.filtro.dinamico;

import pnl.modelo.Filtro;

public class FiltroValorDefault implements Comparable<FiltroValorDefault>{ 
	
	private Filtro filtro;
	private String valor;
	
	
	public FiltroValorDefault(Filtro filtro,String valor) {
		super();
		this.filtro = filtro;
		this.valor = valor;
	}




	public Filtro getFiltro() {
		return filtro;
	}








	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}




	public String getValor() {
		return valor;
	}


	public void setValor(String valor) {
		this.valor = valor;
	}


	@Override
	public int compareTo(FiltroValorDefault o) {
		// TODO Auto-generated method stub
		int compareIndice = ((FiltroValorDefault) o).getFiltro().getIndiceFiltro().intValue(); 
		return getFiltro().getIndiceFiltro().intValue() - compareIndice ;
	
	}

	
	
}


