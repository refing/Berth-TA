package taberth;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        
        int costils = 0;
        int costgd = 0;
        
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
                try {
                    ruincreate(stemp);
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Heuristic.class.getName()).log(Level.SEVERE, null, ex);
                }
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
        
        System.out.println("");
        System.out.println("great deluge ils");
        stemp = new ArrayList<Ship>(sbest);
        //local search pake great deluge pake local optima baru dan di ils
        
        for (int i = 0; i < maxiteration; i++) {
            ArrayList<Ship> sbestperturb = new ArrayList<Ship>(stemp);
        //perturb
            do {
                try {
                    ruincreate2(stemp);
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Heuristic.class.getName()).log(Level.SEVERE, null, ex);
                }
            } while (!Util.cekhc(stemp));
            
            
            sbest = new ArrayList<Ship>(stemp); //sbestnya gd
            stemp = new ArrayList<Ship>(sbest); //stempnya gd
            
            //great deluge
            for (int j = 0; j < 1000; j++) {
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
                    try {
                        ruincreate(stemp);
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(Heuristic.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        } while (!Util.cekhc(stemp));
                        break;
                }
                
                cost1 = Util.cost(sbest);
                //calculate cost
                cost2 = Util.cost(stemp); //yang baru dishake

                //init level = cost terbaik
                decayrate = 1;

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
            costils = Util.cost(sbestperturb);
            costgd = Util.cost(sbest);
            //bandingkan hasil gd dan perturb awal
            if (costgd<costils) {
                costils=costgd;
                stemp = new ArrayList<Ship>(sbest);
            }else
                stemp = new ArrayList<Ship>(sbestperturb);
            
        }
        System.out.println("cost hc "+penalty1);
        System.out.println("cost gd"+costgd);
        
        
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
            try {
                ruincreate(stemp);
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(Heuristic.class.getName()).log(Level.SEVERE, null, ex);
            }
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
    
    public void runruin(ArrayList<Ship> listship){
        try {
            ruincreate(listship);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Heuristic.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("");
        System.out.println("return method awal");
        for (int i = 0; i < listship.size(); i++) {
            System.out.println("ship "+listship.get(i).getShipId()+" berth "+listship.get(i).getBerth());
        }
    }
    
    public void sementaragd(){
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
        System.out.println("HILLCLIMBING INITIAL");
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
                try {
                    ruincreate(stemp);
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Heuristic.class.getName()).log(Level.SEVERE, null, ex);
                }
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
        for(int i=0;i<2;i++){
            System.out.println("PERTURBATION "+i+" GO");
        do {
                try {
                    ruincreate2(stemp);
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Heuristic.class.getName()).log(Level.SEVERE, null, ex);
                }
                    } while (!Util.cekhc(stemp));
            System.out.println("PERTURBATION "+i+" DONE");
            System.out.println("GREAT DELUGE");
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
                try {
                    ruincreate(stemp);
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Heuristic.class.getName()).log(Level.SEVERE, null, ex);
                }
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
            System.out.println("iteration "+i+" DONE");
            System.out.println("");
    }
        
    }
    public void ruincreate2(ArrayList<Ship> listship)throws CloneNotSupportedException{
        ArrayList<Berth> allberth = new ArrayList<Berth>();
        for (int i = 0; i < 11; i++) {
            Berth berth = new Berth(i);
            allberth.add(berth);
        }
        
        for (int i = 0; i < listship.size(); i++) {
            switch(listship.get(i).getBerth()){
                case(0):
                    allberth.get(0).getDaftarship().add(listship.get(i));
//                    System.out.println("masuk0");
                    break;
                case(1):
                    allberth.get(1).getDaftarship().add(listship.get(i));
//                    System.out.println("masuk1");
                    break;
                case(2):
                    allberth.get(2).getDaftarship().add(listship.get(i));
//                    System.out.println("masuk2");
                    break;
                case(3):
                    allberth.get(3).getDaftarship().add(listship.get(i));
//                    System.out.println("masuk3");
                    break;
                case(4):
                    allberth.get(4).getDaftarship().add(listship.get(i));
//                    System.out.println("masuk4");
                    break;
                case(5):
                    allberth.get(5).getDaftarship().add(listship.get(i));
//                    System.out.println("masuk5");
                    break;
                case(6):
                    allberth.get(6).getDaftarship().add(listship.get(i));
//                    System.out.println("masuk6");
                    break;
                case(7):
                    allberth.get(7).getDaftarship().add(listship.get(i));
//                    System.out.println("masuk7");
                    break;
                case(8):
                    allberth.get(8).getDaftarship().add(listship.get(i));
//                    System.out.println("masuk8");
                    break;
                case(9):
                    allberth.get(9).getDaftarship().add(listship.get(i));
//                    System.out.println("masuk9");
                    break;
                case(10):
                    allberth.get(10).getDaftarship().add(listship.get(i));
//                    System.out.println("masuk10");
                    break;
            }
        }
//        System.out.println("ship di berth 0");
//        for (int i = 0; i < allberth.get(0).getDaftarship().size(); i++) {
//            System.out.println("ship "+allberth.get(0).getDaftarship().get(i).getShipId());
//        }
//        System.out.println("ruin go");
        ArrayList<Ship> candidate = new ArrayList<>();
        //ruin
        Random rn = new Random();
        int ruinfactor = 10;
        do {
            
//            int berth= 0;
            int r = rn.nextInt(ruinfactor)+1; //random number jumlah vessel di berth
            //r is limited to maximum nnumber in berth
            
            //            System.out.println("r = "+r);
//            int r = 1;
            //pick r ship randomly di berth berth
//            ArrayList<Ship> tempcandid = new ArrayList<>();
//            for (int i = 0; i < allberth.size(); i++) {
//                System.out.println("size listship di berth "+allberth.get(i).getIdBerth()+" adalah "+allberth.get(i).getDaftarship().size());
//            }
//            System.out.println("yg kepilih");
//            System.out.println("size listship di berth "+allberth.get(berth).getIdBerth()+" adalah "+allberth.get(berth).getDaftarship().size());
            for (int i = 0; i < r; i++) {
                //random pick berth
                int berth=0;
                int bounds = 0;
                do {
                    berth = rn.nextInt(11);
                    bounds = allberth.get(berth).getDaftarship().size();
                } while (bounds==0);
                
//                    System.out.println("bounds = "+bounds);
                    int shiprandom=0;
                    shiprandom = rn.nextInt(bounds); //random shhip nya
//                    System.out.println("RANDOM RUINSHIP = "+shiprandom);
                    Ship pickedship = allberth.get(berth).getDaftarship().get(shiprandom);
                    if(!candidate.contains(pickedship)){
                        candidate.add(pickedship);
                    }
                
//                tempcandid.add(pickedship);
//                System.out.println("removed ship= "+pickedship.getShipId());
                
            }
            
            
//            System.out.println("RUIN FACTOR NOW = "+ruinfactor+" r now "+r);
            ruinfactor = ruinfactor - r;
        } while (ruinfactor>0);
//        for (int i = 0; i < candidate.size(); i++) {
//            System.out.println("removed ship "+candidate.get(i).getShipId()+" di berth "+candidate.get(i).getBerth());
//        }
//        System.out.println("size yang diremove "+candidate.size());
        for (int i = 0; i < candidate.size(); i++) {
            allberth.get(candidate.get(i).getBerth()).getDaftarship().remove(candidate.get(i));
        }
//        for (int i = 0; i < allberth.size(); i++) {
//            System.out.println("size listship di berth "+allberth.get(i).getIdBerth()+" adalah "+allberth.get(i).getDaftarship().size());
//        }
        
//        System.out.println("ruin done");
        
//        for (int i = 0; i < map.size(); i++) {
//            for (int j = 0; j < map.get(i).size(); j++) {
//                System.out.print("berthh "+i+" : "+map.get(i).get(j).getShipId()+" "+"\n");
//            }
//        }
//        System.out.println("removed ship ");
//        for (int i = 0; i < candidate.size(); i++) {
//            System.out.println(i+". ship "+listship.get(candidate.get(i).getShipId()).getShipId()+" berth "+listship.get(candidate.get(i).getShipId()).getBerth());
//        }
        
//        System.out.println("size "+candidate.size());
        
//        System.out.println("recreate go");
        ArrayList<Ship> outputan = new ArrayList<Ship>();
        candidate.sort(Comparator.comparing(Ship::getArrival));
        for (int i = 0; i < candidate.size(); i++) {
//            System.out.println("CANDIDATE "+i);
            //bikin listnya feasiblle per sip disini
            ArrayList<Ship> feasible = new ArrayList<Ship>();
            for (int j = 0; j < 11; j++) {
                ArrayList<Ship> isi = new ArrayList<Ship>();
                if (candidate.get(i).getProcessTimes()[j]>0) {
                    //berth yang compat aja
//                    System.out.println("j="+ j);
                    //list buat ngecek feasibblenya
                    
                    for (int l = 0; l < allberth.get(j).getDaftarship().size(); l++) {
//                        System.out.println(j+" yang ada di berth ini = "+ map.get(j).get(l).getShipId());
                        if (allberth.get(j).getDaftarship().size()>0) {
                            isi.add(allberth.get(j).getDaftarship().get(l));
                    }
                    }
                    Ship baru = (Ship)candidate.get(i).clone(); 
                    
                    baru.setBerth(j);
                    isi.add(baru);
                    countagain(isi);
                    if (Util.cekhc(isi)==true) {
                        baru.setHi(baru.getProcessTimes()[j]);
                        feasible.add(baru);
                    }
//                    for (int k = 0; k < feasible.size(); k++) {
//                        System.out.println("isi feasible "+feasible.get(k).getShipId());
//                    }
                    
                }
            }
//            for (int k = 0; k < feasible.size(); k++) {
//                System.out.println("isi feasible last "+feasible.get(k).getShipId());
//            }
//            sort list vessel by assignment cost
            feasible.sort(Comparator.comparing(Ship::getHi));
//            System.out.println("feasible ship ");
//            for (int z = 0; z < feasible.size(); z++) {
//                System.out.println(z+". ship "+feasible.get(z).getShipId()+" beretnya "+feasible.get(z).getBerth()+" hi "+feasible.get(z).getHi());
//            }
//            insert vessel ke yang slightly worst
            if (feasible.size()<2) {
                outputan.add(feasible.get(0));
                allberth.get(feasible.get(0).getBerth()).getDaftarship().add(feasible.get(0));
            }else{
                outputan.add(feasible.get(1));
                allberth.get(feasible.get(1).getBerth()).getDaftarship().add(feasible.get(1));
            }
        }
//        System.out.println(" ");
//        for (int k = 0; k < outputan.size(); k++) {
//            System.out.println("isi outputan last "+outputan.get(k).getShipId());
//        }
//        System.out.println("recreate done");
//        for (int i = 0; i < allberth.size(); i++) {
//            System.out.println("size listship di berth "+allberth.get(i).getIdBerth()+" adalah "+allberth.get(i).getDaftarship().size());
//        }
//        System.out.println("normalize go");
        
        ArrayList<Ship> fix = new ArrayList<Ship>();
        for (int i = 0; i < allberth.size(); i++) {
            fix.addAll(allberth.get(i).getDaftarship());
        }
        fix.sort(Comparator.comparing(Ship::getArrival));
        countagain(fix);
//        for (int i = 0; i < fix.size(); i++) {
//            System.out.println("Ship "+fix.get(i).getShipId()+" berth "+fix.get(i).getBerth()+" arrival "+fix.get(i).getArrival()+ " depart "+fix.get(i).getRi());
//        }
        Util.cekhc(fix);
//        System.out.println(fix.size());
        
        listship.clear();
        listship.addAll(fix);
//        System.out.println("perturb alldone");
    }
    
    public void ruincreate(ArrayList<Ship> listship) throws CloneNotSupportedException{
        
//        ArrayList<Ship> stemp = new ArrayList<Ship>(initsol);
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
        int ruinfactor = 5;
        do {
            //random pick berth
            int berth = rn.nextInt(11);
//            int berth= 0;
            int r = rn.nextInt(ruinfactor)+1; //random number jumlah vessel di berth
            //r is limited to maximum nnumber in berth
            
            //            System.out.println("r = "+r);
//            int r = 1;
            //pick r ship randomly di berth berth
//            ArrayList<Ship> tempcandid = new ArrayList<>();
            for (int i = 0; i < r; i++) {
                int shiprandom = rn.nextInt(map.get(berth).size()); //random shhip nya
                Ship pickedship = map.get(berth).get(shiprandom);
                if(!candidate.contains(pickedship)){
                    candidate.add(pickedship);
                }
//                tempcandid.add(pickedship);
                
                System.out.println("removed ="+pickedship.getShipId());
                
            }
            
            
            System.out.println("RUIN FACTOR NOW = "+ruinfactor+" r now "+r);
            ruinfactor = ruinfactor - r;
        } while (ruinfactor>0);
//        for (int i = 0; i < candidate.size(); i++) {
//                map.get(berth).remove(candidate.get(i));
//            }
        for (int i = 0; i < candidate.size(); i++) {
            map.get(candidate.get(i).getBerth()).remove(candidate.get(i));
        }
        
        
//        for (int i = 0; i < map.size(); i++) {
//            for (int j = 0; j < map.get(i).size(); j++) {
//                System.out.print("berthh "+i+" : "+map.get(i).get(j).getShipId()+" "+"\n");
//            }
//        }
        System.out.println("removed ship ");
        for (int i = 0; i < candidate.size(); i++) {
            System.out.println(i+". ship "+listship.get(candidate.get(i).getShipId()).getShipId()+" berth "+listship.get(candidate.get(i).getShipId()).getBerth());
        }
        
        System.out.println("size "+candidate.size());
        
//        for (int i = 0; i < candidate.get(0).getProcessTimes().length; i++) {
//            System.out.println(i+". compatible berth "+candidate.get(0).getProcessTimes()[i]);
//        }
        ArrayList<Ship> outputan = new ArrayList<Ship>();
        candidate.sort(Comparator.comparing(Ship::getArrival));
        for (int i = 0; i < candidate.size(); i++) {
            System.out.println("CANDIDATE "+i);
            //bikin listnya feasiblle per sip disini
            ArrayList<Ship> feasible = new ArrayList<Ship>();
            for (int j = 0; j < 11; j++) {
                ArrayList<Ship> isi = new ArrayList<Ship>();
                if (candidate.get(i).getProcessTimes()[j]>0) {
                    //berth yang compat aja
                    System.out.println("j="+ j);
                    //list buat ngecek feasibblenya
//                    try{
//                        
//                    }
//                    }catch(Exception e){
//                        e.printStackTrace();
//                    }
                    
                    for (int l = 0; l < map.get(j).size(); l++) {
//                        System.out.println(j+" yang ada di berth ini = "+ map.get(j).get(l).getShipId());
                        if (map.get(j).size()>0) {
                            isi.add(map.get(j).get(l));
                    }
                    }
                    Ship baru = (Ship)candidate.get(i).clone(); 
                    
                    baru.setBerth(j);
                    isi.add(baru);
                    countagain(isi);
                    if (Util.cekhc(isi)==true) {
                        baru.setHi(baru.getProcessTimes()[j]);
                        feasible.add(baru);
                    }
                    for (int k = 0; k < feasible.size(); k++) {
                        System.out.println("isi feasible "+feasible.get(k).getShipId());
                    }
                    
                }
            }
            for (int k = 0; k < feasible.size(); k++) {
                System.out.println("isi feasible last "+feasible.get(k).getShipId());
            }
//            sort list vessel by assignment cost
            feasible.sort(Comparator.comparing(Ship::getHi));
            System.out.println("feasible ship ");
            for (int z = 0; z < feasible.size(); z++) {
                System.out.println(z+". ship "+feasible.get(z).getShipId()+" beretnya "+feasible.get(z).getBerth()+" hi "+feasible.get(z).getHi());
            }
//            insert vessel ke yang slightly worst
            if (feasible.size()<2) {
                outputan.add(feasible.get(0));
                map.get(feasible.get(0).getBerth()).add(feasible.get(0));
            }else{
                outputan.add(feasible.get(1));
                map.get(feasible.get(1).getBerth()).add(feasible.get(1));
            }
        }
        System.out.println(" ");
        for (int k = 0; k < outputan.size(); k++) {
            System.out.println("isi outputan last "+outputan.get(k).getShipId());
        }
        
        
        
        System.out.println("");
        
        ArrayList<Ship> fix = new ArrayList<Ship>();
        for (int i = 0; i < map.size(); i++) {
            fix.addAll(map.get(i));
        }
        fix.sort(Comparator.comparing(Ship::getArrival));
        countagain(fix);
        for (int i = 0; i < fix.size(); i++) {
            System.out.println("Ship "+fix.get(i).getShipId()+" berth "+fix.get(i).getBerth()+" arrival "+fix.get(i).getArrival()+ " depart "+fix.get(i).getRi());
        }
        Util.cekhc(fix);
        System.out.println(fix.size());
        
        listship.clear();
        listship.addAll(fix);
//        kkk
//            double u = rn.nextDouble();
//            double beta = 0.01;
//            double k = Math.log((u*beta)/(1-beta))/Math.log(beta);
            
    }

    public void blink(){
        Random rn = new Random();
        double u = rn.nextDouble();
            double beta = 0.01;
            double k = Math.log((u*beta)/(1-beta))/Math.log(beta);
        
        
        System.out.println("k = " + k);
    }
}

    
    

