
import java.util.ArrayList;

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
        boolean cek1 = false;
        boolean cek2 = false;
        boolean cek3 = false;
        boolean valid = false;
        for (int i = 0; i < listship.size(); i++) {
            //constrain ti >= ai
            if(listship.get(i).getTi()>=(int)listship.get(i).getArrival()){
                cek1=true;
            }
            //constrain ri>= ti+hi
            if(listship.get(i).getRi()>=listship.get(i).getTi()+listship.get(i).getHi()){
                cek2=true;
            }
            //consntrain ti >= relk //???????broken
//            if(listship.get(i).getTi()>=listbertha.get(listship.get(i).getBerth()).getReleasetime()){
//                System.out.println("lolosss"+i);
//            }
            if (cek1==false||cek2==false) {
                break;
            }
            
        }
        //constrain kapal 2 bisa berth kalo kapal 1 udah depart (7)
        if(conflictmatrix(listship)==true){
            cek3=true;
        }else{
            cek3=false;
        }
        
        
        //Ship            Berth       Start           End                 Cost
        //ti>ai
        //ri>ti+hi
        //ti>relk
        if(cek1&&cek2&&cek3){
            valid=true;
        }
        return valid;
    }
    
    
    public static boolean conflictmatrix(ArrayList<Ship> listship){
        int M = InitSolution.M;
        int sigma = 0;
        boolean conf = false;
        boolean depart = false;
        
        for(int i = 0; i < listship.size(); i++) {
            for(int j = i+1; j < listship.size(); j++) {
                if(listship.get(i).getRi()<=listship.get(j).getTi()){
                    sigma =1;
                }
            }
        }
        
        for (int i = 0; i < listship.size(); i++) {
            for (int j = i+1; j < listship.size(); j++) {
                depart = listship.get(j).getTi()>=listship.get(i).getRi()-M+M*sigma;
            }
        }
        if (depart){
            conf= true;
        }
        return conf;
    }
    
    public static int cost(ArrayList<Ship> listship){
        int cost=0;
        for (int i = 0; i < listship.size(); i++) {
            cost = cost + listship.get(i).getHi();
        }
        return cost;
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
}
