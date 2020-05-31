package taberth;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author refing
 */
public class ReadFile {
//    ArrayList<String[]> arrship;
//    ArrayList<Ship> listship;
//
//    ArrayList<String[]> arrberth;
//    ArrayList<Berth> listberth;
//    
//    ArrayList<BerthTrans> listbertha;
    ArrayList<String[]> arrship = new ArrayList<>();
    ArrayList<Ship> listship = new ArrayList<>();
    static int totalship=0;
    static int totalberth=0;
    static int safetyadjacent=0;
    static int safetyopposite=0;
    static int [][] adjacentmatrix = new int[11][11];
    static int [][] oppositematrix = new int[11][11];
    public ReadFile(String file)throws IOException {
//        this.arrberth=arrberth;
//        this.arrship=arrship;
//        this.listberth=listberth;
//        this.listship=listship;
//        this.listbertha=listbertha;
        
        int lineNumber=0;
        String text="";    
        
            FileReader fcr = new FileReader(file);
            BufferedReader cr = new BufferedReader(fcr);
            
            ArrayList<String[]> opptemp = new ArrayList<String[]>();
            ArrayList<String[]> adjtemp = new ArrayList<String[]>();
            
            while ((text = cr.readLine()) != null) {
                lineNumber++;
                
                if(lineNumber == 3){
                    String[] textarr = text.split(" ");
                    totalship=Integer.parseInt(textarr[0]);
                    totalberth=Integer.parseInt(textarr[1]);
                    safetyadjacent=Integer.parseInt(textarr[2]);
                    safetyopposite=Integer.parseInt(textarr[3]);
                }
                
                //tabel matrix opposite
                else if(lineNumber >67 && lineNumber <79){
                    opptemp.add(text.split("\t"));
                }
                //tabel matrix adjacent
                else if(lineNumber >80 && lineNumber <92){
                    adjtemp.add(text.split("\t"));
                }
                
                
                //tabel ship
                //header
                //Type Draft Length Width DWT Arrival Des_depart Manoeuvring_time Cost_Wait Cost_Delay Process_times_in_berths(0=Incompatible)(matrix)
                if(lineNumber > 141){
                    arrship.add(text.split(" "));
                }
              }
            
            
            
            
            for (int i = 0; i < opptemp.size(); i++) {
                for (int j=0; j< opptemp.get(i).length;j++) {
                    oppositematrix[i][j]=Integer.parseInt(opptemp.get(i)[j]);
                }
            } 
            
            for (int i = 0; i < adjtemp.size(); i++) {
                for (int j=0; j< adjtemp.get(i).length;j++) {
                    adjacentmatrix[i][j]=Integer.parseInt(adjtemp.get(i)[j]);
                }
            }   
            
//            for (int i = 0; i < oppositematrix.length; i++) {
//                for (int j = 0; j < oppositematrix[i].length; j++) {
//                    System.out.print(oppositematrix[i][j]+" ");
//                }
//                System.out.println("");
//            }
            
            
        
//        System.out.println("SHIPS");
//        for (String[] strArr : arrship) {
//                System.out.println(Arrays.toString(strArr));
//        }
        
        //loop semua ship i
        //bikin objek ship dengan id i
        //isi atribut objek ship dari masing masing kolom pada ship i
        
        
        
        for (int i = 0; i<arrship.size() ; i++) {
            Ship ship = new Ship(i);
            for (String strArr : arrship.get(i)) {
                ship.setType(arrship.get(i)[0]);
                ship.setDraft(Double.parseDouble(arrship.get(i)[1]));
                ship.setLength(Double.parseDouble(arrship.get(i)[2]));
                ship.setWidth(Double.parseDouble(arrship.get(i)[3]));
                ship.setDWT(Double.parseDouble(arrship.get(i)[4]));
                ship.setArrival(Double.parseDouble(arrship.get(i)[5]));
                ship.setDesDepart(Double.parseDouble(arrship.get(i)[6]));
                ship.setManouvTime(Integer.parseInt(arrship.get(i)[7]));
                ship.setCostWait(Integer.parseInt(arrship.get(i)[8]));
                ship.setCostDelay(Integer.parseInt(arrship.get(i)[9]));
                
                int[] proc = new int[11]; //jumlah berth
                int j = 10;
                for (int k = 0; k < proc.length; k++) {
                    proc[k]=Integer.parseInt(arrship.get(i)[j]);
                    j++;
                }
                ship.setProcessTimes(proc);
            }
            listship.add(ship);
        }
    }
}
