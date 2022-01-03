package graphViz;
import target.Target;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Map;
import java.util.Set;


public class GraphViz
{

    private String TEMP_DIR ;
    private String DOT;
    private String NAMEFILE;


    private int[] dpiSizes = {46, 51, 57, 63, 70, 78, 86, 96, 106, 116, 128, 141, 155, 170, 187, 206, 226, 249};

    private int currentDpiPos = 7;

    public GraphViz(String Path , String name) {
        TEMP_DIR = Path;
        DOT = "dot";
        NAMEFILE = name;
    }

    public void decreaseDpi() {
        if (this.currentDpiPos > 0) {
            --this.currentDpiPos;
        }
    }
    private StringBuilder graph = new StringBuilder();
    public String getDotSource() {
        return this.graph.toString();
    }
    public void add(String line) {
        this.graph.append(line);
    }
    public void addln(String line) {
        this.graph.append(line + "\n");
    }
    public byte[] getGraph(String dot_source, String type) {
        //   File dot;
        try {
            // File dot = new File("c:/Users/danse/Documents/demo/demo.dot");
            byte[] img_stream = null;

            File dot = writeDotSourceToFile(dot_source);
            if (dot != null)
            {
                img_stream = get_img_stream(dot, type);
                //if (dot.delete() == false)
                //     System.err.println("Warning: " + dot.getAbsolutePath() + " could not be deleted!");
                return img_stream;
            }
            return null;
        } catch (Exception ioe) { return null; }
    }
    public int writeGraphToFile(byte[] img, File to) {
        try {
            FileOutputStream fos = new FileOutputStream(to);
            fos.write(img);
            fos.close();
        } catch (java.io.IOException ioe) { return -1; }
        return 1;
    }
    private byte[] get_img_stream(File dot, String type) {
        File img;
        byte[] img_stream = null;

        try {
         //   img = new File(TEMP_DIR+"/bar.png");
            img = File.createTempFile("bar", type, new File(TEMP_DIR));
            Runtime rt = Runtime.getRuntime();

            // patch by Mike Chenault
            //String[] args = {"dot -Tpng "+ dot.getAbsolutePath()+ " -o "+ img.getAbsolutePath()};
            String[] args = {"dot", "-Tpng", "-Gdpi="+dpiSizes[this.currentDpiPos], dot.getAbsolutePath(), "-o", img.getAbsolutePath()};
            Process p = rt.exec(args);

            p.waitFor();

            FileInputStream in = new FileInputStream(img.getAbsolutePath());
            img_stream = new byte[in.available()];
            in.read(img_stream);
            // Close it if we need to
            if( in != null ) in.close();

            if (img.delete() == false)
                System.err.println("Warning: " + img.getAbsolutePath() + " could not be deleted!");
        }
        catch (java.io.IOException ioe) {
            System.err.println("Error:    in I/O processing of tempfile in dir " + TEMP_DIR+"\n");
            System.err.println("       or in calling external command");
            ioe.printStackTrace();
        }
        catch (java.lang.InterruptedException ie) {
            System.err.println("Error: the execution of the external program was interrupted");
            ie.printStackTrace();
        }
        return img_stream;
    }
    private File writeDotSourceToFile(String str) throws java.io.IOException {
        File temp;
        try {
            temp = new File( TEMP_DIR +  NAMEFILE + ".viz" );
            FileWriter fout = new FileWriter(temp);
            fout.write(str);
            //BufferedWriter br = new BufferedWriter(new FileWriter(NAMEFILE+".dot"));
            // BufferedWriter br = new BufferedWriter(new FileWriter(TEMP_DIR +  NAMEFILE + ".viz"));
            // br.write(str);
            //br.flush();
            // br.close();
            fout.close();
        }
        catch (Exception e) {
            System.err.println("Error: I/O error while writing the dot source to temp file!");
            return null;
        }
        return temp;
    }
    public String start_graph() {
        return "digraph G {";
    }
    public String end_graph() {
        return "}";
    }
    //////////////////////////////////////////////////////////////////////////////////////
    public String makeDotFormat(Map<String, Target> targetMap , Map<String, Set<String>> serialSets) {
        String dotFormat = "";
        for (String key : targetMap.keySet()) {
            if (targetMap.get(key).getSetDependsOn().size() == 0)
                dotFormat += targetMap.get(key).getName() + ";\n";
            else {
                for (String DependsOn : targetMap.get(key).getSetDependsOn())
                    dotFormat += targetMap.get(key).getName()
                            + " -> "
                            + DependsOn
                            + ";\n";
            }
        }
        return dotFormat;
    }

}