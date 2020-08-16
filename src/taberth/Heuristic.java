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
    long besttimer =0;
    long startimer=0;
    long endtimer=0;
    long hctimerstart =0;
    long hctimerend = 0;
    long gdtimerstart = 0;
    long gdtimerend = 0;
    long gdbest = 0;
    long hcbest = 0;
    
    
    
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
    
    public void hill(String filename, int run){
        long hctimerstart   = System.nanoTime();
        this.hctimerstart = hctimerstart;
        ArrayList<Ship> sbest = Util.cloneList(initsol);
        ArrayList<Ship> stemp = Util.cloneList(initsol);
        int[] traj = new int[1000000];
        ArrayList<Double> tra = new ArrayList<>();
        
        double penalty1 = Util.cost(sbest);
//        System.out.println(""+penalty1);
        int penalty2 = 0;
        for (int i = 0; i < 2500500; i++) {
            
//            stemp=sbest;
//            swap(stemp);
//            if(Util.cekhc(stemp)==false){
//                System.out.println("invalid");
//            }
            do {
                shift(stemp);
            } while (!Util.cekhc(stemp));
            
            penalty2=Util.cost(stemp);
            if(penalty2 < penalty1){
                penalty1 = penalty2;
                sbest = Util.cloneList(stemp);
                long hctimerbest   = System.nanoTime();
                this.hcbest = hctimerbest;
//                sbest.clear();
//                sbest.addAll(stemp);
            }else{
                stemp = Util.cloneList(sbest);
//                stemp.clear();
//                stemp.addAll(sbest);
            }
//            if (i%10000==0) {
//                System.out.println(i+" penalty saat ini "+penalty2);
//            }
            if (i%500==0) {
                tra.add(penalty1);
            }
            
        }
        System.out.println(""+tra.size());
        long hctimerend   = System.nanoTime();
        this.hctimerend = hctimerend;
        this.hilsol=Util.cloneList(sbest);
        System.out.println("best"+penalty1);
        Mainprog.grouptrj[run]=tra;
//        Mainprog.trj.add(traj);
//        Util.trajectory2(tra, filename, run, "hc");
    }
    
    
    
    public void gd(String filename, int run){
            ArrayList<Ship> sbestgd = Util.cloneList(initsol); //sbestnya gd
            ArrayList<Ship> stempgd = Util.cloneList(initsol); //stempnya gd
            ArrayList<Ship> bestest = Util.cloneList(initsol);
            int[] traj = new int[1000000];
            ArrayList<Double> tra = new ArrayList<>();
            Random rn = new Random();
            int gditer = 5000;
            double bbest = 0;
            double cost1 = 0;
            double cost2 = 0;
            
            double level = Util.cost(sbestgd);
            double desiredvalue = 0.98*level;
            double decayrate = (level-desiredvalue)/gditer;
            
            bbest = Util.cost(bestest);
            cost1 = Util.cost(sbestgd);
            
            long gdtimerstart   = System.nanoTime();
            this.gdtimerstart=gdtimerstart;
            //great deluge
            for (int j = 0; j < gditer; j++) {
                int numb=rn.nextInt(1); //reinforcement learning ntar masi pake sr
                switch(numb){
                    case(0):
                        do {
                            shift(stempgd);
                        } while (!Util.cekhc(stempgd));
                        break;
                    case(1):
                        do {
                            swap(stempgd);
                        } while (!Util.cekhc(stempgd));
                        break;
//                    case(2):
//                        do {
//                    try {
//                        ruincreate(stempgd);
//                    } catch (CloneNotSupportedException ex) {
//                        Logger.getLogger(Heuristic.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                        } while (!Util.cekhc(stempgd));
//                        break;
                }
                
                //calculate cost
                cost2 = Util.cost(stempgd); //yang baru dishake

                //init level = cost terbaik
                //BEST OF THE BEBST
                
                //kalo improve, update best local solution and level
                if (cost2<cost1) {
                    cost1=cost2;
                    sbestgd = Util.cloneList(stempgd);
                    level = cost2;
                }else if (cost2<=level) {
                    cost1=cost2;
                    sbestgd = Util.cloneList(stempgd);
                }else
                    stempgd = Util.cloneList(sbestgd);
                if (cost2<bbest) {
                    bbest=cost2;
                    bestest = Util.cloneList(stempgd);
                    long gdbest   = System.nanoTime();
                    this.gdbest = gdbest;
                }
                //kalo ga improve, apakah kurang dari sama dengan level, kalo iya update bbest local
                level=level-decayrate;
//                if (j%100==0) {
//                    System.out.println(j+" gd penalty saat ni "+cost2);
//                }
//               if (j<5000) {
                tra.add(cost1);
//            }
            }
            long gdtimerend   = System.nanoTime();
            this.gdtimerend=gdtimerend;
//            System.out.println("bestest cost: "+bbest);
            this.gdsol=Util.cloneList(bestest);
            Mainprog.grouptrj[run]=tra;
//        Util.trajectory2(tra, filename, run, "gd"+decayrate);
    }
    
    public void ilshc(String filename, int run){
        long starttimer = System.nanoTime();
        this.startimer=starttimer;
        long endtimer =0;
        long besttimer=0;
        
        ArrayList<Double> tra = new ArrayList<>();
        
        
        ArrayList<Ship> sbest = Util.cloneList(initsol);
        ArrayList<Ship> stemp = Util.cloneList(initsol);
            
        double penalty1 = Util.cost(sbest);
        double penalty2 = 0;
        
        
        Random rn = new Random();
                
        int hciter = 500;
        
        
        double costils = 0;
        double costgd = 0;
        double costbest = 0;
        
        
        
        long hctimerstart   = System.nanoTime();
        this.hctimerstart = hctimerstart;
        
        //hill climbing 1 cari local optima 1
        for (int i = 0; i < hciter; i++) {
            
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
//                case(2):
//                    do {
//                try {
//                    ruincreate(stemp);
//                } catch (CloneNotSupportedException ex) {
//                    Logger.getLogger(Heuristic.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                    } while (!Util.cekhc(stemp));
//                    break;
            }
            
            penalty2=Util.cost(stemp);
            if(penalty2 < penalty1){
                penalty1 = penalty2;
                sbest = Util.cloneList(stemp);
                long hcbest   = System.nanoTime();
                this.hcbest=hcbest;
            }else{
                stemp = Util.cloneList(sbest);
            }
            
//            if (i%1000==0) {
//                System.out.println(i+" hc penalty best "+penalty1);
//            }
//            System.out.println(i+" penalty best "+penalty1);
        }
        long hctimerend   = System.nanoTime();
        this.hctimerend=hctimerend;
