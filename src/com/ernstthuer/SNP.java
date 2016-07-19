package com.ernstthuer;

import javafx.geometry.Pos;

/**
 * Created by ethur on 7/14/16.
 *
 * collect SNPs and their functionalities, gather observations and
 *
 */
public class SNP implements Comparable<SNP>{
    private String gene;
    private char ORG;
    private char ALT;
    private int position;
    private int ORGcov;
    private int ALTcov;
    private boolean validated ;

    public SNP(String gene, char ORG, char ALT, int position) {
        this.gene = gene;
        this.ORG = ORG;
        this.ALT = ALT;
        this.position = position;
        this.ORGcov = 0;
        this.ALTcov = 0;
        this.validated = false;
    }

    public SNP(char ORG, char ALT, int position) {
        this.gene = null;
        this.ORG = ORG;
        this.ALT = ALT;
        this.position = position;
        this.ORGcov = 0;
        this.ALTcov = 0;
        this.validated = false;
    }

    public int getORGcov() {
        return ORGcov;
    }

    public void setORGcov(int ORGcov) {
        this.ORGcov = ORGcov;
    }

    public int getALTcov() {
        return ALTcov;
    }

    public void setALTcov(int ALTcov) {
        this.ALTcov = ALTcov;
    }

    public boolean isValidated() {
        return validated;
    }

    public int getPosition() {
        return position;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SNP snp = (SNP) o;

        if (ALT != snp.ALT) return false;
        return position == snp.position;

    }

    @Override
    public int hashCode() {
        int result = (int) ALT;
        result = 31 * result + position;
        return result;
    }

    @Override
    public String toString(){
        return this.ALT + " " + this.position;
    }

    @Override
    public int compareTo(SNP o) {
        if (this.ALT == o.ALT && this.position == o.position){
            return 1;
        }else {
            return 0;
        }
    }
}
