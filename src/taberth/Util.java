package taberth;


import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author refing
 */
public class Util {
    public static boolean cekhc(ArrayList<Ship> listship){
        boolean cek2 = false;
        boolean cek3 = true;
        boolean cek4 = false;
        boolean cek5 = false;
        boolean cek6 = false;
        boolean cek7 = false;
        boolean cek8 = false;
        boolean cek9 = false;
        boolean cek10 = false;
        boolean valid = false;
        for (int i = 0; i < listship.size(); i++) {
            //constrain ti >= ai
            if(listship.get(i).getTi()>=(int)listship.get(i).getArrival()){
                cek2=true;
            }else{
                cek2=false;
//                System.out.println(i+" Xconstrain 2");
            }
            //constrin mik=1
            //cek3
            //consntrain ti >= mikrelk //?
            for(int j = i+1; j < listship.size(); j++) {
                if (listship.get(i).getBerth()==listship.get(j).getBerth()) {
//                    System.out.println("ship "+listship.get(i).getShipId()+" di berth "+listship.get(i).getBerth()+" dan ship "+listship.get(j).getShipId()+" diberth "+listship.get(j).getBerth());
                    if(listship.get(j).getTi()>=listship.get(i).getRi()){
                        cek4 =true;
                    }else{
                        cek4 =false;
                        System.out.println(i+" Xconstrain 4");
                    }
                }
            }
            //constrain ri>= ti+mikhi
            if(listship.get(i).getRi()>=listship.get(i).getTi()+listship.get(i).getHi()){
                cek5=true;
            }else{
                cek5=false;
                System.out.println(i+" Xconstrain 5");
            }
            //constrain ui>=ri-si
            if(listship.get(i).getUi()>=listship.get(i).getRi()-listship.get(i).getDesDepart()){
                cek6=true;
            }else{
                cek6=false;
                System.out.println(i+" Xconstrain 6");
            }
            //constrain kapal 2 bisa berth kalo kapal 1 udah depart (7)
            int M = InitSolution.M;
            int sigma = 0;
            boolean depart = false;

            for(int j = i+1; j < listship.size(); j++) {
                if(listship.get(j).getTi()>=listship.get(i).getRi()){
                    sigma =1;
                }
                depart = listship.get(j).getTi()>=listship.get(i).getRi()-M*(1-sigma);
                if (depart==false) {
                    cek7=false;
                    break;
                }
                if (depart==true){
                    cek7=true;
                }
            }
            //constrain mvb-n-1
            int sigmasum=0;
            int mib=1;
            boolean opp = false;
            boolean adj = false;
            //adjacent opposite
            
            for (int j = i+1; j < listship.size(); j++) {

                    if (ReadFile.adjacentmatrix[listship.get(i).getBerth()][listship.get(j).getBerth()]>0) {
                        adj=(listship.get(i).getLength()/2)+(listship.get(j).getLength()/2)+ReadFile.safetyadjacent > ReadFile.adjacentmatrix[listship.get(i).getBerth()][listship.get(j).getBerth()];
                    }
                    if (adj) {
                        cek8 = false;
                        break;
//                        if(listship.get(j).getTi()>=listship.get(i).getRi()){
//                            sigmasum++;
//                        }
                    }else{
                        cek8=true;
                    }

            }
            for (int j = i+1; j < listship.size(); j++) {

                    
                    if (ReadFile.oppositematrix[listship.get(i).getBerth()][listship.get(j).getBerth()]>0) {
                        opp=(listship.get(i).getWidth())+(listship.get(j).getWidth())+ReadFile.safetyopposite > ReadFile.oppositematrix[listship.get(i).getBerth()][listship.get(j).getBerth()];
                    }
                    if (opp) {
                        cek9 = false;
                        break;
//                        if(listship.get(j).getTi()>=listship.get(i).getRi()){
//                            sigmasum++;
//                        }
                    }else{
                        cek9=true;
                    }

            }
            
    //        System.out.println("sigmasum "+sigmasum);

//            if(sigmasum>=mib){
//                cek8 = false;
//            }else{
//                cek8=true;
//            }
//            if (oppadj(listship)==true) {
//                cek8=false;
//                System.out.println(i+" Xconstrain 8");
//            }else{
//                cek8=true;
//            }
            //constrain ti!=0,ri!=0,ui!=0
            if (listship.get(i).getTi()>=0&&listship.get(i).getRi()>=0&&listship.get(i).getUi()>=0) {
                cek10=true;
            }else{
                cek10=false;
                System.out.println(i+" Xconstrain 9");
//                System.out.println("ship "+listship.get(i).getShipId());
//                System.out.println("berth "+listship.get(i).getBerth());
//                System.out.println("Ti "+listship.get(i).getTi());
//                System.out.println("Ri "+listship.get(i).getRi());
//                System.out.println("Ui "+listship.get(i).getUi());
            }
        }
        
        
        if(cek2&&cek3&&cek4&&cek5&&cek6&&cek7&&cek8&&cek9&&cek10){
            valid=true;
        }else
            valid=false;
        return valid;
    }
    public static boolean cekhc2(ArrayList<Ship> listship){
        boolean cek0 = false;
        boolean cek1 = false;
        boolean cek2 = false;
        boolean cek3 = false;
        boolean cek4 = false;
        boolean valid = false;
        for (int i = 0; i < listship.size(); i++) {
            //constrain hi!=0
            if (listship.get(i).getHi()!=0) {
                cek0=true;
            }else{
                cek0=false;
                System.out.println(i+" Xconstrain 0");
                System.out.println("berth "+listship.get(i).getBerth());
                System.out.println("hi "+listship.get(i).getHi());
            }
            //constrain ti >= ai
            if(listship.get(i).getTi()>=(int)listship.get(i).getArrival()){
                cek1=true;
            }else{
                cek1=false;
                System.out.println(i+" Xconstrain 1");
            }
            //constrain ri>= ti+hi
            if(listship.get(i).getRi()>=listship.get(i).getTi()+listship.get(i).getHi()){
                cek2=true;
            }else{
                cek2=false;
                System.out.println(i+" Xconstrain 2");
            }
            //constrain kapal 2 bisa berth kalo kapal 1 udah depart (7)
            int M = InitSolution.M;
            int sigma = 0;
            boolean depart = false;

            for(int j = i+1; j < listship.size(); j++) {
                if(listship.get(j).getTi()>=listship.get(i).getRi()){
                    sigma =1;
                }
                depart = listship.get(j).getTi()>=listship.get(i).getRi()-M*(1-sigma);
                if (depart==false) {
                    cek3=false;
                    break;
                }
                if (depart==true){
                    cek3=true;
                }
            }
            
            
//            if(conflictmatrix(listship)==true){
//                cek3=true;
//            }else{
//                cek3=false;
//                System.out.println(i+" Xconstrain 3");
//            }
            //consntrain ti >= relk //?
            
                for(int j = i+1; j < listship.size(); j++) {
                    if (listship.get(i).getBerth()==listship.get(j).getBerth()) {
    //                    System.out.println("ship "+listship.get(i).getShipId()+" di berth "+listship.get(i).getBerth()+" dan ship "+listship.get(j).getShipId()+" diberth "+listship.get(j).getBerth());
                        if(listship.get(j).getTi()>=listship.get(i).getRi()){
                            cek4 =true;
                        }else{
                            cek4 =false;
                            System.out.println(i+" Xconstrain 4");
                        }
                    }
                }
            
//            //constraint 10
//            if (oppadj(listship)==true) {
//                cek5=true;
//            }else{
//                cek5=false;
//                System.out.println(i+" Xconstrain 5");
//            }
            
        }
        
        
        if(cek0&&cek1&&cek2&&cek3&&cek4){
            valid=true;
        }else
            valid=false;
        return valid;
    }
    public static boolean oppadj (ArrayList<Ship> listship){
        boolean bool = false;
        ArrayList <Ship> adjacent = new ArrayList<>();
        int sigmasum=0;
        int mib=1;
        boolean opp = false;
        boolean adj = false;
        //adjacent opposite
        for (int i = 0; i < listship.size(); i++) {
            for (int j = i+1; j < listship.size(); j++) {
                
                    if (ReadFile.adjacentmatrix[listship.get(i).getBerth()][listship.get(j).getBerth()]>0) {
                        adj=(listship.get(i).getLength()/2)+(listship.get(j).getLength()/2)+ReadFile.safetyadjacent > ReadFile.adjacentmatrix[listship.get(i).getBerth()][listship.get(j).getBerth()];
                    }
                    if (ReadFile.oppositematrix[listship.get(i).getBerth()][listship.get(j).getBerth()]>0) {
                        opp=(listship.get(i).getWidth())+(listship.get(j).getWidth())+ReadFile.safetyopposite > ReadFile.oppositematrix[listship.get(i).getBerth()][listship.get(j).getBerth()];
                    }
                    if (adj||opp) {
                        if(listship.get(j).getTi()>=listship.get(i).getRi()){
                            sigmasum++;
                        }
                    }
                
            }
        }
//        System.out.println("sigmasum "+sigmasum);
        
        if(sigmasum>=mib){
            bool = true;
        }
        return bool;
        
//        for (int i = 0; i < listship.size(); i++) {
//            mib++;
//        }
//        
//        System.out.println(""+mib);
        
    }
    