//        System.out.println(" penalty best "+penalty1);
        this.hilsol=Util.cloneList(sbest);
        tra.add(penalty1);
//        System.out.println("1hc done");
//        System.out.println("great deluge ils");
        
        //local search pake great deluge pake local optima baru dan di ils
        ArrayList<Ship> perturb = Util.cloneList(sbest);
        ArrayList<Ship> bestperturb = Util.cloneList(sbest);
        costils = Util.cost(perturb);
        costbest = Util.cost(bestperturb);
        for (int i = 0; i < 5000; i++) {
//            System.out.println("ils "+i);
        //perturb
            do {
                try {
                    ruincreate2(perturb);
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Heuristic.class.getName()).log(Level.SEVERE, null, ex);
                }
            } while (!Util.cekhc(perturb));
//            System.out.println(i+"pre perturb "+Util.cost(perturb));
            
            ArrayList<Ship> sbest2 = Util.cloneList(perturb); //sbestnya gd
            ArrayList<Ship> stemp2 = Util.cloneList(perturb); //stempnya gd
            
            penalty1 = Util.cost(sbest2);
            for (int j = 0; j < hciter; j++) {
            
//            stemp=sbest;
//            swap(stemp);
//            if(Util.cekhc(stemp)==false){
//                System.out.println("invalid");
//            }
            int numb=rn.nextInt(1); //reinforcement learning ntar masi pake sr
            switch(numb){
                case(0):
                    do {
                        shift(stemp2);
                    } while (!Util.cekhc(stemp2));
                    break;
                case(1):
                    do {
                        swap(stemp2);
                    } while (!Util.cekhc(stemp2));
                    break;
//                case(2):
//                    do {
//                try {
//                    ruincreate(stemp2);
//                } catch (CloneNotSupportedException ex) {
//                    Logger.getLogger(Heuristic.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                    } while (!Util.cekhc(stemp2));
//                    break;
            }
            
            penalty2=Util.cost(stemp2);
            if(penalty2 < penalty1){
                penalty1 = penalty2;
                sbest2 = Util.cloneList(stemp2);
            }else{
                stemp2 = Util.cloneList(sbest2);
            }
//            if (i%1000==0) {
//                System.out.println(i+" hc penalty best "+penalty1);
//            }
//            System.out.println(i+" penalty best "+penalty1);
        }
            costgd = Util.cost(sbest2);
            if (costgd<costils) {
                costils=costgd;
                perturb = Util.cloneList(sbest2);
//                System.out.println(i+"change"+costils);
            }
            if (costils<costbest) {
                costbest=costils;
                bestperturb=Util.cloneList(perturb);
                besttimer   = System.nanoTime();
                this.besttimer=besttimer;
//                System.out.println(besttimer);
            }
