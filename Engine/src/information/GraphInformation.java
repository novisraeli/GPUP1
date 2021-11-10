package information;

public class GraphInformation implements Information{ // 2

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

    @Override
    public String toString() {
        return "Graph Information: \n\r" +
                "amount Of Targets: " + amountOfTargets +
                "amount Of levies: " + levies +
                "amount Of middles: " + middles +
                "amount Of roots: " + roots +
                "amount Of independents: " + independents + "\n\r";
    }
}
