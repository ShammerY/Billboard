package model;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.io.*;

public class BillboardList {
    public ArrayList<Billboard> list;
    public BillboardList(){
        list = new ArrayList<>();
    }
    public ArrayList<Billboard> getList(){
        return list;
    }
    public void addBillboard(Billboard e){
        list.add(e);
    }
    public String loadJsonData() throws IOException{
        try{
            File file = new File("data.json");
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String data = "";
            String line = "";
            while((line = reader.readLine())!=null){
                data += line;
            }
            Gson gson = new Gson();
            Billboard[] array = gson.fromJson(data , Billboard[].class);
            fis.close();

            for(int i=0; i<array.length;i++){
                list.add(array[i]);
            }
            return "\n Data recovered";
        }catch(FileNotFoundException ex){
            return "";
        }
    }
    public boolean loadData(String path) throws IOException {
        try{
            String line;
            String msj = "";
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            while((line = reader.readLine())!=null){
                String[] data = line.split("\\|");
                if(isNumeric(data[0])){
                    list.add(new Billboard(Integer.parseInt(data[0]),Integer.parseInt(data[1]),Boolean.parseBoolean(data[2]),data[3]));
                }
            }
        }catch(FileNotFoundException ex){
            return false;
        }
        return true;
    }
    public void saveData() throws IOException{
        String path = "data.json";
        File file = new File(path);
        FileOutputStream fos = new FileOutputStream(file);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));

        Gson gson = new Gson();
        String data = gson.toJson(list);

        writer.write(data);
        writer.flush();
        fos.close();
    }
    private boolean isNumeric(String t){
        try{
            Integer.parseInt(t);
            return true;
        }catch(NumberFormatException ex){
            return false;
        }
    }
    public String printBillboards(){
        if(list.size()==0){return "\n No billboards registered yet...";}
        String msj = "\n W  |  H  | inUse | Brand";
        for(int i=0;i<list.size();i++){
            msj+="\n"+list.get(i).getWidth()+"   "+list.get(i).getHeight();
            if(list.get(i).isInUse()){
                msj += "   "+list.get(i).isInUse()+"   "+list.get(i).getBrand();
            }else{
                msj += "   "+list.get(i).isInUse()+"  "+list.get(i).getBrand(); //Para que se vea alineado
            }
        }
        msj += "\n\n TOTAL : "+list.size()+" Billboards";
        return msj;
    }
    public String dangerousBillboardReport(String path) throws IOException{
        //String path = "C:/Users/samue/Desktop/Samuel Ibarra/APO II/Billboard";
        File folder = new File(path);
        if(!folder.exists()){
            return "\n Folder NOT FOUND";
        }
        File file = new File(path+"/report.txt");
        FileOutputStream fos = new FileOutputStream(file);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
        String data = reportDangerousBillboards();
        writer.write(data);
        writer.flush();
        writer.close();
        return data+"\n Report Generated Succesfully";
    }
    private String reportDangerousBillboards(){
        int j = 0;
        String msj = "========================================\n"+
                     "       DANGEROUS BILLBOARD REPORT       \n"+
                     "========================================\n"+
                     " The Dangerous Billboard Are: \n";
        for(int i=0;i<list.size();i++){
            int num = list.get(i).getWidth()*list.get(i).getHeight();
            if(num >= 200000){
                j++;
                msj += j+". Billboard <"+list.get(i).getBrand()+"> with area <"+num+"cm2>\n";
            }
        }
        if(j == 0){
            msj += " No Dangerous Billboards Found";
        }
        return msj;
    }
}