//            System.out.println(i+" cost ils "+costils);
//            if (i%5==0) {
//                    System.out.println(i+" penalty best "+costils);
//                    traj[i]=costils;
//                }
//            Mainprog.trj.add(traj);
tra.add(costils);
        
        }
        this.ilssol=Util.cloneList(bestperturb);
//        System.out.println("cost hc "+penalty1);
//        System.out.println("cost ilsgd "+costils);
        endtimer   = System.nanoTime();
        
        
        this.endtimer = endtimer;
        //acceptance criteria ils bandinginnya 
        Mainprog.grouptrj[run]=tra;
//        Util.trajectory2(tra, filename, run, "ilshc");
        
       
        
    }
    public void ilshcrl(String filename, int run){
        long starttimer = System.nanoTime();
        this.startimer=starttimer;
        long endtimer =0;
        long besttimer=0;
        
        ArrayList<Integer> tra = new ArrayList<>();
        
        int[]reinforce = new int[2];
        reinforce[0]=0;
        reinforce[1]=0;
        
        ArrayList<Ship> sbest = Util.cloneList(initsol);
        ArrayList<Ship> stemp = Util.cloneList(initsol);
            
        int penalty1 = Util.cost(sbest);
        int penalty2 = 0;
        
        
        Random rn = new Random();
                
        int maxiteration = 5000;
        
        int cost1 = 0;
        int cost2 = 0;
        
        int costils = 0;
        int costgd = 0;
        int costbest = 0;
        
        int bbest = 0;
        
        
        double level = Util.cost(sbest);
        double decayrate = 5;
        
        
        long hctimerstart   = System.nanoTime();
        this.hctimerstart = hctimerstart;
        
        //hill climbing 1 cari local optima 1
        for (int i = 0; i < 200; i++) {
            
//            stemp=sbest;
//            swap(stemp);
//            if(Util.cekhc(stemp)==false){
//                System.out.println("invalid");
//            }
            int numb=0; //rl
                if(reinforce[0]>reinforce[1]){
                    numb=0;
                }else if(reinforce[1]>reinforce[0]){
                    numb=1;
                }else{
                    numb=rn.nextInt(1);
                }
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
//                case(2):
//                    do {
//                try {
//                    ruincreate(stemp);
//                } catch (CloneNotSupportedException ex) {
//                    Logger.getLogger(Heuristic.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                    } while (!Util.cekhc(stemp));
//                    break;
            }
            
            penalty2=Util.cost(stemp);
            if(penalty2 < penalty1){
                penalty1 = penalty2;
                sbest = Util.cloneList(stemp);
                reinforce[numb]++;
                long hcbest   = System.nanoTime();
                this.hcbest=hcbest;
            }else{
                stemp = Util.cloneList(sbest);
                reinforce[numb]--;
            }
//            if (i%1000==0) {
//                System.out.println(i+" hc penalty best "+penalty1);
//            }
//            System.out.println(i+" penalty best "+penalty1);
        }
        
        long hctimerend   = System.nanoTime();
        this.hctimerend=hctimerend;
