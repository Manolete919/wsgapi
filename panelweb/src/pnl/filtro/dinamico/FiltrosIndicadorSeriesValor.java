package pnl.filtro.dinamico;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pnl.modelo.IndicadorSerie;

public class FiltrosIndicadorSeriesValor {
	
	private IndicadorSerie indicadorSerie;
	private List<FiltroValorDefault> filtroValorDefaults;
	public FiltrosIndicadorSeriesValor(IndicadorSerie indicadorSerie,
			List<FiltroValorDefault> filtroValorDefaults) {
		super();
		this.indicadorSerie = indicadorSerie;
		this.filtroValorDefaults = filtroValorDefaults;
	}
	public IndicadorSerie getIndicadorSerie() {
		return indicadorSerie;
	}
	public void setIndicadorSerie(IndicadorSerie indicadorSerie) {
		this.indicadorSerie = indicadorSerie;
	}
	public List<FiltroValorDefault> getFiltroValorDefaults() {
		
		List<FiltroValorDefault> filtroValorDefaults2 = new ArrayList<FiltroValorDefault>();
		
		if(!filtroValorDefaults.isEmpty()){
			
			FiltroValorDefault[] arr = filtroValorDefaults.toArray(new FiltroValorDefault[filtroValorDefaults.size()]);
			
			//hace un sort a la lista
			Arrays.sort(arr);
			
			filtroValorDefaults2 = new ArrayList<FiltroValorDefault>();
			for(int x=0; x<arr.length; x++ ){
				filtroValorDefaults2.add(arr[x]);
			}
			
			
			
		}
		
		return filtroValorDefaults2;

	}
	
	
	
	
}
