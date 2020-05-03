package taberth;


import java.util.ArrayList;
import java.util.Comparator;
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
public class Heuristic {
    ArrayList<Ship> initsol;
    ArrayList<Ship> initsol2;
    ArrayList<Ship> ilssol;
    ArrayList<Ship> gdsol;
    ArrayList<Ship> hilsol;
    public Heuristic(ArrayList<Ship> initsol){
        this.initsol=initsol;
        this.initsol2=initsol;
    }
    
    public void hill2(){
        ArrayList<Ship> sbest = new ArrayList<Ship>(initsol);
        ArrayList<Ship> stemp = new ArrayList<Ship>(initsol);
        
        double penalty1 = Util.cost(sbest);
        double penalty2 = 0;
        for (int i = 0; i < 1000; i++) {
            
//            stemp=sbest;
            shift(stemp);
//            if(Util.cekhc(stemp)==false){
//                System.out.println("invalid");
//            }
//            do {
//                stemp = shift(sbest);
//            } while (!Util.cekhc(stemp));
            
            penalty2=Util.cost(stemp);
            if(penalty2 < penalty1){
                penalty1 = penalty2;
                sbest = new ArrayList<Ship>(stemp);
//                sbest.clear();
//                sbest.addAll(stemp);
            }else{
                stemp = new ArrayList<Ship>(sbest);
//                stemp.clear();
//                stemp.addAll(sbest);
            }
            System.out.println(i+" penalty best "+penalty2);
        }
        System.out.println("best"+penalty1);
    }
    
