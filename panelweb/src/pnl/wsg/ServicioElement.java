/**
 * ServicioElement.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pnl.wsg;

public class ServicioElement  implements java.io.Serializable {
    private long idServicio;

    private java.lang.String usuario;

    private java.lang.String clave;

    private pnl.wsg.Bind sentencias_binds;

    public ServicioElement() {
    }

    public ServicioElement(
           long idServicio,
           java.lang.String usuario,
           java.lang.String clave,
           pnl.wsg.Bind sentencias_binds) {
           this.idServicio = idServicio;
           this.usuario = usuario;
           this.clave = clave;
           this.sentencias_binds = sentencias_binds;
    }


    /**
     * Gets the idServicio value for this ServicioElement.
     * 
     * @return idServicio
     */
    public long getIdServicio() {
        return idServicio;
    }


    /**
     * Sets the idServicio value for this ServicioElement.
     * 
     * @param idServicio
     */
    public void setIdServicio(long idServicio) {
        this.idServicio = idServicio;
    }


    /**
     * Gets the usuario value for this ServicioElement.
     * 
     * @return usuario
     */
    public java.lang.String getUsuario() {
        return usuario;
    }


    /**
     * Sets the usuario value for this ServicioElement.
     * 
     * @param usuario
     */
    public void setUsuario(java.lang.String usuario) {
        this.usuario = usuario;
    }


    /**
     * Gets the clave value for this ServicioElement.
     * 
     * @return clave
     */
    public java.lang.String getClave() {
        return clave;
    }


    /**
     * Sets the clave value for this ServicioElement.
     * 
     * @param clave
     */
    public void setClave(java.lang.String clave) {
        this.clave = clave;
    }


    /**
     * Gets the sentencias_binds value for this ServicioElement.
     * 
     * @return sentencias_binds
     */
    public pnl.wsg.Bind getSentencias_binds() {
        return sentencias_binds;
    }


    /**
     * Sets the sentencias_binds value for this ServicioElement.
     * 
     * @param sentencias_binds
     */
    public void setSentencias_binds(pnl.wsg.Bind sentencias_binds) {
        this.sentencias_binds = sentencias_binds;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ServicioElement)) return false;
        ServicioElement other = (ServicioElement) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.idServicio == other.getIdServicio() &&
            ((this.usuario==null && other.getUsuario()==null) || 
             (this.usuario!=null &&
              this.usuario.equals(other.getUsuario()))) &&
            ((this.clave==null && other.getClave()==null) || 
             (this.clave!=null &&
              this.clave.equals(other.getClave()))) &&
            ((this.sentencias_binds==null && other.getSentencias_binds()==null) || 
             (this.sentencias_binds!=null &&
              this.sentencias_binds.equals(other.getSentencias_binds())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += new Long(getIdServicio()).hashCode();
        if (getUsuario() != null) {
            _hashCode += getUsuario().hashCode();
        }
        if (getClave() != null) {
            _hashCode += getClave().hashCode();
        }
        if (getSentencias_binds() != null) {
            _hashCode += getSentencias_binds().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ServicioElement.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://axis/EISApiOnlineWS.wsdl/types/", "servicioElement"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idServicio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://axis/EISApiOnlineWS.wsdl/types/", "idServicio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usuario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://axis/EISApiOnlineWS.wsdl/types/", "usuario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clave");
        elemField.setXmlName(new javax.xml.namespace.QName("http://axis/EISApiOnlineWS.wsdl/types/", "clave"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sentencias_binds");
        elemField.setXmlName(new javax.xml.namespace.QName("http://axis/EISApiOnlineWS.wsdl/types/", "sentencias_binds"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://axis/EISApiOnlineWS.wsdl/types/", "bind"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
