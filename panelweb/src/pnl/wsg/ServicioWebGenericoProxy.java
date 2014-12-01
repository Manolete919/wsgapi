package pnl.wsg;

public class ServicioWebGenericoProxy implements pnl.wsg.ServicioWebGenerico {
  private String _endpoint = null;
  private pnl.wsg.ServicioWebGenerico servicioWebGenerico = null;
  
  public ServicioWebGenericoProxy() {
    _initServicioWebGenericoProxy();
  }
  
  public ServicioWebGenericoProxy(String endpoint) {
    _endpoint = endpoint;
    _initServicioWebGenericoProxy();
  }
  
  private void _initServicioWebGenericoProxy() {
    try {
      servicioWebGenerico = (new pnl.wsg.GenericoServiceLocator()).getServicioWebGenericoPort();
      if (servicioWebGenerico != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)servicioWebGenerico)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)servicioWebGenerico)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (servicioWebGenerico != null)
      ((javax.xml.rpc.Stub)servicioWebGenerico)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public pnl.wsg.ServicioWebGenerico getServicioWebGenerico() {
    if (servicioWebGenerico == null)
      _initServicioWebGenericoProxy();
    return servicioWebGenerico;
  }
  
  public pnl.wsg.ServicioResponseElement obtenerXml(pnl.wsg.ServicioElement parameters) throws java.rmi.RemoteException{
    if (servicioWebGenerico == null)
      _initServicioWebGenericoProxy();
    return servicioWebGenerico.obtenerXml(parameters);
  }
  
  
}