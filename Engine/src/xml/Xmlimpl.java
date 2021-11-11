package xml;

import generated.GPUPDescriptor;
import generated.GPUPTarget;
import generated.GPUPTargetDependencies;
import target.Dependency;
import target.Target;
import target.TargetDependencies;

import java.io.FileInputStream;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Xmlimpl implements Xml{

    private GPUPDescriptor GPUPDescriptor;
    private final static String JAXB_XML_PACKAGE_NAME = "generated";

    public Xmlimpl(String path) throws Exception {

        try {
            InputStream inputStream = new FileInputStream(new File(path));
            GPUPDescriptor = deserializeFrom(inputStream);

        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }

        catch (Exception ex) {
            throw new XmlIsExists(path);
        }
    }


    public void checkXmlFile() throws Exception {
        /// check if the file is contsin legal content

    }

    private static GPUPDescriptor deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (GPUPDescriptor) u.unmarshal(in);
    }

    public Map<String, Target> makeAMap()  {

        Target newTarget;
        Map<String,Target> targetsMap= new HashMap<>();

        for (GPUPTarget p : GPUPDescriptor.getGPUPTargets().getGPUPTarget()) {

            if (p.getGPUPTargetDependencies() == null)
                newTarget = new Target(p.getName(), null, p.getGPUPUserData());

            else{
            List<Dependency> newListDependency = new ArrayList<>();
                for (GPUPTargetDependencies.GPUGDependency p2 : p.getGPUPTargetDependencies().getGPUGDependency()) {
                    newListDependency.add(new Dependency(p2.getValue(), p2.getType()));
                }
            newTarget = new Target(p.getName(), new TargetDependencies(newListDependency), p.getGPUPUserData());
            }
            targetsMap.put(newTarget.getName(),newTarget);
        }
        return targetsMap;
    }



}