    public static void confop(ArrayList<Ship> listship){
        ArrayList <Ship> adjacent = new ArrayList<>();
        HashSet <int[]> adjs = new HashSet<>(); 
        int[] arrr = new int[4];
        for (int i = 0; i < listship.size(); i++) {
            for (int j = i+1; j < listship.size(); j++) {
                if (ReadFile.adjacentmatrix[listship.get(i).getBerth()][listship.get(j).getBerth()]>0) {
                    boolean adj=(listship.get(i).getLength()/2)+(listship.get(j).getLength()/2)+ReadFile.safetyadjacent > ReadFile.adjacentmatrix[listship.get(i).getBerth()][listship.get(j).getBerth()];
                    if (adj) {
                        arrr[1]=listship.get(i).getShipId();
                        arrr[2]=listship.get(i).getBerth();
                        arrr[3]=listship.get(j).getShipId();
                        arrr[4]=listship.get(j).getBerth();
                        adjs.add(arrr);
                    }
                }
            }
        }
        
        System.out.println(adjs);
//        for (int i = 0; i < listship.size(); i++) {
//            for (int j = i+1; j < listship.size(); j++) {
//                if (ReadFile.oppositematrix[listship.get(i).getBerth()][listship.get(j).getBerth()]>0) {
//                    boolean opp=(listship.get(i).getWidth())+(listship.get(j).getWidth())+ReadFile.safetyopposite > ReadFile.oppositematrix[listship.get(i).getBerth()][listship.get(j).getBerth()];
//                }
//            }
//        }
                
    }
    
