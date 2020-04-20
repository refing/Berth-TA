
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
    
    int M = 0;
    
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
            b.setHi(pilih.getHandlingtime());
            b.setBerth(pilih.getId());
            
            //M=ri
            M = ri;
            this.M=M;
            
            //UPDATE RELEASE TIME DI AKIR
            listbertha.get(pilih.getId()).setReleasetime(ri);
            //if arrival time udah selesai, release time reset jadi 0
            //reset handling time --udah selalu direset di awal awal
            
           
            
    }
        
        
        
        
    }
    public void printinit(){
        //masukin schedule
            //id ship , id berthh , arrival ship, end ship, cost
        for (int i = 0; i < listship.size(); i++) {
            berthsch[i][0]=listship.get(i).getShipId();
            berthsch[i][1]=listship.get(i).getBerth();
            berthsch[i][2]=(int)listship.get(i).getArrival();
            berthsch[i][3]=(int)listship.get(i).getArrival()+listship.get(i).getHi();
            berthsch[i][4]=listship.get(i).getHi();
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
    
    public void cekhc(ArrayList<Ship> listship){
        for (int i = 0; i < berthsch.length; i++) {
            //constrain ti >= ai
            if(listship.get(i).getTi()>=(int)listship.get(i).getArrival()){
                System.out.println("lolos"+i);
            }
            //constrain ri>= ti+hi
            if(listship.get(i).getRi()>=listship.get(i).getTi()+listship.get(i).getHi()){
                System.out.println("loloss"+i);
            }
            //consntrain ti >= relk
            if(listship.get(i).getTi()>=listbertha.get(berthsch[i][1]).getReleasetime()){
                System.out.println("lolosss"+i);
            }
            
        }
        //constrain kapal 2 bisa berth kalo kapal 1 udah depart (7)
        //constrain ga tabrakan (10)
        if(conflictmatrix(listship)==true){
            System.out.println("conflict kapal = lolos");
        }else{
            System.out.println("conflict kapal = tidak lolos");
        }
        
        
        //Ship            Berth       Start           End                 Cost
        //ti>ai
        //ri>ti+hi
        //ti>relk
        
    }
    
    
    public boolean conflictmatrix(ArrayList<Ship> listship){
        int sigmasum = 0;
        int sigma = 0;
        int mib = berthsch.length-listship.size()+1;
        boolean conf = false;
        boolean depart = false;
        
        for(int i = 0; i < listship.size(); i++) {
            for(int j = i+1; j < listship.size(); j++) {
                if(listship.get(i).getRi()<=listship.get(j).getTi()){
                    sigma =1;
                    sigmasum++;
                }
            }
        }
        for (int i = 0; i < listship.size(); i++) {
            for (int j = 0; j < listship.size(); j++) {
                depart = listship.get(j).getTi()>=listship.get(i).getRi()-M-M*sigma;
            }
        }
        if (sigmasum>=mib&&depart){
            conf= true;
        }
        return conf;
    }
    
    public void generatenew(ArrayList<Ship> listship){
        ArrayList<Ship> newsol = new ArrayList();
        newsol = (ArrayList)listship.clone();
        
        newsol.get(3).setTi(0);
        
        cekhc(newsol);
    }
    
}
