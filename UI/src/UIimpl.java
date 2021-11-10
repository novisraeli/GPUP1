import engine.*;
import xml.XmlException;

import java.util.*;
import java.io.*;



public class UIimpl implements UI {
    engine engine =new engineImpl();
    private boolean fileLoaded=false;
    private boolean firstTask=true;
    Scanner s =new Scanner(System.in);
    //add engine impl instance
    public void mainMenu(){

        int input;
        boolean run =true;
        System.out.println("Hello,please choose an option:");
        while(run){
            printMainMenu();
            if(s.hasNextInt()){
                input =s.nextInt();
                switch (input){//main menu switch
                    case 1:
                        if(loadFile()) {
                            fileLoaded = true;
                        }
                        break;
                    case 2:
                        if(fileLoadedCheck(fileLoaded)){
                            showGraphInfo();
                        }
                        break;
                    case 3:
                        if(fileLoadedCheck(fileLoaded)){
                            showTargetInfo();
                        }
                        break;
                    case 4:
                        if(fileLoadedCheck(fileLoaded)){
                            showPathBetweenTwoTargets();
                        }
                        break;
                    case 5:
                        if(fileLoadedCheck(fileLoaded)){
                            runTask();
                        }
                        break;
                    case 6:
                        run=false;
                        System.out.println("Salamat");
                        break;
                    default:
                        System.out.println("Invalid option");
                        break;
                }
            }
            else{
                System.out.println("not a number!!");
            }

        }

    }
    @Override

    public boolean loadFile(){//option 1
        String input;
        System.out.println("Enter file full path or, 0 to return to main menu");
        input=s.nextLine();
        if(s.equals("0")){
            return false;
        }
        else {
           try{
               engine.loadFile(input);
           }
           catch (Exception e){
               System.out.println(e);
               return false;
           }
        }
        return false;

    }
    @Override
    public void showGraphInfo(){//option 2
        try {
            engine.targetsInformation();
        }
        catch (Exception e){
            System.out.println(e);
        }
        //print what came back
    }
    @Override
    public void showTargetInfo(){//option 3
        System.out.println("Enter Target Name");
        String input=s.nextLine();
        try{
            engine.specificTargetInformation(input);
        }
        catch (Exception e){
            System.out.println(e);
        }
        //check Target exist in engine.checkTargetExist(input)
        //if not print something about it and return

        //run engine,showTargetInfo
        //print what came back
    }
    @Override
    public void showPathBetweenTwoTargets(){//option 4
        System.out.println("Enter Targets Names IN TWO SEPERATE LINES");
        String input1=s.nextLine();
        String input2=s.nextLine();
        try{
            engine.FindAPathBetweenTwoTargets(input1,input2);
        }
        catch (Exception e){
            System.out.println(e);
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
            engine.runTask(time,random,success,warning);//need to add params
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    private void printMainMenu(){
        System.out.println("1.Load file");
        System.out.println("2.Show info about the targets");
        System.out.println("3.Show info about a specific target");
        System.out.println("4.Find a path between 2 targets");
        System.out.println("5.Run task");
        System.out.println("6.Exit");
    }
    private boolean fileLoadedCheck(boolean file){
        if(!file){
            System.out.println("Flie not loaded yet");
            return false;
        }
        return true;
    }
}
