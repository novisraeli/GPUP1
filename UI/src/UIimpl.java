import java.util.*;



public class UIimpl implements UI {
    private boolean flieLoaded;

    Scanner s =new Scanner(System.in);
    public void mainMenu(){
        int input;
        System.out.println("Hello,please choose an option:");
        printMainMenu();
        input =s.nextInt();
        while(true){
            switch (input){
                case 1:

                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                default:
                    break;
            }
        }

    }
    public void openFile(String path){

    }
    private void printMainMenu(){
        System.out.println("1.Load file");
        System.out.println("2.Show info about the targets");
        System.out.println("3.Show info about a specific target");
        System.out.println("4.Find a path between 2 targets");
        System.out.println("5.Run task");
        System.out.println("6.Exit");
    }
}
