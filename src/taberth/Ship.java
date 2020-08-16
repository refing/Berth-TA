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
import java.util.ArrayList;
public class Ship implements Cloneable{
    
    private int shipId;
    private String type;
    private double draft;
    private double length;
    private double width;
    private double DWT;
    private double arrival;
    private double desDepart;
    private int manouvTime;
    private int costWait;
    private int costDelay;
    private int [] processTimes = new int[11];
    private int ti;
    private int ri;
    private int hi;
    private int ui;
    private int berth;
    
    private Ship ship;
    
    public Ship(int shipId) {
        this.shipId = shipId;
    }
    
    public Ship(Ship ship){
        this.shipId=ship.shipId;
        this.type=ship.type;
        this.draft=ship.draft;
        this.length=ship.length;
        this.width=ship.width;
        this.DWT=ship.DWT;
        this.arrival=ship.arrival;
        this.desDepart=ship.desDepart;
        this.manouvTime=ship.manouvTime;
        this.costWait=ship.costWait;
        this.costDelay=ship.costDelay;
        this.processTimes = ship.processTimes;
        this.ti=ship.ti;
        this.ri=ship.ri;
        this.hi=ship.hi;
        this.ui=ship.ui;
        this.berth=ship.berth;
        
    }
    
    public Object clone()throws CloneNotSupportedException{  
        return super.clone();  
    }  
    
    
//    public Ship getShip(){
//        return this.ship;
//    }

    public int getShipId() {
        return shipId;
    }

    public void setShipId(int shipId) {
        this.shipId = shipId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getDraft() {
        return draft;
    }

    public void setDraft(double draft) {
        this.draft = draft;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getDWT() {
        return DWT;
    }

    public void setDWT(double DWT) {
        this.DWT = DWT;
    }

    public double getArrival() {
        return arrival;
    }

    public void setArrival(double Arrival) {
        this.arrival = Arrival;
    }

    public double getDesDepart() {
        return desDepart;
    }

    public void setDesDepart(double desDepart) {
        this.desDepart = desDepart;
    }

    public int getManouvTime() {
        return manouvTime;
    }

    public void setManouvTime(int manouvTime) {
        this.manouvTime = manouvTime;
    }

    public int getCostWait() {
        return costWait;
    }

    public void setCostWait(int costWait) {
        this.costWait = costWait;
    }

    public int getCostDelay() {
        return costDelay;
    }

    public void setCostDelay(int costDelay) {
        this.costDelay = costDelay;
    }

//    public ArrayList<Integer> getProcessTimes() {
//        return processTimes;
//    }
//
//    public void setProcessTimes(ArrayList<Integer> processTimes) {
//        this.processTimes = processTimes;
//    }

    
    public int[] getProcessTimes() {
        return processTimes;
    }

    public void setProcessTimes(int[] processTimes) {
        this.processTimes = processTimes;
    }

    public int getTi() {
        return ti;
    }

    public void setTi(int ti) {
        this.ti = ti;
    }

    public int getRi() {
        return ri;
    }

    public void setRi(int ri) {
        this.ri = ri;
    }

    public int getHi() {
        return hi;
    }

    public void setHi(int hi) {
        this.hi = hi;
    }

    public int getUi() {
        return ui;
    }

    public void setUi(int ui) {
        this.ui = ui;
    }
    
    

    public int getBerth() {
        return berth;
    }

    public void setBerth(int berth) {
        this.berth = berth;
    }

//    public int getSafeadj() {
//        return safeadj;
//    }
//
//    public void setSafeadj(int safeadj) {
//        this.safeadj = safeadj;
//    }
//
//    public int getSafeopp() {
//        return safeopp;
//    }
//
//    public void setSafeopp(int safeopp) {
//        this.safeopp = safeopp;
//    }

    
            
    
    
    
}