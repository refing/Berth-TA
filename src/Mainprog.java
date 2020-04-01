/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author refing
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.*;
import java.util.*;

public class Mainprog {
    
    public static void main(String[] args) throws IOException {
        
        ArrayList<String[]> arrship = new ArrayList<String[]>();
        ArrayList<Ship> listship = new ArrayList<Ship>();
        
        ArrayList<String[]> arrberth = new ArrayList<String[]>();
        ArrayList<Berth> listberth = new ArrayList<Berth>();
        
        String file = "src/instancetest/problem_10_vessels_1.txt";
        int lineNumber=0;
        String text="";
        
        
            FileReader fcr = new FileReader(file);
            BufferedReader cr = new BufferedReader(fcr);
            
//            while ((file = cr.readLine()) != null){
//                for (lineNumber = 1; lineNumber < 149; lineNumber++) {
//                    if (lineNumber == 141) {
//                      text = cr.readLine();
//                    } else
//                      cr.readLine();
//                }

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
            
        
            
            
//        System.out.println("BERTH");
//        for (int i = 0; i<arrberth.size() ; i++) {
//            System.out.println(arrberth.get(i));
//        }
        
        
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
                berth.setMaxDraft(Float.parseFloat(arrberth.get(i)[1]));
                berth.setMaxLength(Float.parseFloat(arrberth.get(i)[2]));
                berth.setMaxWidth(Float.parseFloat(arrberth.get(i)[3]));
                berth.setMaxDWT(Float.parseFloat(arrberth.get(i)[4]));
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
                ship.setDraft(Float.parseFloat(arrship.get(i)[1]));
                ship.setLength(Float.parseFloat(arrship.get(i)[2]));
                ship.setWidth(Float.parseFloat(arrship.get(i)[3]));
                ship.setDWT(Float.parseFloat(arrship.get(i)[4]));
                ship.setArrival(Float.parseFloat(arrship.get(i)[5]));
                ship.setDesDepart(Float.parseFloat(arrship.get(i)[6]));
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
        
        //PRINT BERTHLIST
//        System.out.println("BERTH");
//        for (int i = 0; i < listberth.size(); i++) {
//            System.out.println(listberth.get(i).getIdAsli());
//        }
        
        
        
        
        //constructive heur
        
        //sort ship by arrival time
        
        
        
        //find smallest rel time berth
        //find smallest cost berth
        //get index smallest
        
        //cek constrain, index smallest ke reltime, kalo 0 lanjutkan
        //kalo reltime ga 0, cari cost lain, index tadi di blok
        
//        for (int i = 0; i < berthi.length; i++) {
//            if(berthi[i] > 4) {
//                smallest = berthi[i];
//            }
//        }

//        Arrays.asList(berthi).indexOf(4);

        
        
        
//        System.out.println("sss");
//        for (int i = 0; i < berthi.length; i++) {
//            System.out.println(berthi[i]);
//        }
        
        ArrayList<BerthTrans> listbertha = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            BerthTrans bertha = new BerthTrans(i);
            bertha.setReleasetime(0);
            bertha.setHandlingtime(0);
            listbertha.add(bertha);
        }
        


        int[] berthi = new int[listship.size()];
        int[][] berthsch = new int[listship.size()][5];
        int ti = 0;
        int ri = 0;
        int M = 0;
            
        
            
        //Sort ship by increasing arrival time
        listship.sort(Comparator.comparing(Ship::getArrival));
        
        //forloop
        for (int i = 0; i < listship.size(); i++) {
            
            //set handling time per ship
            Ship b = listship.get(i); //i
            berthi = b.getProcessTimes();
            
            
            for (int j = 0; j < listbertha.size(); j++) {
                listbertha.get(j).setHandlingtime(berthi[j]);
            }
            for (int j = 0; j < listbertha.size(); j++) {
                if(b.getArrival()>listbertha.get(j).getReleasetime())      //??????
                    listbertha.get(j).setReleasetime(0);
            }
            
            //sort and filter berth by release time and handling time on available berth
            List<BerthTrans> filterberth = listbertha.stream().filter(p -> p.getHandlingtime()> 0).collect(Collectors.toList());;
            filterberth.sort(Comparator.comparing(BerthTrans::getReleasetime).thenComparing(BerthTrans::getHandlingtime));
           
            
            
            //ngeremove. unsafe
            //listbertha.removeIf(p -> p.getHandlingtime() == 0);
            
            //filter steream
//            BerthTrans berthfilter = listbertha.stream()                        // Convert to steam
//                .filter(x -> x.getHandlingtime()!= 0)        // we want "jack" only
//                .findAny()                                      // If 'findAny' then return found
//                .orElse(null);                                  // If not found, return null

            

        
        
            //B = FIRST element in list after sort
            BerthTrans pilih = filterberth.get(0);
            
            
            //ti = max(relb, ai, M)
            ti = Math.max((int)b.getArrival(), Math.max(pilih.getReleasetime(),M));
            //ri = ti + hi
            ri = ti + pilih.getHandlingtime();
                
            //M=ri
            M = ri;
            
            //UPDATE RELEASE TIME DI AKIR
            listbertha.get(pilih.getId()).setReleasetime(ri);
            //if arrival time udah selesai, release time reset jadi 0
            //reset handling time --udah selalu direset di awal awal
            
                
            
            
            
            
            
            
            
            //masukin schedule
            //id ship , id berthh , arrival ship, end ship, cost
            berthsch[i][0]=b.getShipId();
            berthsch[i][1]=pilih.getId();
            berthsch[i][2]=(int)b.getArrival();
            berthsch[i][3]=(int)b.getArrival()+pilih.getHandlingtime();
            berthsch[i][4]=pilih.getHandlingtime();
        }
        
        
        
        System.out.println("bertshcedule");
        for (int i = 0; i < berthsch.length; i++) {
            for (int j = 0; j < berthsch[i].length; j++) {
                System.out.print(berthsch[i][j] + " ");
            }
            System.out.println("");
        }
        
        int totalw = 0;
        for (int i = 0; i < berthsch.length; i++) {
            totalw = totalw + berthsch[i][4];
        }
        System.out.println("total cost = " + totalw);    
            
        
        
//Ship            Berth       Start           End                 Cost
//0		 9		0		5		5
//1		 3		3		10		7
//2		 4		9		21		12
//3		 10		9		14		5
//4		 7		11		39		28
//5		 1		11		39		28
//6		 3		14		21		7
//7		 9		17		21		4
//8		 4		32		37		5
//9		 0		35		44		9
//Cost: 110
            
          
        
        
        
    }
    
}