//        System.out.println(" penalty best "+penalty1);
        this.hilsol=Util.cloneList(sbest);
        reinforce[0]=0;
        reinforce[1]=0;
        tra.add(penalty1);
//        System.out.println("1hc done");
//        System.out.println("great deluge ils");
        
        //local search pake great deluge pake local optima baru dan di ils
        ArrayList<Ship> perturb = Util.cloneList(sbest);
        ArrayList<Ship> bestperturb = Util.cloneList(sbest);
        costils = Util.cost(perturb);
        costbest = Util.cost(bestperturb);
        for (int i = 0; i < 5000; i++) {
//            System.out.println("ils "+i);
        //perturb
            do {
                try {
                    ruincreate2(perturb);
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Heuristic.class.getName()).log(Level.SEVERE, null, ex);
                }
            } while (!Util.cekhc(perturb));
//            System.out.println(i+"pre perturb "+Util.cost(perturb));
            
            ArrayList<Ship> sbest2 = Util.cloneList(perturb); //sbestnya gd
            ArrayList<Ship> stemp2 = Util.cloneList(perturb); //stempnya gd
            
            penalty1 = Util.cost(sbest2);
            for (int j = 0; j < 200; j++) {
            
//            stemp=sbest;
//            swap(stemp);
//            if(Util.cekhc(stemp)==false){
//                System.out.println("invalid");
//            }
                int numb=0; //rl
                if(reinforce[0]>reinforce[1]){
                    numb=0;
                }else if(reinforce[1]>reinforce[0]){
                    numb=1;
                }else{
                    numb=rn.nextInt(1);
                }
            switch(numb){
                case(0):
                    do {
                        shift(stemp2);
                    } while (!Util.cekhc(stemp2));
                    break;
                case(1):
                    do {
                        swap(stemp2);
                    } while (!Util.cekhc(stemp2));
                    break;
//                case(2):
//                    do {
//                try {
//                    ruincreate(stemp2);
//                } catch (CloneNotSupportedException ex) {
//                    Logger.getLogger(Heuristic.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                    } while (!Util.cekhc(stemp2));
//                    break;
            }
            
            penalty2=Util.cost(stemp2);
            if(penalty2 < penalty1){
                penalty1 = penalty2;
                sbest2 = Util.cloneList(stemp2);
                reinforce[numb]++;
            }else{
                stemp2 = Util.cloneList(sbest2);
                reinforce[numb]--;
            }
//            if (i%1000==0) {
//                System.out.println(i+" hc penalty best "+penalty1);
//            }
//            System.out.println(i+" penalty best "+penalty1);
        }
            costgd = Util.cost(sbest2);
            if (costgd<costils) {
                costils=costgd;
                perturb = Util.cloneList(sbest2);
//                System.out.println(i+"change"+costils);
            }
            if (costils<costbest) {
                costbest=costils;
                bestperturb=Util.cloneList(perturb);
                besttimer   = System.nanoTime();
                this.besttimer=besttimer;
//                System.out.println(besttimer);
            }
//            System.out.println(i+" cost ils "+costils);
//            if (i%5==0) {
//                    System.out.println(i+" penalty best "+costils);
//                    traj[i]=costils;
//                }
//            Mainprog.trj.add(traj);
            tra.add(costils);
        
        }
        this.ilssol=Util.cloneList(bestperturb);
//        System.out.println("cost hc "+penalty1);
//        System.out.println("cost ilsgd "+costils);
        endtimer   = System.nanoTime();
        
        
        this.endtimer = endtimer;
        //acceptance criteria ils bandinginnya 
