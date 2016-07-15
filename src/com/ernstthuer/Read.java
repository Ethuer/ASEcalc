package com.ernstthuer;

import org.biojava.nbio.core.sequence.DNASequence;


import java.util.Iterator;
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
    private DNASequence reference;
    private int start;
    private int length;
    //private List<SNP> snips ;

    public Read(DNASequence seq,DNASequence reference, int start, int length) {
        this.seq = seq;
        this.reference = reference;
        this.start = start;
        this.length = length;
    }

    public String whichGene(){
        return "Gene";
    }

    public SNP findSNPs(DNASequence ref) {
        if (ref.equals(this.seq)) {
            return null;
        } else {

            int count = 0;
            System.out.println("Here");

            for(int i = 1; i < this.seq.getLength(); i++){
                if(this.seq.getCompoundAt(i) != ref.getCompoundAt(i)){
                    SNP snp = new SNP(this.gene, this.seq.toString().charAt(i),ref.toString().charAt(i),i);
                    if(!snips.contains(snp)){
                        snips.add(snp);
                    }
                }


            }

//            while(this.seq.iterator().hasNext()){
//
//                //System.out.println("Hier");
//                char ORG = this.seq.next();
//                count +=1;
//                if(this.seq.iterator().next() != ref.iterator().next()){
//
//                    this.seq.iterator().next().toString();
//                    SNP snp = new SNP(this.gene, this.seq.iterator().next().toString().charAt(0),ref.iterator().next().toString().charAt(0),count);
//
//                    if(!snips.contains(snp)){
//                        snips.add(snp);
//
//                    }
//                    return snp;
//
//                }
//            }
            return null;
        }
    }
}
