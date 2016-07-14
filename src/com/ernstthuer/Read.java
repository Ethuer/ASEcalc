package com.ernstthuer;

import java.util.List;

/**
 * Created by ethur on 7/14/16.
 */
public class Read {
    
    private String seq;
    private int start;
    private int length;
    private List<SNP> snips ;

    public Read(String seq, int start, int length) {
        this.seq = seq;
        this.start = start;
        this.length = length;
    }
}
