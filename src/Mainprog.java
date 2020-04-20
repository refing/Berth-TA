/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author refing
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.*;
import java.util.*;

public class Mainprog {
    
    public static void main(String[] args) throws IOException {
        
        ArrayList<String[]> arrship = new ArrayList<String[]>();
        ArrayList<Ship> listship = new ArrayList<Ship>();
        ArrayList<String[]> arrberth = new ArrayList<String[]>();
        ArrayList<Berth> listberth = new ArrayList<Berth>();
        ArrayList<BerthTrans> listbertha = new ArrayList<BerthTrans>();
        
        String file = "src/instancetest/problem_10_vessels_1.txt";
        
        ReadFile read = new ReadFile(file, arrship, listship, arrberth, listberth, listbertha);
        InitSolution init = new InitSolution(arrship, listship, arrberth, listberth, listbertha);
        
        init.cekhc(listship);
        
        init.generatenew(listship);
        
        
          
            
        
        
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
