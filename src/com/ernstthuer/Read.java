package com.ernstthuer;

import java.util.List;

/**
 * Created by ethur on 7/14/16.
 */
public class Read {
    /**
     * Class of read observation, loaded from a bam file, iterates , and finds mismatches to call all SNPs
     * the putative SNPs should be populated by bootstrapping over samples,
     *
     * There has to be a more elegant way to load the sequence than
     *
     */
    public static List<SNP> snips;
    private String gene;
    private String seq;
    private int start;
    private int length;
    //private List<SNP> snips ;

    public Read(String seq, int start, int length) {
        this.seq = seq;
        this.start = start;
        this.length = length;
    }

    public String whichGene(){
        return "Gene";
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
                    SNP snp = new SNP(this.gene,chA,chB,position);
                    snips.add(snp);
                }
            }
            return false;
        }
    }
}