//        Util.trajectory2(tra, filename, run, "ilshcrl");
        
       
        
    }
    public void ilsgd(String filename, int run, double desired){
        long starttimer = System.nanoTime();
        this.startimer=starttimer;
        long endtimer =0;
        long besttimer=0;
        
        
        ArrayList<Double> tra = new ArrayList<>();
        
        
        ArrayList<Ship> sbest = Util.cloneList(initsol);
        ArrayList<Ship> stemp = Util.cloneList(initsol);
        ArrayList<Ship> bestes = Util.cloneList(initsol);
            
        double penaltybest = Util.cost(bestes);
        double penalty1 = Util.cost(sbest);
        double penalty2 = 0;
        
        
        Random rn = new Random();
                
        int gditer = 500;
        
        double cost1 = 0;
        double cost2 = 0;
        
        double costils = 0;
        double costgd = 0;
        double costbest = 0;
        
        double bbest = 0;
        System.out.println(""+desired);
        double level = Util.cost(sbest);
        double desiredvalue = desired*level;
        double decayrate = 0.0000000000000005;
//        System.out.println(""+decayrate);
        
            long gdtimerstart   = System.nanoTime();
            this.gdtimerstart=gdtimerstart;
            //great deluge
            for (int j = 0; j < gditer; j++) {
                int numb=rn.nextInt(1); //sr
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
//                    case(2):
//                        do {
//                    try {
//                        ruincreate(stemp);
//                    } catch (CloneNotSupportedException ex) {
//                        Logger.getLogger(Heuristic.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                        } while (!Util.cekhc(stemp));
//                        break;
                }
                
                //calculate cost
                penalty2 = Util.cost(stemp); //yang baru dishake

                //init level = cost terbaik
                //BEST OF THE BEBST
                
                //kalo improve, update best local solution and level
                if (penalty2<penalty1) {
                    penalty1=penalty2;
                    sbest = Util.cloneList(stemp);
                    level = penalty2;
                }else if (penalty2<=level) {
                    penalty1=penalty2;
                    sbest = Util.cloneList(stemp);
                }else
                    stemp = Util.cloneList(sbest);
                if (penalty2<penaltybest) {
                    penaltybest=penalty2;
                    bestes = Util.cloneList(stemp);
                    long gdbest   = System.nanoTime();
                    this.gdbest = gdbest;
                }
                //kalo ga improve, apakah kurang dari sama dengan level, kalo iya update bbest local
                level=level-decayrate;
                System.out.println("iterasi "+j+" water level "+level);
                System.out.println("bestest "+penaltybest);
                System.out.println("dr "+decayrate);
            }
            long gdtimerend   = System.nanoTime();
            this.gdtimerend=gdtimerend;
//            System.out.println("bestest cost: "+bbest);
            this.gdsol=Util.cloneList(bestes);
//            traj[0]=penaltybest;
//           if (j%100==0) {
                    tra.add(penaltybest);
//                }
        
        //local search pake great deluge pake local optima baru dan di ils
        ArrayList<Ship> perturb = Util.cloneList(bestes);
        ArrayList<Ship> bestperturb = Util.cloneList(bestes);
        costils = Util.cost(perturb);
        costbest = Util.cost(bestperturb);
        for (int i = 0; i < 5000; i++) {
//            System.out.println("ils "+i);
        //perturb
            do {
                try {
                    ruincreate2(perturb);
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Heuristic.class.getName()).log(Level.SEVERE, null, ex);
                }
            } while (!Util.cekhc(perturb));
