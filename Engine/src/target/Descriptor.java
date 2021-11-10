package target;

import generated.GPUPConfiguration;
import generated.GPUPTargets;

import javax.xml.bind.annotation.XmlElement;

public class Descriptor {

    private Configuration Configuration;

    private Targets Targets;

    public Configuration getConfiguration() {
        return Configuration;
    }

    public void setConfiguration(Configuration value) {
        this.Configuration = value;
    }

    public Targets getTargets() {
        return Targets;
    }

    public void setGPUPTargets(Targets value) {
        this.Targets = value;
    }
}
