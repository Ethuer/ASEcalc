package com.ernstthuer;

import javafx.geometry.Pos;

/**
 * Created by ethur on 7/14/16.
 */
public class SNP {
    private String ORG;
    private String ALT;
    private int position;
    private int ORGcov;
    private int ALTcov;

    private int validated ;

    public SNP(String ORG, String ALT, int position, int ORGcov, int ALTcov, int validated) {
        this.ORG = ORG;
        this.ALT = ALT;
        this.position = position;
        this.ORGcov = ORGcov;
        this.ALTcov = ALTcov;
        this.validated = validated;
    }
}
