package xml;

import generated.GPUPDescriptor;
import generated.GPUPTarget;
import generated.GPUPTargetDependencies;
import target.Target;
import target.Type;


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
                newTarget = new Target(p.getName(), p.getGPUPUserData() , null , null , Type.INDEPENDENTS);

            else
            {
            List<String> newListDependency = new ArrayList<>();
            List<String> newListRequiredFor = new ArrayList<>();

            for (GPUPTargetDependencies.GPUGDependency p2 : p.getGPUPTargetDependencies().getGPUGDependency()) {
                if (p2.getType().equals("dependsOn"))
                    newListDependency.add(p2.getValue());
                else if (p2.getType().equals("requiredFor"))
                    newListRequiredFor.add(p2.getValue());
            }

            if (newListDependency == null)
                newTarget = new Target(p.getName(), p.getGPUPUserData() , newListDependency , null , Type.ROOT);
            else if(newListRequiredFor == null)
                newTarget = new Target(p.getName(), p.getGPUPUserData() , null , newListRequiredFor , Type.LEAF);
            else
                newTarget = new Target(p.getName(), p.getGPUPUserData() , newListDependency , newListRequiredFor , Type.MIDDLE);


            }
            targetsMap.put(newTarget.getName(),newTarget);
        }
        return targetsMap;
    }



}
