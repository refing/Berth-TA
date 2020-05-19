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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import sun.text.normalizer.UBiDiProps;

public class Mainprog {
    
    public static void main(String[] args) throws IOException, CloneNotSupportedException  {
        
        
//        for (int i = 0; i < files.length; i++) {
//            String filename = files[i].getName().substring(0, files[i].getName().length() - 4);
//            String filepath = files[i].getPath();
            
//            System.out.println(filename);
//            System.out.println(filepath);
//        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                ArrayList<String[]> arrship = new ArrayList<>();
            ArrayList<Ship> listship = new ArrayList<>();

            File[] files = new File("src/instance/").listFiles();

            String filename = files[i].getName().substring(0, files[i].getName().length() - 4);
            String filepath = files[i].getPath();
            
            ReadFile read = new ReadFile(filepath, arrship, listship);
            InitSolution init = new InitSolution(listship);

            System.out.println("");
            System.out.println("cost initial solution = "+Util.cost(init.initialsol));
            System.out.println("");
            Heuristic heur = new Heuristic(init.initialsol);
            System.out.println("cost initial "+Util.cost(heur.initsol));
            System.out.println("heur");
            
            heur.ilsgd();
            Util.export(heur.ilssol, filename,j);
            Util.exportstat(heur.initsol, heur.hilsol, heur.ilssol, filename,j);
            System.out.println(filename + " run ke "+j);
            }
        }
        
//        String filename = files[0].getName().substring(0, files[0].getName().length() - 4);
//        String filepath = files[0].getPath();
        
        
        
//        ReadFile read = new ReadFile(filepath, arrship, listship);
//        InitSolution init = new InitSolution(listship);
//        
//        System.out.println("");
//        System.out.println("cost initial solution = "+Util.cost(init.initialsol));
//        System.out.println("");
//        
//        System.out.println("cost initial "+Util.cost(init.listship));
////        System.out.println("M initial "+init.M);
//        
//        Heuristic heur = new Heuristic(init.initialsol);
//        System.out.println("cost initial "+Util.cost(heur.initsol));
//        System.out.println("heur");
//        
//        Util.export(init.listship, filename);
//        heur.hill();

//        heur.tesswap();
//        heur.runruin(init.initialsol);
//          heur.ruincreate2(init.initialsol);
//        heur.sementaragd();
//            heur.ilsgd();
//            Util.exportstat(init.initialsol, heur.hilsol, heur.ilssol, filename);
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
    public static void showFiles(File[] files) {
    for (File file : files) {
        if (file.isDirectory()) {
            System.out.println("Directory: " + file.getName());
            showFiles(file.listFiles()); // Calls same method again.
        } else {
            System.out.println("File: " + file.getName());
        }
    }
}
    
}
