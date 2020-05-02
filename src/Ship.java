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
public class Ship {
    
    private int shipId;
    private String type;
    private float draft;
    private float length;
    private float width;
    private float DWT;
    private float arrival;
    private float desDepart;
    private int manouvTime;
    private int costWait;
    private int costDelay;
    //private ArrayList <Integer> processTimes = new ArrayList<>();
    private int [] processTimes = new int[11];
    
    private int ti;
    private int ri;
    private int hi;
    
    private int berth;
    
    private int rti;
    private int rri;


    public Ship(int shipId) {
        this.shipId = shipId;
    }

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

    public float getDraft() {
        return draft;
    }

    public void setDraft(float draft) {
        this.draft = draft;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getDWT() {
        return DWT;
    }

    public void setDWT(float DWT) {
        this.DWT = DWT;
    }

    public float getArrival() {
        return arrival;
    }

    public void setArrival(float Arrival) {
        this.arrival = Arrival;
    }

    public float getDesDepart() {
        return desDepart;
    }

    public void setDesDepart(float desDepart) {
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

    public int getBerth() {
        return berth;
    }

    public void setBerth(int berth) {
        this.berth = berth;
    }

    public int getRti() {
        return rti;
    }

    public void setRti(int rti) {
        this.rti = rti;
    }

    public int getRri() {
        return rri;
    }

    public void setRri(int rri) {
        this.rri = rri;
    }
            
    
    
    
}
