/**
 * GenericoServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pnl.wsg;

public class GenericoServiceLocator extends org.apache.axis.client.Service implements pnl.wsg.GenericoService {

    public GenericoServiceLocator() {
    }


    public GenericoServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public GenericoServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ServicioWebGenericoPort
    private java.lang.String ServicioWebGenericoPort_address = "http://localhost:7001/wsg/GenericoService";

    public java.lang.String getServicioWebGenericoPortAddress() {
        return ServicioWebGenericoPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ServicioWebGenericoPortWSDDServiceName = "ServicioWebGenericoPort";

    public java.lang.String getServicioWebGenericoPortWSDDServiceName() {
        return ServicioWebGenericoPortWSDDServiceName;
    }

    public void setServicioWebGenericoPortWSDDServiceName(java.lang.String name) {
        ServicioWebGenericoPortWSDDServiceName = name;
    }

    public pnl.wsg.ServicioWebGenerico getServicioWebGenericoPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ServicioWebGenericoPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getServicioWebGenericoPort(endpoint);
    }

    public pnl.wsg.ServicioWebGenerico getServicioWebGenericoPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            pnl.wsg.ServicioWebGenericoPortBindingStub _stub = new pnl.wsg.ServicioWebGenericoPortBindingStub(portAddress, this);
            _stub.setPortName(getServicioWebGenericoPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setServicioWebGenericoPortEndpointAddress(java.lang.String address) {
        ServicioWebGenericoPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (pnl.wsg.ServicioWebGenerico.class.isAssignableFrom(serviceEndpointInterface)) {
                pnl.wsg.ServicioWebGenericoPortBindingStub _stub = new pnl.wsg.ServicioWebGenericoPortBindingStub(new java.net.URL(ServicioWebGenericoPort_address), this);
                _stub.setPortName(getServicioWebGenericoPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ServicioWebGenericoPort".equals(inputPortName)) {
            return getServicioWebGenericoPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://axis/EISApiOnlineWS.wsdl/types/", "GenericoService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://axis/EISApiOnlineWS.wsdl/types/", "ServicioWebGenericoPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ServicioWebGenericoPort".equals(portName)) {
            setServicioWebGenericoPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
