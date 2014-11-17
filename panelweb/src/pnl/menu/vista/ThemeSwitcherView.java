package pnl.menu.vista;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import pnl.servicio.ThemeService;

 
@ManagedBean
public class ThemeSwitcherView implements Serializable {
	private static final long serialVersionUID = 1L;
 
    private List<Theme> themes;
     
    @ManagedProperty("#{themeService}")
    private ThemeService service;
 
    @PostConstruct
    public void init() {
        themes = service.getThemes();
    }
     
    public List<Theme> getThemes() {
        return themes;
    }
 
    public void setService(ThemeService service) {
        this.service = service;
    }
}