    public void hill(){
        ArrayList<Ship> sbest = new ArrayList<Ship>();
        ArrayList<Ship> stemp = new ArrayList<Ship>();
        sbest=Util.cloneList(initsol);
//        ArrayList<Ship> asli = (ArrayList)initsol.clone();
//        ArrayList<Ship> sbest = (ArrayList)initsol.clone();
//        ArrayList<Ship> scandidate = (ArrayList)initsol.clone();
//        ArrayList<Ship> stemp = (ArrayList)initsol.clone();
        
        double penalty1 = Util.cost(sbest);
        double penalty2 = 0;
        
        for (int i = 0; i < 10000; i++) {
            
            do {
                shift(sbest);
            } while (!Util.cekhc(stemp));
            
            penalty2=Util.cost(stemp);
            if(penalty2 < penalty1){
                penalty1 = penalty2;
                sbest = stemp;
            }else{
                stemp = sbest;
            }
//            System.out.println("iteration "+i+"cost="+penalty1);
        }
        this.hilsol=sbest;
        System.out.println("cost : "+penalty1);
    }
    public void ils (){
        ArrayList<Ship> asli = (ArrayList)initsol.clone();
        ArrayList<Ship> sbest = (ArrayList)initsol.clone();
        ArrayList<Ship> scandidate = (ArrayList)initsol.clone();
        ArrayList<Ship> stemp = (ArrayList)initsol.clone();
        
        
            
        int maxiteration = 1000;
        int iteration=0;
        
        int randomindexcourse=0;
        int randomslot=0;
        
        
        double penalty1 = Util.cost(sbest);
        double penalty2 = 0;
        
        
        boolean check1 = false;
        boolean check2 = false;
        
        for (int i = 0; i < 1000; i++) {
            
            do {
                shift(sbest);
            } while (!Util.cekhc(stemp));
            
            penalty2=Util.cost(stemp);
            if(penalty2 < penalty1){
                penalty1 = penalty2;
                sbest = stemp;
            }else{
                stemp = sbest;
            }
        }
        do {
             do {
//                scandidate = shift(sbest);
            } while (!Util.cekhc(scandidate));
            
            penalty2=Util.cost(scandidate);
            if(penalty2 < penalty1){
                penalty1 = penalty2;
                sbest = scandidate;
            }else{
                scandidate = sbest;
            }
            iteration++;
        } while (!(iteration==maxiteration));
        
        this.ilssol = sbest;
        System.out.println("cost : "+Util.cost(sbest));
           
    }
    public void gd(){
        //initsol
        ArrayList<Ship> sbest = (ArrayList)initsol.clone();
        ArrayList<Ship> stemp = (ArrayList)initsol.clone();
        
        int cost1 = 0;
        int cost2 = 0;
        int estimatedquality = 0;
        float level = Util.cost(sbest);
        float decayrate = 0;
        int iteration = 0;
        int maxiteration = 10000;
        
        do {
            //create neigborhood
            do {
//                stemp = shift(sbest);
            } while (!Util.cekhc(stemp));

            //calculate cost
            cost1 = Util.cost(sbest);
            cost2 = Util.cost(stemp);

            //init level = cost terbaik
            //estimated qualiity = cost2 - cost1
            //decay rate = estimated quality/number of iteration
            estimatedquality=cost2-cost1;
            decayrate = estimatedquality/maxiteration;

            //kalo improve, update best local solution and level
            if (cost2<cost1) {
                sbest = stemp;
                level = cost2;
            }else if (cost2<=level) {
                sbest = stemp;
            }else
                stemp = sbest;
            //kalo ga improve, apakah kurang dari sama dengan level, kalo iya update bbest local
            level=level-decayrate;

            //stop criteria
            iteration++;
        } while (!(iteration==maxiteration));

        this.gdsol=sbest;
        System.out.println("cost : "+Util.cost(sbest));
        
    }
    
    
    public void shift(ArrayList<Ship> ls){
        
//        ArrayList<Ship> solution = (ArrayList)ls.clone();
//        ArrayList<Ship> shifted = (ArrayList)ls.clone();
        Random rn = new Random();
        int hi = 0;
        int angka = 0;
        int totalavailable=0;
        
        Ship pick1 = ls.get(rn.nextInt(ls.size())); //pick shipp yang akan dishift
//        System.out.println("preshift, pickeed ship"+pick1.getShipId());
         //pick random berth
         //pick handling time di berth
        //cek handling time yang gak nol, pick compatible berth only
        do {
            for (int i = 0; i < pick1.getProcessTimes().length; i++) {
                if(pick1.getProcessTimes()[i]>0){
                    totalavailable++;
                }
            }
            if (totalavailable>1) {
                do {
                    angka = rn.nextInt(11);
                } while (angka==pick1.getBerth());
            }
            if (totalavailable==1) {
                angka = pick1.getBerth();
            }
            hi = pick1.getProcessTimes()[angka];
        } while (hi==0);
        pick1.setBerth(angka);

        //compute ulang ti ri hi
        countagain(ls);

    }
    
