package xml;
import generated2.*;
//import generated2.GPUPDescriptor.GPUPSerialSets.GPUPSerialSet;

import target.DependsOnConflict;
import target.*;

import java.io.FileInputStream;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.*;


public class Xmlimpl implements Xml {
    private final generated2.GPUPDescriptor gpupDescriptor;
    private final static String JAXB_XML_PACKAGE_NAME = "generated2";

    public Xmlimpl(String path) throws Exception {
        try {
            InputStream inputStream = new FileInputStream(new File(path));
            gpupDescriptor = deserializeFrom(inputStream);
        } catch (Exception ex) {
            throw new XmlIsExists(path);
        }
    }
    private static GPUPDescriptor deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (GPUPDescriptor) u.unmarshal(in);
    }
    public Map<String, Target> makeAMap() throws Exception {
        Target newTarget;
        Map<String, Target> targetsMap = new HashMap<>();

        for (GPUPTarget p : gpupDescriptor.getGPUPTargets().getGPUPTarget()) {
            if (targetsMap.containsKey(p.getName().toUpperCase()) || targetsMap.containsKey(p.getName())) {
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
        }
 /* /// test :
        Set <String> setA = new HashSet<>();
        setA.add("B");
        setA.add("G");
        Set <String> setB = new HashSet<>();
        setB.add("C");
        Set <String> setC = new HashSet<>();
        setC.add("A");
        setC.add("F");
        Set <String> setD = new HashSet<>();
        setD.add("E");
        Set <String> setE = new HashSet<>();
        setE.add("A");
        Set <String> setF = new HashSet<>();


        targetsMap.put("A" , new Target("A" , null , setA , new HashSet()));
        targetsMap.put("B" , new Target("B" , null , setB , new HashSet()));
        targetsMap.put("C" , new Target("C" , null, setC , new HashSet()));
        targetsMap.put("D" , new Target("D" , null , setD , new HashSet()));
        targetsMap.put("E" , new Target("E" , null , setE , new HashSet()));
        targetsMap.put("F" , new Target("F" , null , setF , new HashSet()));
*/
        organizeTheDependencies(targetsMap);
        makeTypeForTargets(targetsMap);
            return targetsMap;
    }
    public void organizeTheDependencies(Map<String, Target> targetMap) throws Exception {
        Set<String> setOfKey = targetMap.keySet();

        for (String targetKey: setOfKey) // organize all the map
        {
            // organize the RequiredFor of all target
            for (String st2 : targetMap.get(targetKey).getSetDependsOn()) {
                if (setOfKey.contains(st2)) { // check if the target is exists in the xml file
                    if (!targetMap.get(st2).getSetDependsOn().contains(targetKey)) // check if there is a conflict
                        targetMap.get(st2).addToSetRequiredFor(targetKey);
                    else throw new DependsOnConflict(targetKey,st2);
                }
                else
                    throw new TargetIsExists(st2);
            }
            // organize the DependsOn of all target
            for (String st2 : targetMap.get(targetKey).getSetRequiredFor()) {
                if (setOfKey.contains(st2)) { // check if the target is exists in the xml file
                    if (!targetMap.get(st2).getSetRequiredFor().contains(targetKey)) // check if there is a conflict
                        targetMap.get(st2).addToSetDependsOn(targetKey);
                    else throw new RequiredForConflict(targetKey,st2);
                }
                else
                    throw new TargetIsExists(st2);
            }
        }
    }
    public void makeTypeForTargets(Map<String, Target> targetMap) {
        targetMap.forEach((k,t) ->
                {
                    if (t.getSetRequiredFor().size() == 0 && t.getSetDependsOn().size() == 0)
                        t.SetType(Target.Type.INDEPENDENTS);
                    else if (t.getSetDependsOn().size() == 0)
                        t.SetType(Target.Type.LEAF);
                    else if (t.getSetRequiredFor().size() == 0)
                        t.SetType(Target.Type.ROOT);
                    else
                        t.SetType(Target.Type.MIDDLE);
                }
        );
    }
    public String getWorkingDirectoryXml(){
        return gpupDescriptor.getGPUPConfiguration().getGPUPWorkingDirectory();
    }
    public int getMaxParallelism(){
        return gpupDescriptor.getGPUPConfiguration().getGPUPMaxParallelism();
    }
    public Map<String,Set<String>> getSerialSets(){
        Map<String,Set<String>> serialSets = new HashMap<>();
        List<GPUPDescriptor.GPUPSerialSets.GPUPSerialSet> temp=gpupDescriptor.getGPUPSerialSets().getGPUPSerialSet();

        for(GPUPDescriptor.GPUPSerialSets.GPUPSerialSet s:temp){
            Set<String> set=new HashSet<>();
            String t="";
            String targets=s.getTargets();
            for(int i=0;i<targets.length();i++){
                if(targets.charAt(i)==','){
                    set.add(t);
                    t="";
                }
                else{
                    t+=targets.charAt(i);
                }
            }
            serialSets.put(s.getName(),set);
        }
        return serialSets;
    }

}