    public static boolean conflictmatrix(ArrayList<Ship> listship){
        int M = InitSolution.M;
        int sigma = 0;
        boolean conf = false;
        boolean depart = false;
        
        for(int i = 0; i < listship.size(); i++) {
            for(int j = i+1; j < listship.size(); j++) {
                if(listship.get(j).getTi()>=listship.get(i).getRi()){
                    sigma =1;
                }
                depart = listship.get(j).getTi()>=listship.get(i).getRi()-M*(1-sigma);
            }
        }
        
        if (depart){
            conf = true;
        }
        return conf;
    }
    
    
    public static int cost(ArrayList<Ship> listship){
        int cost=0;
        for (int i = 0; i < listship.size(); i++) {
            cost = cost + listship.get(i).getUi();
        }
        return cost;
    }
    
    public static ArrayList<Ship> cloneList(ArrayList<Ship> sl) {
        ArrayList<Ship> clonedList = new ArrayList<Ship>(sl.size());
        for (Ship ship : sl) {
            clonedList.add(new Ship(ship));
        }
        return clonedList;
    }
    
    public static void printinit(ArrayList<Ship> listship){
        int[][] berthsch = new int[listship.size()][5];
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
        
        System.out.println("total cost = " + cost(listship));  
    }
    
    
    
