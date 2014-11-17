/**
 * Servicio.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pnl.wsg;

public class Servicio  implements java.io.Serializable, org.apache.axis.encoding.AnyContentType {
    private java.lang.String proveedorBase;

    private int codigoError;

    private org.apache.axis.message.MessageElement [] _any;

    private java.lang.String mensajeError;

    public Servicio() {
    }

    public Servicio(
           java.lang.String proveedorBase,
           int codigoError,
           org.apache.axis.message.MessageElement [] _any,
           java.lang.String mensajeError) {
           this.proveedorBase = proveedorBase;
           this.codigoError = codigoError;
           this._any = _any;
           this.mensajeError = mensajeError;
    }


    /**
     * Gets the proveedorBase value for this Servicio.
     * 
     * @return proveedorBase
     */
    public java.lang.String getProveedorBase() {
        return proveedorBase;
    }


    /**
     * Sets the proveedorBase value for this Servicio.
     * 
     * @param proveedorBase
     */
    public void setProveedorBase(java.lang.String proveedorBase) {
        this.proveedorBase = proveedorBase;
    }


    /**
     * Gets the codigoError value for this Servicio.
     * 
     * @return codigoError
     */
    public int getCodigoError() {
        return codigoError;
    }


    /**
     * Sets the codigoError value for this Servicio.
     * 
     * @param codigoError
     */
    public void setCodigoError(int codigoError) {
        this.codigoError = codigoError;
    }


    /**
     * Gets the _any value for this Servicio.
     * 
     * @return _any
     */
    public org.apache.axis.message.MessageElement [] get_any() {
        return _any;
    }


    /**
     * Sets the _any value for this Servicio.
     * 
     * @param _any
     */
    public void set_any(org.apache.axis.message.MessageElement [] _any) {
        this._any = _any;
    }


    /**
     * Gets the mensajeError value for this Servicio.
     * 
     * @return mensajeError
     */
    public java.lang.String getMensajeError() {
        return mensajeError;
    }


    /**
     * Sets the mensajeError value for this Servicio.
     * 
     * @param mensajeError
     */
    public void setMensajeError(java.lang.String mensajeError) {
        this.mensajeError = mensajeError;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Servicio)) return false;
        Servicio other = (Servicio) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.proveedorBase==null && other.getProveedorBase()==null) || 
             (this.proveedorBase!=null &&
              this.proveedorBase.equals(other.getProveedorBase()))) &&
            this.codigoError == other.getCodigoError() &&
            ((this._any==null && other.get_any()==null) || 
             (this._any!=null &&
              java.util.Arrays.equals(this._any, other.get_any()))) &&
            ((this.mensajeError==null && other.getMensajeError()==null) || 
             (this.mensajeError!=null &&
              this.mensajeError.equals(other.getMensajeError())));
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
        if (getProveedorBase() != null) {
            _hashCode += getProveedorBase().hashCode();
        }
        _hashCode += getCodigoError();
        if (get_any() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(get_any());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(get_any(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMensajeError() != null) {
            _hashCode += getMensajeError().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Servicio.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://axis/EISApiOnlineWS.wsdl/types/", "Servicio"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("proveedorBase");
        elemField.setXmlName(new javax.xml.namespace.QName("http://axis/EISApiOnlineWS.wsdl/types/", "proveedorBase"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoError");
        elemField.setXmlName(new javax.xml.namespace.QName("http://axis/EISApiOnlineWS.wsdl/types/", "codigoError"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mensajeError");
        elemField.setXmlName(new javax.xml.namespace.QName("http://axis/EISApiOnlineWS.wsdl/types/", "mensajeError"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
