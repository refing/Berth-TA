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
    private float maxDraft;
    private float maxLength; 
    private float maxWidth;
    private float maxDWT;

    public Berth(int idBerth) {
        this.idBerth = idBerth;
    }

    public int getIdBerth() {
        return idBerth;
    }

    public void setIdBerth(int idBerth) {
        this.idBerth = idBerth;
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

    public float getMaxDraft() {
        return maxDraft;
    }

    public void setMaxDraft(float maxDraft) {
        this.maxDraft = maxDraft;
    }

    public float getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(float maxLength) {
        this.maxLength = maxLength;
    }

    public float getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(float maxWidth) {
        this.maxWidth = maxWidth;
    }

    public float getMaxDWT() {
        return maxDWT;
    }

    public void setMaxDWT(float maxDWT) {
        this.maxDWT = maxDWT;
    }

    
    
}
