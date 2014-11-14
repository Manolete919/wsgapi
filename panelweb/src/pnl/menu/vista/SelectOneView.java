package pnl.menu.vista;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import pnl.servicio.Tema;
import pnl.servicio.ThemeService;
 

 
@ManagedBean
public class SelectOneView {
     
    private String option;  
    private Theme theme;
    private List<Theme> themes;
  
     
    @ManagedProperty("#{themeService}")
    private ThemeService service;
    
    @ManagedProperty("#{tema}")
    private Tema tema;
     
    @PostConstruct
    public void init() {
    	option = tema.getTema();
        themes = service.getThemes();
    }
 
    public String getOption() {
        return option;
    }
 
    public void setOption(String option) {
        this.option = option;
    }
 
    public Theme getTheme() {
        return theme;
    }
 
    public void setTheme(Theme theme) {
        this.theme = theme;
    }
 
    public List<Theme> getThemes() {
        return themes;
    }
 
    public void setService(ThemeService service) {
        this.service = service;
    }
    
    public void guardarCambios(){
    	
    	tema.setTema(this.getOption());
    	System.out.println("xx " + tema.getTema());
    }

	public void setTema(Tema tema) {
		this.tema = tema;
	}



    
    
}