//            System.out.println(i+"pre perturb "+Util.cost(perturb));
            
            ArrayList<Ship> sbestgd = Util.cloneList(perturb); //sbestnya gd
            ArrayList<Ship> stempgd = Util.cloneList(perturb); //stempnya gd
            ArrayList<Ship> bestest = Util.cloneList(perturb);
            bbest = Util.cost(bestest);
            cost1 = Util.cost(sbestgd);
            level = Util.cost(sbestgd);
            //great deluge
            for (int j = 0; j < gditer; j++) {
                int numb=rn.nextInt(1); //reinforcement learning ntar masi pake sr
                switch(numb){
                    case(0):
                        do {
                            shift(stempgd);
                        } while (!Util.cekhc(stempgd));
                        break;
                    case(1):
                        do {
                            swap(stempgd);
                        } while (!Util.cekhc(stempgd));
                        break;
//                    case(2):
//                        do {
//                    try {
//                        ruincreate(stempgd);
//                    } catch (CloneNotSupportedException ex) {
//                        Logger.getLogger(Heuristic.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                        } while (!Util.cekhc(stempgd));
//                        break;
                }
                
                //calculate cost
                cost2 = Util.cost(stempgd); //yang baru dishake

                //init level = cost terbaik
                //BEST OF THE BEBST
                
                //kalo improve, update best local solution and level
                if (cost2<cost1) {
                    cost1=cost2;
                    sbestgd = Util.cloneList(stempgd);
                    level = cost2;
                }else if (cost2<=level) {
                    cost1=cost2;
                    sbestgd = Util.cloneList(stempgd);
                }else
                    stempgd = Util.cloneList(sbestgd);
                if (cost2<bbest) {
                    bbest=cost2;
                    bestest = Util.cloneList(stempgd);
                }
                //kalo ga improve, apakah kurang dari sama dengan level, kalo iya update bbest local
                level=level-decayrate;
//                if (j%100==0) {
//                    tra.add(cost1);
//                }
            }
//            System.out.println(i+" cost gd "+bbest);
            costgd = Util.cost(bestest);
            //bandingkan hasil gd dan perturb awal
            
            if (costgd<costils) {
                costils=costgd;
                perturb = Util.cloneList(bestest);
//                System.out.println(i+"change"+costils);
            }
            if (costils<costbest) {
                costbest=costils;
                bestperturb=Util.cloneList(perturb);
                besttimer   = System.nanoTime();
                this.besttimer=besttimer;
//                System.out.println(besttimer);
            }
//            System.out.println(i+" cost ils "+costils);
//            if (i%10==0) {
////                    System.out.println(i+" penalty best "+costils);
//                    traj[i/10]=costils;
//                }
            tra.add(costils);
        
        }
//        System.out.println(""+traj.length);
System.out.println("ils best"+costbest);
        this.ilssol=Util.cloneList(bestperturb);
//        System.out.println("cost hc "+penalty1);
//        System.out.println("cost ilsgd "+costils);
        endtimer   = System.nanoTime();
        this.endtimer = endtimer;
        Mainprog.grouptrj[run]=tra;
//        Util.trajectory2(tra, filename, run, "ilsgd"+decayrate);