    public void countagain(ArrayList<Ship> listship){
        ArrayList<BerthTrans> listberth2 = new ArrayList<BerthTrans>();
        
        
        for (int i = 0; i < 11; i++) {
            BerthTrans berth2 = new BerthTrans(i);
            berth2.setReleasetime(0);
            berth2.setHandlingtime(0);
            listberth2.add(berth2);
        }
        
        //Sort ship by increasing arrival time
        listship.sort(Comparator.comparing(Ship::getArrival));
        
        //forloop
        for (int i = 0; i < listship.size(); i++) {
            
            //set handling time per ship
            Ship thisship = listship.get(i); //i
            
            
            
            for (int j = 0; j < listberth2.size(); j++) {
                listberth2.get(j).setHandlingtime(thisship.getProcessTimes()[j]);
            }
            for (int j = 0; j < listberth2.size(); j++) {
                if(thisship.getArrival()>=listberth2.get(j).getReleasetime())      //??????
                    listberth2.get(j).setReleasetime(0);
            }
            
            
            BerthTrans pilih = listberth2.get(thisship.getBerth());
            
            int ti = 0;
            int ri = 0;
            
            if(pilih.getReleasetime()>0){
                thisship.setCostWait(pilih.getReleasetime()-(int)thisship.getArrival());
                ti=pilih.getReleasetime();
            }
            if(pilih.getReleasetime()==0){
                ti=(int)thisship.getArrival();
            }  
            
//            System.out.println(i+". arr = "+thisship.getArrival()+" rel = "+pilih.getReleasetime()+" M="+m);
//            System.out.println(i+". ti max = " + ti);
//            if (ti>m) {
//                System.out.println("THIS" + ti);
//            }
            thisship.setTi(ti);

            ri = ti + pilih.getHandlingtime();
            
            thisship.setHi(pilih.getHandlingtime());
            thisship.setRi(ri);

            //UPDATE RELEASE TIME DI AKIR
            listberth2.get(pilih.getId()).setReleasetime(ri);
            //if arrival time udah selesai, release time reset jadi 0
            //reset handling time --udah selalu direset di awal awal
            
           
            
    }
    }
    public void countagain2(ArrayList<Ship> listship){
        ArrayList<BerthTrans> listberth2 = new ArrayList<BerthTrans>();
        
        
        for (int i = 0; i < 11; i++) {
            BerthTrans berth2 = new BerthTrans(i);
            berth2.setReleasetime(0);
            berth2.setHandlingtime(0);
            listberth2.add(berth2);
        }
        
        //Sort ship by increasing arrival time
        listship.sort(Comparator.comparing(Ship::getArrival));
        
        //forloop
        for (int i = 0; i < listship.size(); i++) {
            
            //set handling time per ship
            Ship thisship = listship.get(i); //i
            
            
            
            for (int j = 0; j < listberth2.size(); j++) {
                listberth2.get(j).setHandlingtime(thisship.getProcessTimes()[j]);
            }
            for (int j = 0; j < listberth2.size(); j++) {
                if(thisship.getArrival()>listberth2.get(j).getReleasetime())      //??????
                    listberth2.get(j).setReleasetime(0);
            }
            
            
            BerthTrans pilih = listberth2.get(thisship.getBerth());
            
            int ti = 0;
            int ri = 0;
            
            if(pilih.getReleasetime()>0){
                thisship.setCostWait(pilih.getReleasetime()-(int)thisship.getArrival());
                ti=pilih.getReleasetime();
            }
            if(pilih.getReleasetime()==0){
                ti=(int)thisship.getArrival();
            }  
            
            thisship.setTi(ti);

            ri = ti + pilih.getHandlingtime();
            
            thisship.setHi(pilih.getHandlingtime());
            thisship.setRi(ri);

            //UPDATE RELEASE TIME DI AKIR
            listberth2.get(pilih.getId()).setReleasetime(ri);
            //if arrival time udah selesai, release time reset jadi 0
            //reset handling time --udah selalu direset di awal awal
            
           
            
    }
    }
    
    public ArrayList<Ship> swap(ArrayList<Ship> listship){
        ArrayList<Ship> solution = listship;
        ArrayList<Ship> swapped = listship;
        Random rn = new Random();
        int hi = 0;
        int angka1 = 0;
        int angka2 = 0;
        
       
        
         //pick random berth
         //pick handling time di berth
        //cek handling time yang gak nol, pick compatible berth only
        
        do {
            angka1 = rn.nextInt(listship.size());
            angka2 = rn.nextInt(listship.size());
        } while (solution.get(angka1).getHi()==0&&solution.get(angka2).getHi()==0);
        Ship pick1 = solution.get(angka1); //pick shipp yang akan diswap
        Ship pick2 = solution.get(angka2); //pick shipp yang akan diswap
        
        
        
        int berthtemp = 0;
        berthtemp = pick1.getBerth();
        pick1.setBerth(pick2.getBerth());
        pick2.setBerth(berthtemp);
        
        //compute ulang ti ri hi
//        swapped=countagain(solution);
        
        return swapped;
    }
    public void ruincreate(){
        
    }

}
    
    

