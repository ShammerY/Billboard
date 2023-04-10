import model.*;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Main {
    private Scanner reader;
    private BillboardList billboardList;
    public Main(){
        reader = new Scanner(System.in);
        billboardList = new BillboardList();
    }
    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.loadJsonData();
        main.executeProgram();
    }

    private void print(Object t){System.out.println(t);}

    public void executeProgram() throws IOException {
        print(mainMenu());
        switch(reader.next()){
            case "1":
                print(loadData());
                executeProgram();
            case "2":
                print(registerBillboard());
                billboardList.saveData();
                executeProgram();
            case "3":
                print(billboardList.printBillboards());
                executeProgram();
            case "4":
                print(dangerousBillboardReport());
                executeProgram();
            case "0":
                System.exit(0);
            default:
                print("\n Invalid Option");
                executeProgram();
        }
    }
    private String loadData() throws IOException {
        print("\n Enter the PATH where the archive is saved in this computer :\n"+
                "Format example :\nC:/folder/folder/......../file_name.csv");
        reader.nextLine();
        String path = reader.nextLine();
        if(!billboardList.loadData(path)){
            return "\n FILE NOT FOUND";
        }
        billboardList.saveData();
        return "\n Data Loaded Succesfully";
    }
    private String registerBillboard(){
        String input;
        String[] data;
        print("\n Enter width, height, if its used, brand name :\n"+
                "Format example :\n 111++222++true++brand_Name");
        try{
            reader.nextLine();
            input = reader.nextLine();
            data = input.split("\\+\\+");
            if(Integer.parseInt(data[0]) <=0 || Integer.parseInt(data[1])<=0){return "\nERROR : Width and Height must be greater than 0";}
            billboardList.addBillboard(new Billboard(Integer.parseInt(data[0]),Integer.parseInt(data[1]),Boolean.parseBoolean(data[2]),data[3]));
        }catch(ArrayIndexOutOfBoundsException ex){
            return "\nERROR : Please enter the necessary input values";
        }catch(NumberFormatException ex){
            return "\nError : ONE OF THE INPUT VALUES IS NOT VALID";
        }
        return "\n Billbard registered succesfully";
    }
    private String dangerousBillboardReport() throws IOException {
        print("\n Enter Folder path where the data will be saved :\n"+
                "Format example:\nC:/folder/folder/.........../folder_name");
        reader.nextLine();
        String path = reader.nextLine();
        return billboardList.dangerousBillboardReport(path);
    }
    private void loadJsonData() throws IOException {
        print(billboardList.loadJsonData());
    }
    private String mainMenu(){
        return  "\n OPTIONS : \n"+
                "(1) Import SCV data \n"+
                "(2) Register new Billboard \n"+
                "(3) Print Billboards \n"+
                "(4) High Danger Level Report \n"+
                "(0) Exit Program";
    }
}