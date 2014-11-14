package wsg.web;

import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="sentencias_binds")
public class Bind {
	
	@XmlAnyElement(lax = true)
	protected List<Object> any;

	public List<Object> getAny() {
		return any;
	}

	public void setAny(List<Object> any) {
		this.any = any;
	}
	
}
