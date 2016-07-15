package com.ernstthuer;

import org.biojava.nbio.core.sequence.DNASequence;


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
    private DNASequence seq;
    private int start;
    private int length;
    //private List<SNP> snips ;

    public Read(DNASequence seq, int start, int length) {
        this.seq = seq;
        this.start = start;
        this.length = length;
    }

    public String whichGene(){
        return "Gene";
    }

    public boolean findSNPs(DNASequence ref) {
        if (ref.equals(this.seq)) {
            return true;
        } else {

            int count = 0;
            while(this.seq.iterator().hasNext()){
                count +=1;
                if(this.seq.iterator().next() != ref.iterator().next()){
                    this.seq.iterator().next().toString();
                    SNP snp = new SNP(this.gene, this.seq.iterator().next().toString().charAt(0),ref.iterator().next().toString().charAt(0),count);
                    snips.add(snp);

                }
            }


            /*int minLen = ref.length();
            for (int i = 0; i != minLen; i++) {
                char chA = ref.charAt(i);
                char chB = this.seq.charAt(i);
                if (chA != chB) {
                    int position = i+start;
                    SNP snp = new SNP(this.gene,chA,chB,position);
                    snips.add(snp);
                }
            }
            */
            return false;
        }
    }
}
