package information;
/// contain all the information about targets graph:
/// - amount of targets
/// - amount of levies
/// - amount of middles
/// - amount of independents
/// - amount of roots

public class GraphInformation implements Information{ // 2

    private final int amountOfTargets;
    private final int levies;
    private final int middles;
    private final int roots;
    private final int independents;

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
                "amount Of Targets: " + amountOfTargets + "\n\r" +
                "amount Of levies: " + levies + "\n\r" +
                "amount Of middles: " + middles + "\n\r" +
                "amount Of roots: " + roots + "\n\r" +
                "amount Of independents: " + independents + "\n\r";
    }
}