//        Mainprog.trj.add(traj);
        //acceptance criteria ils bandinginnya 
        
        
       
        
    }
    public void ilsgdrl(String filename, int run){
        long starttimer = System.nanoTime();
        this.startimer=starttimer;
        long endtimer =0;
        long besttimer=0;
        
        ArrayList<Integer> tra = new ArrayList<>();
        
        int[] reinforce = new int[2];
        reinforce[0]=0;
        reinforce[1]=0;
        
        ArrayList<Ship> sbest = Util.cloneList(initsol);
        ArrayList<Ship> stemp = Util.cloneList(initsol);
        ArrayList<Ship> bestes = Util.cloneList(initsol);
            
        int penaltybest = Util.cost(bestes);
        int penalty1 = Util.cost(sbest);
        int penalty2 = 0;
        
        
        Random rn = new Random();
                
        int maxiteration = 50000;
        
        int cost1 = 0;
        int cost2 = 0;
        
        int costils = 0;
        int costgd = 0;
        int costbest = 0;
        
        int bbest = 0;
        
        
        double level = Util.cost(sbest);
        double decayrate = 5;
        
            long gdtimerstart   = System.nanoTime();
            this.gdtimerstart=gdtimerstart;
            //great deluge
            for (int j = 0; j < 200; j++) {
                int numb=0; //rl
                if(reinforce[0]>reinforce[1]){
                    numb=0;
                }else if(reinforce[1]>reinforce[0]){
                    numb=1;
                }else{
                    numb=rn.nextInt(1);
                }
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
//                    case(2):
//                        do {
//                    try {
//                        ruincreate(stemp);
//                    } catch (CloneNotSupportedException ex) {
//                        Logger.getLogger(Heuristic.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                        } while (!Util.cekhc(stemp));
//                        break;
                }
                
                //calculate cost
                penalty2 = Util.cost(stemp); //yang baru dishake

                //init level = cost terbaik
                //BEST OF THE BEBST
                
                //kalo improve, update best local solution and level
                if (penalty2<penalty1) {
                    penalty1=penalty2;
                    sbest = Util.cloneList(stemp);
                    level = penalty2;
                    reinforce[numb]++;
                }else if (penalty2<=level) {
                    penalty1=penalty2;
                    sbest = Util.cloneList(stemp);
                    reinforce[numb]--;
                }else
                    stemp = Util.cloneList(sbest);
                    reinforce[numb]--;
                if (penalty2<penaltybest) {
                    penaltybest=penalty2;
                    bestes = Util.cloneList(stemp);
                    long gdbest   = System.nanoTime();
                    this.gdbest = gdbest;
                }
                //kalo ga improve, apakah kurang dari sama dengan level, kalo iya update bbest local
                level=level-decayrate;
                
            }
            long gdtimerend   = System.nanoTime();
            this.gdtimerend=gdtimerend;
//            System.out.println("bestest cost: "+bbest);
            this.gdsol=Util.cloneList(bestes);
//            traj[0]=penaltybest;
            tra.add(penaltybest);
           
        reinforce[0]=0;
        reinforce[1]=0;
        //local search pake great deluge pake local optima baru dan di ils
        ArrayList<Ship> perturb = Util.cloneList(bestes);
        ArrayList<Ship> bestperturb = Util.cloneList(bestes);
        costils = Util.cost(perturb);
        costbest = Util.cost(bestperturb);
        
        for (int i = 0; i < 5000; i++) {
//            System.out.println("ils "+i);
        //perturb
            do {
                try {
                    ruincreate2(perturb);
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Heuristic.class.getName()).log(Level.SEVERE, null, ex);
                }
            } while (!Util.cekhc(perturb));
//            System.out.println(i+"pre perturb "+Util.cost(perturb));
            
            ArrayList<Ship> sbestgd = Util.cloneList(perturb); //sbestnya gd
            ArrayList<Ship> stempgd = Util.cloneList(perturb); //stempnya gd
            ArrayList<Ship> bestest = Util.cloneList(perturb);
            bbest = Util.cost(bestest);
            cost1 = Util.cost(sbestgd);
            level = Util.cost(sbestgd);
            //great deluge
            for (int j = 0; j < 200; j++) {
                int numb=0; //rl
                if(reinforce[0]>reinforce[1]){
                    numb=0;
                }else if(reinforce[1]>reinforce[0]){
                    numb=1;
                }else{
                    numb=rn.nextInt(1);
                }
                switch(numb){
                    case(0):
                        do {
                            shift(stempgd);
                        } while (!Util.cekhc(stempgd));
                        break;
                    case(1):
                        do {
                            swap(stempgd);
                        } while (!Util.cekhc(stempgd));
                        break;
//                    case(2):
//                        do {
//                    try {
//                        ruincreate(stempgd);
//                    } catch (CloneNotSupportedException ex) {
//                        Logger.getLogger(Heuristic.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                        } while (!Util.cekhc(stempgd));
//                        break;
                }
                
                //calculate cost
                cost2 = Util.cost(stempgd); //yang baru dishake

                //init level = cost terbaik
                //BEST OF THE BEBST
                
                //kalo improve, update best local solution and level
                if (cost2<cost1) {
                    cost1=cost2;
                    sbestgd = Util.cloneList(stempgd);
                    level = cost2;
                    reinforce[numb]++;
                }else if (cost2<=level) {
                    cost1=cost2;
                    sbestgd = Util.cloneList(stempgd);
                    reinforce[numb]--;
                }else
                    stempgd = Util.cloneList(sbestgd);
                    reinforce[numb]--;
                if (cost2<bbest) {
                    bbest=cost2;
                    bestest = Util.cloneList(stempgd);
                }
                //kalo ga improve, apakah kurang dari sama dengan level, kalo iya update bbest local
                level=level-decayrate;
                
            }
