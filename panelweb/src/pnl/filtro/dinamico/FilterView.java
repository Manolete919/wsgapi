package pnl.filtro.dinamico;
import java.io.Serializable;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

 
@ManagedBean
@ViewScoped
public class FilterView implements Serializable {
     
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public boolean filterByName(Object value, Object filter, Locale locale) {
	    String filterText = (filter == null) ? null : filter.toString().trim();
	    if (filterText == null || filterText.equals("")) {
	        return true;
	    }

	    if (value == null) {
	        return false;
	    }

	    String name = value.toString().toUpperCase();
	    filterText = filterText.toUpperCase();

	    if (name.contains(filterText)) {
	        return true;
	    } else {
	        return false;
	    }
	}
}