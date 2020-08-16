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

public class Mainprog {
    public static ArrayList<Double>[] grouptrj = (ArrayList<Double>[])new ArrayList[10];
    public static void main(String[] args) throws IOException, CloneNotSupportedException  {
        
        File[] files = new File("src/instance/").listFiles();
        
        ArrayList<Double> avgtrj = new ArrayList<>();
        
//        String[] algg = {"ilsgd005","ilsgd01","ilsgd02","ilsgd03","ilsgd04","ilsgd05","ilsgd06","ilsgd07","ilsgd08","ilsgd09","ilsgd095"};
        double[] desired = {0.05,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,0.95};
//        for (int k = 0; k < algg.length; k++) {
        for (int i = 0; i < files.length; i++) {
            String filename = files[i].getName().substring(0, files[i].getName().length() - 4);
            String filepath = files[i].getPath();
//            System.out.println(""+filename);
            String alg = "00";
            double des = 0.1;
//            System.out.println(""+alg);
            for (int j = 0; j < 5; j++) {
            ReadFile read = new ReadFile(filepath);
            InitSolution init = new InitSolution(read.listship);
            Util.export(init.initialsol, filename,j,alg);
//            System.out.println("");
//            System.out.println("cost initial solution = "+Util.cost(init.initialsol));
//            System.out.println("");
            
//            Heuristic heur = new Heuristic(init.initialsol);
//            heur.ilsgd(filename, j,des);
//            Util.export(heur.ilssol, filename,j,alg);
//            Util.exportstat(heur.initsol, heur.ilssol, filename,j,heur.startimer,heur.endtimer,heur.besttimer,alg);
            
            
//            heur.ilshc(filename, j);
//            Util.export(heur.ilssol, filename,j,alg);
//            Util.exportstat(heur.initsol, heur.ilssol, filename,j,heur.startimer,heur.endtimer,heur.besttimer,alg);
            
//            heur.gd(filename,j);
//            Util.export(heur.gdsol, filename,j,alg);
//            Util.exportstat(heur.initsol, heur.gdsol, filename,j,heur.startimer,heur.endtimer,heur.besttimer,alg);

//            heur.hill(filename,j);
//            Util.export(heur.hilsol, filename,j,alg);
//            Util.exportstat(heur.initsol, heur.hilsol, filename,j,heur.startimer,heur.endtimer,heur.besttimer,alg);

//            heur.ilsgdrl(filename, j);
//            String alg = "ilsgdrl";
//            Util.export(heur.ilssol, filename,j,alg);
//            Util.exportstat(heur.initsol, heur.ilssol, filename,j,heur.startimer,heur.endtimer,heur.besttimer,alg);
            
//            heur.ilshcrl(filename, j);
//            String alg = "ilshcrl";
//            Util.export(heur.ilssol, filename,j,alg);
//            Util.exportstat(heur.initsol, heur.ilssol, filename,j,heur.startimer,heur.endtimer,heur.besttimer,alg);
            
//            System.out.println(i+". "+filename + " run ke "+j);
       
            }
            
//            for (int j = 0; j < grouptrj[0].size(); j++) {
//                System.out.println("iter"+j);
//                for (int k = 0; k<grouptrj[j].size(); k++) {
//                    System.out.println(""+grouptrj[j].get(k));
//                }
//                avgtrj.add((grouptrj[0].get(j)+grouptrj[1].get(j)+grouptrj[2].get(j)+grouptrj[3].get(j)+grouptrj[4].get(j)+grouptrj[5].get(j)+grouptrj[6].get(j)+grouptrj[7].get(j)+grouptrj[8].get(j)+grouptrj[9].get(j))/10);
                
//            }
//            for (int j = 0; j < avgtrj.size(); j++) {
//                System.out.println(""+avgtrj.get(j));
//            }
//            Util.trajectory2(avgtrj, filename, alg);
//            avgtrj.clear();
            
        }
//        }



        
        
        
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
