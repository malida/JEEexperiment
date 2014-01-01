
package ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AddEmergency_QNAME = new QName("http://ws/", "addEmergency");
    private final static QName _GetEmergencias_QNAME = new QName("http://ws/", "getEmergencias");
    private final static QName _GetEmergenciasResponse_QNAME = new QName("http://ws/", "getEmergenciasResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetEmergenciasResponse }
     * 
     */
    public GetEmergenciasResponse createGetEmergenciasResponse() {
        return new GetEmergenciasResponse();
    }

    /**
     * Create an instance of {@link GetEmergencias }
     * 
     */
    public GetEmergencias createGetEmergencias() {
        return new GetEmergencias();
    }

    /**
     * Create an instance of {@link AddEmergency }
     * 
     */
    public AddEmergency createAddEmergency() {
        return new AddEmergency();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddEmergency }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "addEmergency")
    public JAXBElement<AddEmergency> createAddEmergency(AddEmergency value) {
        return new JAXBElement<AddEmergency>(_AddEmergency_QNAME, AddEmergency.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEmergencias }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "getEmergencias")
    public JAXBElement<GetEmergencias> createGetEmergencias(GetEmergencias value) {
        return new JAXBElement<GetEmergencias>(_GetEmergencias_QNAME, GetEmergencias.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEmergenciasResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "getEmergenciasResponse")
    public JAXBElement<GetEmergenciasResponse> createGetEmergenciasResponse(GetEmergenciasResponse value) {
        return new JAXBElement<GetEmergenciasResponse>(_GetEmergenciasResponse_QNAME, GetEmergenciasResponse.class, null, value);
    }

}
