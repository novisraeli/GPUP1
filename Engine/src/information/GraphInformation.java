package information;

public class GraphInformation  extends InformationToUI{

    private int amountOfTargets;
    private int levies;
    private int middles;
    private int roots;
    private int independents;
    public GraphInformation(int amountOfTargets , int levies, int middles, int roots,int independents)
    {
        this.amountOfTargets = amountOfTargets;
        this.levies = levies;
        this.middles = middles;
        this.roots = roots;
        this.independents = independents;
    }

}
