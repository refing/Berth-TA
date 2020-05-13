package taberth;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
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
    ArrayList<Ship> gdsol;
    ArrayList<Ship> hilsol;
    public Heuristic(ArrayList<Ship> initsol){
        this.initsol=initsol;
    }
    
    public void tesswap(){
        ArrayList<Ship> sbest = new ArrayList<Ship>(initsol);
        ArrayList<Ship> stemp = new ArrayList<Ship>(initsol);
        
        double penalty1 = Util.cost(sbest);
        double penalty2 = 0;
        
        swap(stemp);
        Util.cekhc(stemp);
        
        System.out.println(" penalty best "+penalty2);
        
    }
    
    public void hill(){
        ArrayList<Ship> sbest = new ArrayList<Ship>(initsol);
        ArrayList<Ship> stemp = new ArrayList<Ship>(initsol);
        
        double penalty1 = Util.cost(sbest);
        double penalty2 = 0;
        for (int i = 0; i < 1000; i++) {
            
//            stemp=sbest;
//            swap(stemp);
//            if(Util.cekhc(stemp)==false){
//                System.out.println("invalid");
//            }
            do {
                swap(stemp);
            } while (!Util.cekhc(stemp));
            
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
    
    
    public void ils (){
        ArrayList<Ship> sbest = new ArrayList<Ship>(initsol);
        ArrayList<Ship> stemp = new ArrayList<Ship>(initsol);
        ArrayList<Ship> scandidate = new ArrayList<Ship>(initsol);
        
            
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
                shift(stemp);
            } while (!Util.cekhc(stemp));
            
            penalty2=Util.cost(stemp);
            if(penalty2 < penalty1){
                penalty1 = penalty2;
                sbest = new ArrayList<Ship>(stemp);
            }else{
                stemp = new ArrayList<Ship>(sbest);
            }
        }
        do {
            
            iteration++;
             do {
                shift(scandidate);
            } while (!Util.cekhc(scandidate));
            
            penalty2=Util.cost(scandidate);
            if(penalty2 < penalty1){
                penalty1 = penalty2;
                sbest = new ArrayList<Ship>(scandidate);
            }else{
                scandidate = new ArrayList<Ship>(sbest);
            }
            System.out.println(iteration+" cost : "+penalty1);
        } while (!(iteration==maxiteration));
        
        this.ilssol = sbest;
        System.out.println("cost : "+penalty1);
           
    }
    public void gd(){
        //initsol
        ArrayList<Ship> sbest = new ArrayList<Ship>(initsol);
        ArrayList<Ship> stemp = new ArrayList<Ship>(initsol);
        
        int cost1 = 0;
        int cost2 = 0;
        int estimatedquality = 0;
        double level = Util.cost(sbest);
        double decayrate = 0;
        int iteration = 0;
        int maxiteration = 10000;
        
        do {
            
            iteration++;
            //create neigborhood
            do {
                shift(stemp);
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
//                cost1=cost2;
                sbest = new ArrayList<Ship>(stemp);
                level = cost2;
            }else if (cost2<=level) {
//                cost1=cost2;
                sbest = new ArrayList<Ship>(stemp);
            }else
                stemp = new ArrayList<Ship>(sbest);
            //kalo ga improve, apakah kurang dari sama dengan level, kalo iya update bbest local
            level=level-decayrate;

            //stop criteria
            System.out.println(iteration+" cost "+cost2);
        } while (!(iteration==maxiteration));

        this.gdsol=sbest;
        System.out.println("cost : "+cost1);
        
    }
    
    public void ilsgd(){
        ArrayList<Ship> sbest = new ArrayList<Ship>(initsol);
        ArrayList<Ship> stemp = new ArrayList<Ship>(initsol);
        
        Random rn = new Random();
                
        int maxiteration = 1000;
        
        double penalty1 = Util.cost(sbest);
        double penalty2 = 0;
        int cost1 = 0;
        int cost2 = 0;
        
        int estimatedquality = 0;
        double level = Util.cost(sbest);
        double decayrate = 0;
        
        //hill climbing 1 cari local optima 1
        for (int i = 0; i < maxiteration; i++) {
            
//            stemp=sbest;
//            swap(stemp);
//            if(Util.cekhc(stemp)==false){
//                System.out.println("invalid");
//            }
            int numb=rn.nextInt(1); //reinforcement learning ntar masi pake sr
            switch(numb){
                case(0):
                    do {
                        shift(stemp);
                    } while (!Util.cekhc(stemp));
                    break;
                case(1):
                    do {
                        swap(stemp);
                    } while (!Util.cekhc(stemp));
                    break;
                case(2):
                    do {
                        ruincreate(stemp);
                    } while (!Util.cekhc(stemp));
                    break;
            }
            
            penalty2=Util.cost(stemp);
            if(penalty2 < penalty1){
                penalty1 = penalty2;
                sbest = new ArrayList<Ship>(stemp);
            }else{
                stemp = new ArrayList<Ship>(sbest);
            }
            System.out.println(i+" penalty best "+penalty1);
        }
        System.out.println(" penalty best "+penalty1);
        
        stemp = new ArrayList<Ship>(sbest);
        //local search pake great deluge pake local optima baru dan di ils
        for (int i = 0; i < maxiteration; i++) {
            
        
        //perturb
            //??? belum ada ruin recreate
            
            
            cost1 = Util.cost(sbest);
            int numb=rn.nextInt(1); //reinforcement learning ntar masi pake sr
            switch(numb){
                case(0):
                    do {
                        shift(stemp);
                    } while (!Util.cekhc(stemp));
                    break;
                case(1):
                    do {
                        swap(stemp);
                    } while (!Util.cekhc(stemp));
                    break;
                case(2):
                    do {
                        ruincreate(stemp);
                    } while (!Util.cekhc(stemp));
                    break;
            }
            //great deluge
            //calculate cost
            cost2 = Util.cost(stemp);

            //init level = cost terbaik
            //estimated qualiity = cost2 - cost1
            //decay rate = estimated quality/number of iteration
            estimatedquality=cost2-cost1;
            decayrate = estimatedquality/maxiteration;

            //kalo improve, update best local solution and level
            if (cost2<cost1) {
                cost1=cost2;
                sbest = new ArrayList<Ship>(stemp);
                level = cost2;
            }else if (cost2<=level) {
                cost1=cost2;
                sbest = new ArrayList<Ship>(stemp);
            }else
                stemp = new ArrayList<Ship>(sbest);
            //kalo ga improve, apakah kurang dari sama dengan level, kalo iya update bbest local
            level=level-decayrate;
            System.out.println(i+" cost gd "+cost1);
        }
        System.out.println("cost hc "+penalty1);
        System.out.println("cost gd"+cost1);
        
        
        //acceptance criteria ils bandinginnya 
        
        
       
        
    }
    
    public void hillils(){
        ArrayList<Ship> sbest = new ArrayList<Ship>(initsol);
        ArrayList<Ship> stemp = new ArrayList<Ship>(initsol);
        ArrayList<Ship> scandidate = new ArrayList<Ship>(initsol);
        
        int maxiteration = 1000;
        int iteration=0;
        
        double penalty1 = Util.cost(sbest);
        double penalty2 = 0;
        int cost1 = 0;
        int cost2 = 0;
        
        int estimatedquality = 0;
        double level = Util.cost(sbest);
        double decayrate = 0;
        
        //hill climbing 1 cari local optima 1
        for (int i = 0; i < 1000; i++) {
            
//            stemp=sbest;
//            swap(stemp);
//            if(Util.cekhc(stemp)==false){
//                System.out.println("invalid");
//            }
            do {
                swap(stemp);
            } while (!Util.cekhc(stemp));
            
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
        
        //perturbation
        int numb=0; //reinforcement learning
        switch(numb){
            case(0):
                do {
                    shift(stemp);
                } while (!Util.cekhc(stemp));
                break;
            case(1):
                do {
                    swap(stemp);
                } while (!Util.cekhc(stemp));
                break;
            case(2):
                do {
                    ruincreate(stemp);
                } while (!Util.cekhc(stemp));
                break;
        }
        
        //hill climbing lagi pake local optima baru
        for (int i = 0; i < 1000; i++) {
            do {
                swap(stemp);
            } while (!Util.cekhc(stemp));
            
            penalty2=Util.cost(stemp);
            if(penalty2 < penalty1){
                penalty1 = penalty2;
                sbest = new ArrayList<Ship>(stemp);
            }else{
                stemp = new ArrayList<Ship>(sbest);
            }
            System.out.println(i+" penalty best "+penalty2);
        }
    }
    
    public static int reinforcement(){
        int[]reinforce = new int[2];
        int max=reinforce[0];
        int id = 0;
        if (reinforce[1]>max) {
            id=1;
            max=reinforce[1];
        }else if(reinforce[2]>max){
            id=2;
            max=reinforce[2];
        }
        if(reinforce[0]==reinforce[1]&&reinforce[1]==reinforce[2]){
//            id = random next int 2;
        }
        return id;
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
    
    public void swap(ArrayList<Ship> listship){
        Random rn = new Random();
        int hi = 0;
        int angka1 = 0;
        int angka2 = 0;
        
       
        
         //pick random berth
         //pick handling time di berth
        //cek handling time yang gak nol, pick compatible berth only
        int berth1=0;
        int berth2=0;
//        int berth3=0;
//        int berth4=0;
        do {
            angka1 = rn.nextInt(listship.size()); //ngambil ship 1
            angka2 = rn.nextInt(listship.size()); //ngambil ship 2
            berth1 = listship.get(angka1).getProcessTimes()[listship.get(angka2).getBerth()];//hi ship 1 kalo ditaruh di tempatnya ship 2
            berth2 = listship.get(angka2).getProcessTimes()[listship.get(angka1).getBerth()];//hi ship 2 kalo ditaruh di tempatnya ship 1
//            berth3 = listship.get(angka1).getProcessTimes()[listship.get(angka1).getBerth()];
//            berth4 = listship.get(angka2).getProcessTimes()[listship.get(angka2).getBerth()];
        } while (berth1==0||berth2==0);
        Ship pick1 = listship.get(angka2); //pick shipp yang akan diswap
        Ship pick2 = listship.get(angka1); //pick shipp yang akan diswap
        
//        System.out.println("kapal 1 "+pick1.getShipId()+" di berth "+pick1.getBerth()+" kapal 2 "+pick2.getShipId()+" di berth "+pick2.getBerth());
        
        int berthtemp = 0;
        berthtemp = pick1.getBerth();
        pick1.setBerth(pick2.getBerth());
        pick2.setBerth(berthtemp);
//        System.out.println("swapped");
//        System.out.println("kapal 1 "+pick1.getShipId()+" di berth "+pick1.getBerth()+" kapal 2 "+pick2.getShipId()+" di berth "+pick2.getBerth());
//        
        //compute ulang ti ri hi
        countagain(listship);
    }
    public void ruincreate(ArrayList<Ship> listship){
        HashMap<Integer, ArrayList<Ship>> map = new HashMap<>(); 
        ArrayList<Ship> new0 = new ArrayList<>();
        ArrayList<Ship> new1 = new ArrayList<>();
        ArrayList<Ship> new2 = new ArrayList<>();
        ArrayList<Ship> new3 = new ArrayList<>();
        ArrayList<Ship> new4 = new ArrayList<>();
        ArrayList<Ship> new5 = new ArrayList<>();
        ArrayList<Ship> new6 = new ArrayList<>();
        ArrayList<Ship> new7 = new ArrayList<>();
        ArrayList<Ship> new8 = new ArrayList<>();
        ArrayList<Ship> new9 = new ArrayList<>();
        ArrayList<Ship> new10 = new ArrayList<>();
        
        for (int i = 0; i < listship.size(); i++) {
            switch(listship.get(i).getBerth()){
                case(0):
                    new0.add(listship.get(i));
                    map.put(0, new0);
                    break;
                case(1):
                    new1.add(listship.get(i));
                    map.put(1, new1);
                    break;
                case(2):
                    new2.add(listship.get(i));
                    map.put(2, new2);break;
                case(3):
                    new3.add(listship.get(i));
                    map.put(3, new3);break;
                case(4):
                    new4.add(listship.get(i));
                    map.put(4, new4);break;
                case(5):
                    new5.add(listship.get(i));
                    map.put(5, new5);break;
                case(6):
                    new6.add(listship.get(i));
                    map.put(6, new6);break;
                case(7):
                    new7.add(listship.get(i));
                    map.put(7, new7);break;
                case(8):
                    new8.add(listship.get(i));
                    map.put(8, new8);break;
                case(9):
                    new9.add(listship.get(i));
                    map.put(9, new9);break;
                case(10):
                    new10.add(listship.get(i));
                    map.put(10, new10);break;
            }
        }
        
//        System.out.println(map.get(3).get(0).getShipId());
        
        
        ArrayList<Ship> candidate = new ArrayList<>();
        //ruin
        Random rn = new Random();
        int ruinfactor = 20;
        do {
            //random pick berth
            int berth = rn.nextInt(11);
//            int r = rn.nextInt(ruinfactor);
            int r = rn.nextInt(map.get(berth).size())+1; //random number jumlah vessel di berth
            //pick r ship randomly di berth berth
            for (int i = 0; i < r; i++) {
                Ship pickedship = map.get(berth).get(i);
                candidate.add(pickedship);
            }
            System.out.println("RUIN FACTOR NOW = "+ruinfactor+" r now "+r);
            ruinfactor = ruinfactor - r;
        } while (ruinfactor>0);
        
        for (int i = 0; i < candidate.size(); i++) {
            System.out.println(i+". ship "+listship.get(candidate.get(i).getShipId()).getShipId()+" berth "+listship.get(candidate.get(i).getShipId()).getBerth());
        }
        
        System.out.println("size "+candidate.size());
        
        //recreate
        candidate.sort(Comparator.comparing(Ship::getArrival));
        for (int i = 0; i < candidate.size(); i++) {
            double u = rn.nextDouble();
            double beta = 0.01;
            double k = Math.log((u*beta/1-beta))/Math.log(beta);
            
            //ambil ship
            Ship pick = candidate.get(i);
            
        }
        
    }

}
    
    

