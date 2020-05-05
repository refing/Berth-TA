package taberth;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author refing
 */

import java.io.IOException;
import java.util.ArrayList;

public class Mainprog {
    
    public static void main(String[] args) throws IOException {
        
        ArrayList<String[]> arrship = new ArrayList<>();
        ArrayList<Ship> listship = new ArrayList<>();
        ArrayList<String[]> arrberth = new ArrayList<>();
        ArrayList<Berth> listberth = new ArrayList<>();
        ArrayList<BerthTrans> listbertha = new ArrayList<>();
        
//        String file = "src/instancetest/problem_10_vessels_1.txt";
        String file = "src/instance/problem_100_vessels_0.txt";
        
        ReadFile read = new ReadFile(file, arrship, listship, arrberth, listberth, listbertha);
        InitSolution init = new InitSolution(listship, listberth, listbertha);
        
        System.out.println("");
        System.out.println("cost initial solution = "+Util.cost(init.initialsol));
        System.out.println("");
        
        System.out.println("cost initial "+Util.cost(init.listship));
        System.out.println("M initial "+init.M);
        
        Heuristic heur = new Heuristic(init.initialsol);
        System.out.println("cost initial "+Util.cost(heur.initsol));
        System.out.println("heur");
//        heur.hill();

//        heur.tesswap();
        
        
        heur.ilsgd();
//        System.out.println("cke hc ils = " + Util.cekhc(heur.ilssol));
//        
//        heur.gd();
//        System.out.println("cke hc gd= " + Util.cekhc(heur.gdsol));

        //heur.hill();
//        heur.countagain(heur.hilsol);

        //System.out.println("hc hil="+Util.cekhc(heur.hilsol));
        
        //System.out.println("cost hil="+Util.cost(heur.hilsol));
        //System.out.println("cost initial "+Util.cost(heur.initsol));
        
        
        
        
        
          
            
        
        
//Ship            Berth       Start           End                 Cost
//0		 9		0		5		5
//1		 3		3		10		7
//2		 4		9		21		12
//3		 10		9		14		5
//4		 7		11		39		28
//5		 1		11		39		28
//6		 3		14		21		7
//7		 9		17		21		4
//8		 4		32		37		5
//9		 0		35		44		9
//Cost: 110
            
          
        
        
        
    }
    
}
