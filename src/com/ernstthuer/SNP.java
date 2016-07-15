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

    public SNP(char ORG, char ALT, int position) {

        this.ORG = ORG;
        this.ALT = ALT;
        this.position = position;
        this.ORGcov = 0;
        this.ALTcov = 0;
        this.validated = false;
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
