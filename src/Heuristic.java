
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
    ArrayList<Ship> ilssol;
    public Heuristic(ArrayList<Ship> initsol){
        this.initsol=initsol;
    }
    
    public void ils (){
        ArrayList<Ship> asli = (ArrayList)initsol.clone();
        ArrayList<Ship> sbest = (ArrayList)initsol.clone();
        ArrayList<Ship> scandidate = (ArrayList)initsol.clone();
        ArrayList<Ship> stemp = (ArrayList)initsol.clone();
        
        
        //s* <- S
        
        //k <- 1
        
            
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
                stemp = shift(sbest);
            } while (!Util.cekhc(stemp));
            
            penalty2=Util.cost(stemp);
            if(penalty2 < penalty1){
                penalty1 = penalty2;
                sbest = stemp;
            }else{
                stemp = sbest;
            }
        }
        this.ilssol=sbest;
        System.out.println("cost : "+Util.cost(sbest));
        
//        double penalty4=penalty(conf, scandidate, stu);
//        int scandidate2[][]=scandidate.clone();
//        boolean terminate = false;
//        while(!terminate){
//            iteration++;
//            
//            
//            //perturb
//            //swap 2
//            int temp=0;
//            do{
//                rindex1 = r.nextInt(cour);
//                rindex2 = r.nextInt(cour);
//                check1=!issafe2(rindex1, scandidate2[rindex2][1], conf, scandidate2);
//                check2=!issafe2(rindex2, scandidate2[rindex1][1], conf, scandidate2);
//
//            }while(rindex1==rindex2&&check1&&check2);
//            
//            temp=scandidate2[rindex2][1];
//            scandidate2[rindex2][1]=scandidate2[rindex1][1];
//            scandidate2[rindex1][1]=temp;
//            
//            check1 = false;
//            check2 = false;
//            
//            
//            //hc lagi
//            for (int i = 0; i < 1000; i++) {
//            
//                do{
//                    randomindexcourse = r.nextInt(cour);
//                    randomslot=r.nextInt(max);
//                    check1=!issafe2(randomindexcourse, randomslot, conf, scandidate2);
//                }while(check1);
//                check1 = false;
//
//                timeslottemp[randomindexcourse][1] = randomslot;
//                penalty2 = penalty(conf, timeslottemp, stu);
//                if(penalty1 > penalty2){
//                    penalty1 = penalty2;
//                    scandidate2[randomindexcourse][1] = timeslottemp[randomindexcourse][1];
//                }else{
//                    timeslottemp[randomindexcourse][1] = scandidate2[randomindexcourse][1];
//                }
//            }
//                
////            if(penalty1<penalty3){
////               sbest = scandidate;
////               penalty3=penalty1;
////            }
//            
//            
//            if (iteration == maxiteration) {
//                terminate = true;
//            }
//            System.out.println("iteration "+iteration+" penalty "+ penalty1);
//        }
//        
//            //return sbest
//            export(sbest,filename,"ils");
////            penalty3=penalty(conf, sbest, stu);
//
//            System.out.println("penalty initial solution: "+init);
//            System.out.println("penalty terbaik dari ils: "+ penalty1);
//
//            double delta = 0;
//            delta = ((init-penalty1)/penalty1)*100;
//            System.out.println("improvement : " +delta+ "%");
//            System.out.println("max timeslot : " + printmaxts(sbest));
    }
    
    public void gd(){
        
    }
    
    public void generatenew(ArrayList<Ship> listship){
        ArrayList<Ship> newsol = new ArrayList();
        newsol = (ArrayList)listship.clone();
        
        //newsol.get(3).setTi(0);
        //cekhc(newsol);
        
        shift(newsol);
    }
    public ArrayList<Ship> shift(ArrayList<Ship> listship){
        ArrayList<Ship> solution = listship;
        Random rn = new Random();
        int hi = 0;
        int angka = 0;
        
        Ship pick1 = solution.get(rn.nextInt(listship.size())); //pick shipp yang akan dishift
        
         //pick random berth
         //pick handling time di berth
        //cek handling time yang gak nol, pick compatible berth only
        
        do {
            angka = rn.nextInt(11);
            hi = pick1.getProcessTimes()[angka];
        } while (hi>0);
        pick1.setBerth(9);

        
        //compute ulang ti ri hi
        countagain(solution);
//        if(Util.cekhc(listship)){
//            
//        }
        
        return solution;
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
                if(thisship.getArrival()>listberth2.get(j).getReleasetime())      //??????
                    listberth2.get(j).setReleasetime(0);
            }
            
            
            BerthTrans pilih = listberth2.get(thisship.getBerth());
            
            int ti = 0;
            int ri = 0;
//            int m = 0;
            //ti = max(relb, ai, M)
            
            if(pilih.getReleasetime()>0){
                thisship.setCostWait(pilih.getReleasetime()-(int)thisship.getArrival());
                ti=pilih.getReleasetime();
            }
            if(pilih.getReleasetime()==0){
                ti=(int)thisship.getArrival();
            }  
            
            //ti = Math.max((int)thisship.getArrival(), Math.max(pilih.getReleasetime(),m));
//            System.out.println(i+". arr = "+thisship.getArrival()+" rel = "+pilih.getReleasetime()+" M="+M);
//            System.out.println(i+". ti max = " + ti);
//            if (ti>m) {
//                System.out.println("THIS" + ti);
//            }
            thisship.setTi(ti);
//            thisship.setRti((int)thisship.getArrival()+thisship.getCostWait());
            //ri = ti + hi
            ri = ti + pilih.getHandlingtime();
            
            thisship.setHi(pilih.getHandlingtime());
            thisship.setRi(ri);
//            thisship.setRri(thisship.getRti()+thisship.getHi());
            thisship.setBerth(pilih.getId());
            
            //M=ri
//            m = ri;
//            this.M=m;
//            System.out.println(i+". Mnow = " + m);
            //UPDATE RELEASE TIME DI AKIR
            listberth2.get(pilih.getId()).setReleasetime(ri);
            //if arrival time udah selesai, release time reset jadi 0
            //reset handling time --udah selalu direset di awal awal
            
           
            
    }
    }
    
    public void swap(){
        
    }
}
