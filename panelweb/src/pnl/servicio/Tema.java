package pnl.servicio;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="tema", eager = true)
@SessionScoped
public class Tema {
	
	private String tema;
	
	@PostConstruct
	public void init(){
		tema="black-tie";
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}
		

}
