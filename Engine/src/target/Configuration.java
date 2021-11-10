package target;

import javax.xml.bind.annotation.XmlElement;

public class Configuration {

    private String GraphName;

    private String WorkingDirectory;

    public String getGraphName() {
        return GraphName;
    }

    public void setGraphName(String value) {
        this.GraphName = value;
    }

    public String getWorkingDirectory() {
        return WorkingDirectory;
    }

    public void setWorkingDirectory(String value) {
        this.WorkingDirectory = value;
    }

}