//            System.out.println(i+" cost gd "+bbest);
            costgd = Util.cost(bestest);
            //bandingkan hasil gd dan perturb awal
            
            if (costgd<costils) {
                costils=costgd;
                perturb = Util.cloneList(bestest);
//                System.out.println(i+"change"+costils);
            }
            if (costils<costbest) {
                costbest=costils;
                bestperturb=Util.cloneList(perturb);
                besttimer   = System.nanoTime();
                this.besttimer=besttimer;
//                System.out.println(besttimer);
            }
//            System.out.println(i+" cost ils "+costils);
           tra.add(costils);
            
        
        }
//        System.out.println(""+traj.length);
        this.ilssol=Util.cloneList(bestperturb);
//        System.out.println("cost hc "+penalty1);
//        System.out.println("cost ilsgd "+costils);
        endtimer   = System.nanoTime();
        this.endtimer = endtimer;
//        Mainprog.trj.add(traj);
        //acceptance criteria ils bandinginnya 
//        Util.trajectory2(tra, filename, run, "ilsgdrl");
        
       
        
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
            thisship.setUi(ri-(int)thisship.getDesDepart());
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
        int ruinfactor = (int)(0.2*listship.size());
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
//        
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
                    if (allberth.get(j).getDaftarship().size()>0) {
//                        System.out.println("berth lebih dari 0");
                        for (int l = 0; l < allberth.get(j).getDaftarship().size(); l++) {
//                        System.out.println(j+" yang ada di berth ini = "+ map.get(j).get(l).getShipId());
                            isi.add(allberth.get(j).getDaftarship().get(l));
                        }
                        Ship baru = (Ship)candidate.get(i).clone(); 
                        baru.setBerth(j); //feasible position?? nyobain semua arrival nya ship?
                        isi.add(baru);
                        countagain(isi);
                        if (Util.cekhc(isi)==true) {
                            baru.setHi(baru.getProcessTimes()[j]);
                            feasible.add(baru);
                        }
                    }
                    else if (allberth.get(j).getDaftarship().size()==0) {
//                        System.out.println("berth 0");
                        Ship baru = (Ship)candidate.get(i).clone(); 
                        baru.setBerth(j); //feasible position?? nyobain semua arrival nya ship?
                        isi.add(baru);
                        countagain(isi);
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
            if (feasible.size()==1) {
//                System.out.println("ambil karena 1");
                outputan.add(feasible.get(0));
                allberth.get(feasible.get(0).getBerth()).getDaftarship().add(feasible.get(0));
            }
            else{
//                System.out.println("ambil karena lebih dari 1");
                outputan.add(feasible.get(1));
                allberth.get(feasible.get(1).getBerth()).getDaftarship().add(feasible.get(1));
            }
//            else if(feasible.size()==0){
//                
//            }
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
//        System.out.println("hc? "+Util.cekhc(fix));
//        System.out.println("hc? "+Util.cekhc(initsol));
//        System.out.println(fix.size());
        
//        listship = Util.cloneList(fix);
        listship.clear();
        listship.addAll(fix);
//        System.out.println("perturb alldone");
//        return fix;
    }
    
    
    public void blink(){
        Random rn = new Random();
        double u = rn.nextDouble();
            double beta = 0.01;
            double k = Math.log((u*beta)/(1-beta))/Math.log(beta);
        
        
        System.out.println("k = " + k);
    }
}

    
    

