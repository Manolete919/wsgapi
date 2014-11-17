package pnl.menu.vista;

import java.io.Serializable;

public class Theme implements Serializable {
	private static final long serialVersionUID = 1L;
	 
    private int id;
     
    private String displayName;
     
    private String name;
     
    public Theme() {}
 
    public Theme(int id, String displayName, String name) {
        this.id = id;
        this.displayName = displayName;
        this.name = name;
    }
 
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getDisplayName() {
        return displayName;
    }
 
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
     
    @Override
    public String toString() {
        return name;
    }
}