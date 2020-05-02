
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Random;
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
    
    static int M = 0;
    
    public InitSolution(ArrayList<String[]> arrship,ArrayList<Ship> listship,ArrayList<String[]> arrberth,ArrayList<Berth> listberth,ArrayList<BerthTrans> listbertha){
        this.arrberth=arrberth;
        this.arrship=arrship;
        this.listberth=listberth;
        this.listship=listship;
        this.listbertha=listbertha;
        
        
    }    
    
    public ArrayList<Ship> insol1(){
        int[] berthi = new int[listship.size()];
        
        int ti = 0;
        int ri = 0;
        int M = 0;
        
        boolean check=false;
            
        //Sort ship by increasing arrival time
        listship.sort(Comparator.comparing(Ship::getArrival));
        
        //forloop
        for (int i = 0; i < listship.size(); i++) {
            
            //set handling time per ship
            Ship thisship = listship.get(i); //i
            berthi = thisship.getProcessTimes();
            
            
            for (int j = 0; j < listbertha.size(); j++) {
                listbertha.get(j).setHandlingtime(berthi[j]);
            }
            for (int j = 0; j < listbertha.size(); j++) {
                if(thisship.getArrival()>listbertha.get(j).getReleasetime())      //??????
                    listbertha.get(j).setReleasetime(0);
            }
            
            //sort and filter berth by release time and handling time on available berth
            List<BerthTrans> filterberth = listbertha.stream().filter(p -> p.getHandlingtime()> 0).collect(Collectors.toList());;
            filterberth.sort(Comparator.comparing(BerthTrans::getReleasetime).thenComparing(BerthTrans::getHandlingtime));
           
            
            

        
        
            //B = FIRST element in list after sort
            BerthTrans pilih = filterberth.get(0);
            
            //ti = max(relb, ai, M)
            ti = Math.max((int)thisship.getArrival(), Math.max(pilih.getReleasetime(),M));
//            System.out.println(i+". arr = "+thisship.getArrival()+" rel = "+pilih.getReleasetime()+" M="+M);
//            System.out.println(i+". ti max = " + ti);
//            if (ti>M) {
//                System.out.println("THIS" + ti);
//            }
            thisship.setTi(ti);
            thisship.setRti((int)thisship.getArrival()+thisship.getCostWait());
            //ri = ti + hi
            ri = ti + pilih.getHandlingtime();
            thisship.setHi(pilih.getHandlingtime());
            thisship.setRi(ri);
            
            thisship.setRri(thisship.getRti()+thisship.getHi());
            thisship.setBerth(pilih.getId());
            
            //M=ri
            M = ri;
            this.M=M;
//            System.out.println(i+". Mnow = " + M);
            //UPDATE RELEASE TIME DI AKIR
            listbertha.get(pilih.getId()).setReleasetime(ri);
            //if arrival time udah selesai, release time reset jadi 0
            //reset handling time --udah selalu direset di awal awal
            
           
            
    }
        
        
        return listship;
    }
    
    public void insol2(){
        int[] berthi = new int[listship.size()];
        
        int ti = 0;
        int ri = 0;
        int M = 0;
            
        
        
        //Sort ship by increasing arrival time
        listship.sort(Comparator.comparing(Ship::getArrival));
        
        //forloop
        for (int i = 0; i < listship.size(); i++) {
            
            //set handling time per ship
            Ship thisship = listship.get(i); //i
            berthi = thisship.getProcessTimes();
            
            
            for (int j = 0; j < listbertha.size(); j++) {
                listbertha.get(j).setHandlingtime(berthi[j]);
            }
            for (int j = 0; j < listbertha.size(); j++) {
                if(thisship.getArrival()>listbertha.get(j).getReleasetime())      //??????
                    listbertha.get(j).setReleasetime(0);
            }
            
            //sort and filter berth by release time and handling time on available berth
            List<BerthTrans> filterberth = listbertha.stream().filter(p -> p.getHandlingtime()> 0).collect(Collectors.toList());;
            filterberth.sort(Comparator.comparing(BerthTrans::getReleasetime).thenComparing(BerthTrans::getHandlingtime));
           
            
            

        
        
            //B = FIRST element in list after sort
            BerthTrans pilih = filterberth.get(0);
            
            if(pilih.getReleasetime()>0){
                thisship.setCostWait(pilih.getReleasetime()-(int)thisship.getArrival());
                ti=pilih.getReleasetime();
            }
            if(pilih.getReleasetime()==0){
                ti=(int)thisship.getArrival();
            }    
            //ti = max(relb, ai, M)
            //ti = Math.max((int)thisship.getArrival(), Math.max(pilih.getReleasetime(),M));
            System.out.println(i+". arr = "+thisship.getArrival()+" rel = "+pilih.getReleasetime()+" M="+M);
            System.out.println(i+". ti max = " + ti);
            if (ti>M) {
                System.out.println("THIS" + ti);
            }
            thisship.setTi(ti);
            thisship.setRti((int)thisship.getArrival()+thisship.getCostWait());
            //ri = ti + hi
            ri = ti + pilih.getHandlingtime();
            thisship.setHi(pilih.getHandlingtime());
            thisship.setRi(ri);
            
            thisship.setRri(thisship.getRti()+thisship.getHi());
            thisship.setBerth(pilih.getId());
            
            //M=ri
            M = ri;
            this.M=M;
            System.out.println(i+". Mnow = " + M);
            //UPDATE RELEASE TIME DI AKIR
            listbertha.get(pilih.getId()).setReleasetime(ri);
            //if arrival time udah selesai, release time reset jadi 0
            //reset handling time --udah selalu direset di awal awal
            
           
            
    }
        
    }
    
    
    
    
    
}
