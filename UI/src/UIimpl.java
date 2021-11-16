import engine.*;
import information.Information;
import engine.engine.*;
import java.util.*;

public class UIimpl implements UI {
    private engine engine = new engineImpl();
    private Scanner s = new Scanner(System.in);
    private Information info;

    public void mainMenu(){

        int input;
        boolean run =true;
        System.out.println("Hello To Nov & Bar G.P.U.P\n\r");
        while(run){
            System.out.println("Please choose an option: (number between 1 - 8)");
            printMainMenu();
            if(s.hasNextInt()){
                input =s.nextInt();
                s.nextLine();
                switch (input){//main menu switch
                    case 1:
                        loadFile();
                        break;

                    case 2:
                        if(engine.ifLoadFile())
                            showGraphInfo();
                        break;

                    case 3:
                        if(engine.ifLoadFile())
                            showTargetInfo();
                        break;

                    case 4:
                        if(engine.ifLoadFile())
                            showPathBetweenTwoTargets();
                        break;

                    case 5:
                        if(engine.ifLoadFile())
                            runTask();
                        break;

                    case 6:
                        if(engine.ifLoadFile())
                            circuitDetection();
                        break;

                    case 7:///////////////////////////////
                        if(engine.ifLoadFile())
                            printXml();
                        break;
                    case 8:
                        run=false;
                        System.out.println("Salamat");
                        break;
                    default:
                        System.out.println("Invalid option \n\rplease enter a number between 1 - 6\n\r");
                        break;
                }
            }
            else{
                System.out.println("Not a number!! \n\rplease enter a number between 1 - 6\n\r");
                s.nextLine();
            }

        }
    engine.exit();
    }

    @Override
    public boolean loadFile(){//option 1
        String input;
        System.out.println("Enter file full path ,or 0 to return to main menu");
        input=s.nextLine();
        if(s.equals("0")){
            return false;
        }
        else {
           try{
               engine.loadFile(input);
               return true;
           }
           catch (Exception e){
               System.out.println(e);
               return false;
           }
        }
    }
    @Override
    public void showGraphInfo(){//option 2
            System.out.println(engine.targetsInformation());
    }
    @Override
    public void showTargetInfo(){//option 3
        System.out.println("Enter Target Name");
        String input = s.nextLine();
        try{
            System.out.println(engine.specificTargetInformation(input));
        }
        catch (Exception e){
            System.out.println(e);
            System.out.println("choose one of the option:\n\r" +
                               "1. Insert new target\n\r" +
                               "2. Return to the menu");
            int inputInt = s.nextInt();
            s.nextLine();

            while (inputInt != 1 || inputInt != 2) {
                if (1 == inputInt) {
                    showTargetInfo();
                    return;
                } else
                    if (2 == inputInt)
                        return;
                    else {
                        System.out.println("Invalid option, choose again:");
                        inputInt = s.nextInt();
                        s.nextLine();
                }
            }
        }
        //check Target exist in engine.checkTargetExist(input)
        //if not print something about it and return

        //run engine,showTargetInfo
        //print what came back
    }
    @Override
    public void showPathBetweenTwoTargets(){//option 4
        int x = 0;
        Dependence d = engineImpl.Dependence.DEPENDS_ON;
        String st;

        System.out.println("Enter Targets Names IN TWO SEPERATE LINES");                    // get the names of targets
        String input1 = s.nextLine();
        String input2 = s.nextLine();

        System.out.println("Enter the attitude between the targets");                          // get the attitude between targets
        System.out.println("1 - DEPENDS_ON\n" + "2 - REQUIRED_FOR");

        while (x != 1 && x!= 2) {
            x = s.nextInt();
            s.nextLine();
            if (x == 1)
                d = engineImpl.Dependence.DEPENDS_ON;
            else if (x == 2)
                d = engineImpl.Dependence.REQUIRED_FOR;
            else
                System.out.println("Invalid option \n" + "1 - DEPENDS_ON\n" + "2 - REQUIRED_FOR\n");
        }

        try{
            info = engine.findAPathBetweenTwoTargets(input1,input2 , d);
            System.out.println(info);
        }
        catch (Exception e){

            System.out.println(e); ///

            System.out.println("choose one of the option:\n\r" +
                    "1. Insert new targets\n\r" +
                    "2. Return to the menu");
            int inputInt = s.nextInt();
            s.nextLine();

            while (inputInt != 1 || inputInt != 2) {
                if (1 == inputInt) {
                    showPathBetweenTwoTargets();
                    return;
                } else
                if (2 == inputInt)
                    return;
                else {
                    System.out.println("Invalid option, choose again:");
                    inputInt = s.nextInt();
                    s.nextLine();
                }
            }


        }
        //check Targets exist in engine.checkTargetExist(input) can check for each target seperatlly
        //if not print something about it and return

        //run engine.showPathBetweenTwoTargets(input1,input2)
        //print what came back
    }

    @Override
    public void runTask() {//option 5
        System.out.println("Enter task details as follows(EACH PARAMETER IS SEPERATED BY SPACE NOT BY ,)) : ");
        System.out.println("simTime for each target(time in ms) ,random or set(1 for random, 0 for set)");
        System.out.println("chance of task succeeding(a number between 1 and 0)");
        System.out.println("if Succeed chance of warning(a number between 1 and 0)");
        float time,success,warning;
        boolean random;
        time=s.nextFloat();
        random=s.nextBoolean();
        success=s.nextFloat();
        warning=s.nextFloat();
        try{
            System.out.println(engine.runTask(time,random,success,warning));//need to add params
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void circuitDetection(){
        System.out.println("Please enter name of target");
        String input1=s.nextLine();
        try {
            System.out.println(engine.circuitDetection(input1));
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    public void printXml() {//option 7
        System.out.println("\n" +
                "\nThe XML File: \n\r");
        engine.printXml();
    }

    private void printMainMenu(){
        System.out.println("1.Load file");
        System.out.println("2.Show info about the targets");
        System.out.println("3.Show info about a specific target");
        System.out.println("4.Find a path between 2 targets");
        System.out.println("5.Run task");
        System.out.println("6.Circuit detection");
        System.out.println("7.print xml");
        System.out.println("8.Exit");
    }
}
