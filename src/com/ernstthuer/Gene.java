package com.ernstthuer;

import java.util.List;

/**
 * Created by ethur on 7/18/16.
 */
public class Gene {

    /**
     * The term Gene is used loosely,  this could be also refering to exon or transctips.
     */
    private String chromosome;
    private int start;
    private int stop;
    private String ident;
    private List<SNP> geneSNPList;
    private String ASE;

    public Gene(String chromosome, int start, int stop, String description) {
        this.chromosome = chromosome;
        this.start = start;
        this.stop = stop;
        this.ident = description;
    }
}
