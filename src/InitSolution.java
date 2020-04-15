
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author refing
 */
public class InitSolution {
    ArrayList<String[]> arrship;
    ArrayList<Ship> listship;

    ArrayList<String[]> arrberth;
    ArrayList<Berth> listberth;
    ArrayList<BerthTrans> listbertha;
    int[][] berthsch;
    
    public InitSolution(ArrayList<String[]> arrship,ArrayList<Ship> listship,ArrayList<String[]> arrberth,ArrayList<Berth> listberth,ArrayList<BerthTrans> listbertha){
        this.arrberth=arrberth;
        this.arrship=arrship;
        this.listberth=listberth;
        this.listship=listship;
        this.listbertha=listbertha;
        
        int[] berthi = new int[listship.size()];
        int[][] berthsch = new int[listship.size()][5];
        this.berthsch=berthsch;
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
           
            
            

        
        
            //B = FIRST element in list after sort
            BerthTrans pilih = filterberth.get(0);
            
            
            //ti = max(relb, ai, M)
            ti = Math.max((int)b.getArrival(), Math.max(pilih.getReleasetime(),M));
            b.setTi(ti);
            //ri = ti + hi
            ri = ti + pilih.getHandlingtime();
            b.setRi(ri);
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
    }
    public void cekhc(){
        for (int i = 0; i < berthsch.length; i++) {
            if(listship.get(i).getTi()>=(int)listship.get(i).getArrival()){
                System.out.println("loloss"+i);
            }
            if(listship.get(i).getRi()>=listship.get(i).getTi()+berthsch[i][4]){
                System.out.println("lolos"+i);
            }
        }
        //Ship            Berth       Start           End                 Cost
        //ti>ai
        //ri>ti+hi
        //ti>relk
        
    }
}
