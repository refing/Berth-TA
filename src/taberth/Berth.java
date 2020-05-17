package taberth;

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
public class Berth {
    private int idBerth;
    private int idAsli;
    private String type;
    private double maxDraft;
    private double maxLength; 
    private double maxWidth;
    private double maxDWT;
    private ArrayList<Ship> daftarship = new ArrayList<Ship>();

    public Berth(int idBerth) {
        this.idBerth = idBerth;
    }

    public int getIdBerth() {
        return idBerth;
    }

    public void setIdBerth(int idBerth) {
        this.idBerth = idBerth;
    }

    public ArrayList<Ship> getDaftarship() {
        return daftarship;
    }

    public void setDaftarship(ArrayList<Ship> daftarship) {
        this.daftarship = daftarship;
    }
    
    public int getIdAsli() {
        return idAsli;
    }

    public void setIdAsli(int idAsli) {
        this.idAsli = idAsli;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMaxDraft() {
        return maxDraft;
    }

    public void setMaxDraft(double maxDraft) {
        this.maxDraft = maxDraft;
    }

    public double getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(double maxLength) {
        this.maxLength = maxLength;
    }

    public double getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(double maxWidth) {
        this.maxWidth = maxWidth;
    }

    public double getMaxDWT() {
        return maxDWT;
    }

    public void setMaxDWT(double maxDWT) {
        this.maxDWT = maxDWT;
    }

    
    
}
