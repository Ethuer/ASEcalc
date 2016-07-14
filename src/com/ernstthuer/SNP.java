package com.ernstthuer;

import javafx.geometry.Pos;

/**
 * Created by ethur on 7/14/16.
 */
public class SNP {
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
}
