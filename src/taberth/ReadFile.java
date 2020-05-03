package taberth;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
    public ReadFile(String file, ArrayList<String[]> arrship,ArrayList<Ship> listship,ArrayList<String[]> arrberth,ArrayList<Berth> listberth, ArrayList<BerthTrans> listbertha)throws IOException {
//        this.arrberth=arrberth;
//        this.arrship=arrship;
//        this.listberth=listberth;
//        this.listship=listship;
//        this.listbertha=listbertha;
        
        int lineNumber=0;
        String text="";    
        
            FileReader fcr = new FileReader(file);
            BufferedReader cr = new BufferedReader(fcr);
            

            while ((text = cr.readLine()) != null) {
                lineNumber++;
                
                //jumlah berth = berth.size()
                //jumlah ship = ship.size()
                //safety adjacent = 10
                //safety opposite = 30
                
                //tabel berth
                //header : 
                //Type Max_Draft Max_Length Max_Width Max_DWT
                
                //berth0
                if(lineNumber >7 && lineNumber <10){
                    arrberth.add(text.split(" "));
                }
                //berth1
                if(lineNumber >11 && lineNumber <20){
                    arrberth.add(text.split(" "));
                }
                //berth2
                if(lineNumber >21 && lineNumber <24){
                    arrberth.add(text.split(" "));
                }
                //berth3
                if(lineNumber >25 && lineNumber <29){
                    arrberth.add(text.split(" "));
                }
                //berth4
                if(lineNumber >30 && lineNumber <34){
                    arrberth.add(text.split(" "));
                }
                //berth5
                if(lineNumber >35 && lineNumber <38){
                    arrberth.add(text.split(" "));
                }
                //berth6
                if(lineNumber >39 && lineNumber <42){
                    arrberth.add(text.split(" "));
                }
                //berth7
                if(lineNumber >43 && lineNumber <46){
                    arrberth.add(text.split(" "));
                }
                //berth8
                if(lineNumber >47 && lineNumber <50){
                    arrberth.add(text.split(" "));
                }
                //berth9
                if(lineNumber >51 && lineNumber <61){
                    arrberth.add(text.split(" "));
                }
                //berth10
                if(lineNumber >62 && lineNumber <66){
                    arrberth.add(text.split(" "));
                }
                
                
//                //tabel matrix opposite
//                else if(lineNumber >67 && lineNumber <79){
//                    //arrship.add(text);
//                }
//                //tabel matrix adjacent
//                else if(lineNumber >80 && lineNumber <92){
//                    //arrship.add(text);
//                }
//                //tabel special rules
//                else if(lineNumber >97 && lineNumber <119){
//                    //arrship.add(text);
//                }
//                //tabel composite rules
//                else if(lineNumber >122 && lineNumber <139){
//                    //arrship.add(text);
//                }
                //tabel ship
                //header
                //Type Draft Length Width DWT Arrival Des_depart Manoeuvring_time Cost_Wait Cost_Delay Process_times_in_berths(0=Incompatible)(matrix)
                else if(lineNumber > 141){
                    arrship.add(text.split(" "));
                }
              }
            
        
            
        
        System.out.println("SHIPS");
        for (String[] strArr : arrship) {
                System.out.println(Arrays.toString(strArr));
        }
        
        //loop semua ship i
        //bikin objek ship dengan id i
        //isi atribut objek ship dari masing masing kolom pada ship i
        
        for (int i = 0; i < arrberth.size(); i++) {
            Berth berth = new Berth(i);
            for (String strArr : arrberth.get(i)){
                berth.setType(arrberth.get(i)[0]);
                berth.setMaxDraft(Double.parseDouble(arrberth.get(i)[1]));
                berth.setMaxLength(Double.parseDouble(arrberth.get(i)[2]));
                berth.setMaxWidth(Double.parseDouble(arrberth.get(i)[3]));
                berth.setMaxDWT(Double.parseDouble(arrberth.get(i)[4]));
           }
            listberth.add(berth);
        }
        
        for (int i = 0; i<arrship.size() ; i++) {
            Ship ship = new Ship(i);
            for (String strArr : arrship.get(i)) {
//                try{
//                    
//                }catch (NumberFormatException nfe){
//                  System.out.println("NumberFormatException: " + nfe.getMessage());
//                }
                
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
//        for (Object obj : listship) {
//               System.out.println(obj);
//        }

//        System.out.println("SHIP");
//        for (int i = 0; i < listship.size(); i++) {
//            System.out.println(listship.get(i).getProcessTimes());
//        }
//        System.out.println("");
        
        //kasi 2nd id berth
        for (int i = 0; i < listberth.size(); i++) {
            if(i<2){
                listberth.get(i).setIdAsli(0);
            }
            else if(i>1&&i<10){
                listberth.get(i).setIdAsli(1);
            }
            else if(i>9&&i<12){
                listberth.get(i).setIdAsli(2);
            }
            else if(i>11&&i<15){
                listberth.get(i).setIdAsli(3);
            }
            else if(i>14&&i<18){
                listberth.get(i).setIdAsli(4);
            }
            else if(i>17&&i<20){
                listberth.get(i).setIdAsli(5);
            }
            else if(i>19&&i<22){
                listberth.get(i).setIdAsli(6);
            }
            else if(i>21&&i<24){
                listberth.get(i).setIdAsli(7);
            }
            else if(i>23&&i<26){
                listberth.get(i).setIdAsli(8);
            }
            else if(i>25&&i<35){
                listberth.get(i).setIdAsli(9);
            }
            else if(i>34&&i<=37){
                listberth.get(i).setIdAsli(10);
            }
        }
        
        for (int i = 0; i < 11; i++) {
            BerthTrans bertha = new BerthTrans(i);
            bertha.setReleasetime(0);
            bertha.setHandlingtime(0);
            listbertha.add(bertha);
        }
    }
}
