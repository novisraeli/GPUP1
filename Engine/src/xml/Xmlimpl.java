package xml;

import generated.GPUPDescriptor;
import generated.GPUPTarget;
import generated.GPUPTargetDependencies;
import target.Target;
import target.TargetIsExists;
import target.Type;
import target.UniqueTarget;


import java.io.FileInputStream;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Xmlimpl implements Xml {

    private GPUPDescriptor GPUPDescriptor;
    private final static String JAXB_XML_PACKAGE_NAME = "generated";

    public Xmlimpl(String path) throws Exception {

        try {
            InputStream inputStream = new FileInputStream(new File(path));
            GPUPDescriptor = deserializeFrom(inputStream);

        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception ex) {
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

    public Map<String, Target> makeAMap() throws Exception {
        Target newTarget;
        Map<String, Target> targetsMap = new HashMap<>();

        for (GPUPTarget p : GPUPDescriptor.getGPUPTargets().getGPUPTarget()) {

            if (targetsMap.containsKey(p.getName())) {
                throw new UniqueTarget(p.getName());
            } else {
                Set<String> newSetDependency = new HashSet<>();
                Set<String> newSetRequiredFor = new HashSet<>();

                if (p.getGPUPTargetDependencies() == null)
                    newTarget = new Target(p.getName(), p.getGPUPUserData(), newSetDependency, newSetRequiredFor);

                else {
                    for (GPUPTargetDependencies.GPUGDependency p2 : p.getGPUPTargetDependencies().getGPUGDependency()) {
                        if (p2.getType().equals("dependsOn"))
                            newSetDependency.add(p2.getValue());
                        else if (p2.getType().equals("requiredFor"))
                            newSetRequiredFor.add(p2.getValue());
                    }
                    newTarget = new Target(p.getName(), p.getGPUPUserData(), newSetDependency, newSetRequiredFor);
                }

                targetsMap.put(newTarget.getName(), newTarget);
            }
            try {
                organizeTheDependencies(targetsMap);
                makeTypeForTargets(targetsMap);
            }
            catch (Exception e) {
            throw e;
            }
            finally {

            }
        }
            return targetsMap;

    }

    public void organizeTheDependencies(Map<String, Target> targetMap) throws Exception {
        Set setOfKey = targetMap.keySet();
        targetMap.forEach((k,t) ->
                {
                    for (String st : t.getSetDependsOn()) {
                        if (setOfKey.contains(st))
                            targetMap.get(st).addToSetRequiredFor(k);
                        //else
                            //throw new UniqueTarget("bar");

                    }

                    for (String st : t.getSetRequiredFor())
                    {
                        if (setOfKey.contains(st))
                            targetMap.get(st).addToSetDependsOn(k);
                        //else
                          //  throw new TargetIsExists(st);
                    }
                }
        );
    }
    public void makeTypeForTargets(Map<String, Target> targetMap) {
        targetMap.forEach((k,t) ->
                {
                    if (t.getSetRequiredFor().size() == 0 && t.getSetDependsOn().size() == 0)
                        t.SetType(Type.INDEPENDENTS);
                    else if (t.getSetDependsOn().size() == 0)
                        t.SetType(Type.LEAF);
                    else if (t.getSetRequiredFor().size() == 0)
                        t.SetType(Type.ROOT);
                    else
                        t.SetType(Type.MIDDLE);
                }
        );
    }
}