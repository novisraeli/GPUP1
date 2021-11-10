import java.util.*;
import java.io.*;


public class UIimpl implements UI {
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
                    break;
                case 5:
                    break;
                case 6:
                    run=false;
                    break;
                default:
                    break;
            }
        }

    }
    public boolean loadFile(){//option 1
        String input;
        while(true){
            System.out.println("Enter file full path or, 0 to return to main menu");
            input=s.nextLine();
            if(s.equals("0")){
                return false;
            }
            File f = new File(input);
            if(f.exists()){
                //load file to engine(can send file as argument
                //add check valid file in engine
            }
            else {
                System.out.println("Flie doesn't exist :(");
            }
        }
    }
    public void showGraphInfo(){//option 2
        //add engine.showGraphInfo
        //print what came back
    }
    public void showTargetInfo(){
        System.out.println("Enter Target Name");
        String input=s.nextLine();
        //check Target exist in engine.checkTargetExist(input)
        //if not print something about it and return

        //run engine,showTargetInfo
        //print what came back
    }
    public void showPathBetweenTwoTargets(){
        System.out.println("Enter Targets Names IN TWO SEPERATE LINES");
        String input1=s.nextLine();
        String input2=s.nextLine();
        //check Targets exist in engine.checkTargetExist(input) can check for each target seperatlly
        //if not print something about it and return

        //run engine.showPathBetweenTwoTargets(input1,input2)
        //print what came back
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