    public static void trajectory2(ArrayList<Double> trj, String filename, String alg){
        try{  
            FileWriter fw=new FileWriter("D:\\hasil\\trajectory\\"+filename+"\\"+alg+"\\"+filename+"_traj_.txt"); 
            
                for (int j = 0; j < trj.size(); j++) {
                    fw.write(""+trj.get(j));
                    fw.write("\n");
                }
//                fw.write(";");
            
            fw.write("\n");
//            fw.write(""+Util.cost(initial)+" hc "+Util.cost(hc)+" ils "+Util.cost(ils)); 
            fw.close();    
        } catch(Exception e){
        	System.out.println(e);
        }    
            System.out.println("File berhasil disimpan di D");  
    }
    public static void exportstatils(ArrayList<Ship> initial, ArrayList<Ship> ils, String filename, int run, long start, long end, long best){
        try{    
            FileWriter fw=new FileWriter("D:\\hasil\\new\\stat.txt", true); 
            
            fw.write(filename+"-"+run+";"+Util.cost(initial)+";"+Util.cost(ils)+";"+start+";"+end+";"+best);
            fw.write("\n");
//            fw.write(""+Util.cost(initial)+" hc "+Util.cost(hc)+" ils "+Util.cost(ils)); 
            fw.close();    
        } catch(Exception e){
        	System.out.println(e);
        }    
            System.out.println("File berhasil disimpan di D");    
    }
    
    public static void export2(ArrayList<Ship> listfinal, String filename, int run){
        try{    
            FileWriter fw=new FileWriter("D:\\hasil\\"+filename+"_all_"+run+".txt"); 
            fw.write("Ship"+"\t"+"Berth"+"\t"+"Start"+"\t"+"End"+"\t"+"Cost"+"\t"+"Ti"); 
            fw.write("\n"); 
            for (int i = 0; i <listfinal.size(); i++) {
                fw.write(listfinal.get(i).getShipId()+"\t\t"+listfinal.get(i).getBerth()+"\t\t"+(int)listfinal.get(i).getArrival()+"\t\t"+listfinal.get(i).getRi()+"\t\t"+listfinal.get(i).getHi()+"\t\t"+listfinal.get(i).getTi());
                fw.write("\n"); 
            }
            fw.write("cost : "+Util.cost(listfinal)); 
             
            fw.close();    
        } catch(Exception e){
        	System.out.println(e);
        }    
            System.out.println("File "+filename+".txt berhasil disimpan di D");    
    }
    
    public static void exportstat(ArrayList<Ship> initial, ArrayList<Ship> sol, String filename, int run, long start, long end, long best, String alg){
        try{    
            FileWriter fw=new FileWriter("D:\\hasil\\"+filename+"\\"+alg+"\\stat.txt", true); 
            
            fw.write(filename+"-"+run+";"+Util.cost(initial)+";"+Util.cost(sol)+";"+start+";"+end+";"+best);
            fw.write("\n");
//            fw.write(""+Util.cost(initial)+" hc "+Util.cost(hc)+" ils "+Util.cost(ils)); 
            fw.close();    
        } catch(Exception e){
        	System.out.println(e);
        }    
            System.out.println("File berhasil disimpan di D");    
    }
    public static void export(ArrayList<Ship> listfinal, String filename, int run, String alg){
        try{    
            FileWriter fw=new FileWriter("D:\\hasil\\"+filename+"\\"+alg+"\\"+filename+"_"+alg+"_"+run+".txt"); 
            fw.write("Ship"+"\t"+"Berth"+"\t"+"Start"+"\t"+"End"+"\t"+"Cost"+"\t"+"Ti"); 
            fw.write("\n"); 
            for (int i = 0; i <listfinal.size(); i++) {
                fw.write(listfinal.get(i).getShipId()+"\t\t"+listfinal.get(i).getBerth()+"\t\t"+(int)listfinal.get(i).getArrival()+"\t\t"+listfinal.get(i).getRi()+"\t\t"+listfinal.get(i).getHi()+"\t\t"+listfinal.get(i).getTi());
                fw.write("\n"); 
            }
            fw.write("cost : "+Util.cost(listfinal)); 
             
            fw.close();    
        } catch(Exception e){
        	System.out.println(e);
        }    
            System.out.println("File "+filename+".txt berhasil disimpan di D");    
    }
    
    
    
}
