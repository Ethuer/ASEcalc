package com.ernstthuer;

import org.biojava.nbio.core.sequence.DNASequence;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ethur on 7/14/16.
 */
public class Read{
    /**
     * Class of read observation, loaded from a bam file, iterates , and finds mismatches to call all SNPs
     * the putative SNPs should be populated by bootstrapping over samples,
     *
     * There has to be a more elegant way to load the sequence than
     *
     */
    //public static ArrayList<SNP> snips = ;
    private String gene;
    private DNASequence seq;
    private DNASequence reference;
    private int start;
    private int length;
    //private List<SNP> snips ;

    public Read(ArrayList<SNP> snips , DNASequence seq, DNASequence reference, int start, int length) {
        this.seq = seq;
        this.reference = reference;
        this.start = start;
        this.length = length;

        try {
            SNP snip = findSNPs(this.reference);
            if (snip != null && !snips.contains(snip)) {
                snips.add(snip);
                //System.out.println(snip.isValidated());
            }
        }
            catch (Exception e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                sw.toString();
                System.out.println(sw);
            }
        }


    public String whichGene(){
        return "Gene";
    }

    public String getGene() {
        return gene;
    }

    public SNP findSNPs(DNASequence ref) {
        //if (this.reference == this.seq) {
        //    return null;
        //} else {




        if(this.reference != this.seq){
            int end = this.start+this.seq.getLength();
            for(int i = 1; i < this.seq.getLength(); i++){
                if(!this.seq.getCompoundAt(i).toString().equals(ref.getCompoundAt(i).toString())){

                    int pos = i + this.start;
                    try {

                        //System.out.println();

                        SNP snp = new SNP(this.gene, this.seq.toString().charAt(i), ref.toString().charAt(i), pos);
                        //System.out.println("found SNP in :" + this.seq.toString().charAt(i) );
                        return snp;
                    }catch(NullPointerException e){
                        //System.out.println(this.gene + this.seq.toString().charAt(i) + ref.toString().charAt(i) + pos);
                        System.out.println("cannot create SNP " +e);
                    }
                    //snips.add(snp);
                    //if(!snips.contains(snp)){
                        //snips.add(snp);
                        //return snp;
                        //System.out.println(snips.size());
                        //System.out.println("Here");
                   /* }else{
                        int arg = snp.getALTcov();
                        arg++;
                        snp.setALTcov(arg);
                    }*/
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

        }
    return null;}
}
