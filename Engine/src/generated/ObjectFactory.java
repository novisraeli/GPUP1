
package generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated package. 
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

    private final static QName _GPUPUserData_QNAME = new QName("", "GPUP-User-Data");
    private final static QName _GPUPWorkingDirectory_QNAME = new QName("", "GPUP-Working-Directory");
    private final static QName _GPUPGraphName_QNAME = new QName("", "GPUP-Graph-Name");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GPUPTargetDependencies }
     * 
     */
    public GPUPTargetDependencies createGPUPTargetDependencies() {
        return new GPUPTargetDependencies();
    }

    /**
     * Create an instance of {@link GPUPDescriptor }
     * 
     */
    public GPUPDescriptor createGPUPDescriptor() {
        return new GPUPDescriptor();
    }

    /**
     * Create an instance of {@link GPUPConfiguration }
     * 
     */
    public GPUPConfiguration createGPUPConfiguration() {
        return new GPUPConfiguration();
    }

    /**
     * Create an instance of {@link GPUPTargets }
     * 
     */
    public GPUPTargets createGPUPTargets() {
        return new GPUPTargets();
    }

    /**
     * Create an instance of {@link GPUPTarget }
     * 
     */
    public GPUPTarget createGPUPTarget() {
        return new GPUPTarget();
    }

    /**
     * Create an instance of {@link GPUPTargetDependencies.GPUGDependency }
     * 
     */
    public GPUPTargetDependencies.GPUGDependency createGPUPTargetDependenciesGPUGDependency() {
        return new GPUPTargetDependencies.GPUGDependency();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "GPUP-User-Data")
    public JAXBElement<String> createGPUPUserData(String value) {
        return new JAXBElement<String>(_GPUPUserData_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "GPUP-Working-Directory")
    public JAXBElement<String> createGPUPWorkingDirectory(String value) {
        return new JAXBElement<String>(_GPUPWorkingDirectory_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "GPUP-Graph-Name")
    public JAXBElement<String> createGPUPGraphName(String value) {
        return new JAXBElement<String>(_GPUPGraphName_QNAME, String.class, null, value);
    }

}
