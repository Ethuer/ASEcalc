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

    public boolean findSNPs(String ref) {
        if (ref.equals(this.seq)) {
            return true;
        } else {

            int minLen = ref.length();
            for (int i = 0; i != minLen; i++) {
                char chA = ref.charAt(i);
                char chB = this.seq.charAt(i);
                if (chA != chB) {
                    int position = i+start;
                    // this is a snp...
                    SNP snp = new SNP(chA,chB,position);
                }
            }


        }return false;


    }


}
