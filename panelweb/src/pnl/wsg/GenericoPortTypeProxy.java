package pnl.wsg;

public class GenericoPortTypeProxy implements pnl.wsg.GenericoPortType {
  private String _endpoint = null;
  private pnl.wsg.GenericoPortType genericoPortType = null;
  
  public GenericoPortTypeProxy() {
    _initGenericoPortTypeProxy();
  }
  
  public GenericoPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initGenericoPortTypeProxy();
  }
  
  private void _initGenericoPortTypeProxy() {
    try {
      genericoPortType = (new pnl.wsg.GenericoServiceLocator()).getGenericoPortTypePort();
      if (genericoPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)genericoPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)genericoPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (genericoPortType != null)
      ((javax.xml.rpc.Stub)genericoPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public pnl.wsg.GenericoPortType getGenericoPortType() {
    if (genericoPortType == null)
      _initGenericoPortTypeProxy();
    return genericoPortType;
  }
  
  public pnl.wsg.ServicioResponseElement obtenerXml(pnl.wsg.ServicioElement parameters) throws java.rmi.RemoteException{
    if (genericoPortType == null)
      _initGenericoPortTypeProxy();
    return genericoPortType.obtenerXml(parameters);
  }
  
  
}