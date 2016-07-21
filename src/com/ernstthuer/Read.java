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
     * string gene is the reference,  this can also mean gene.  needs a special method to test..
     *
     */
    //public static ArrayList<SNP> snips = ;
    private String gene;
    private DNASequence seq;
    private DNASequence reference;
    private int start;
    private int length;
    static boolean mappedAgainstChromosomes ;
    //private List<SNP> snips ;

    public Read(ArrayList<Gene> geneList, String gene, ArrayList<SNP> snips , DNASequence seq, DNASequence reference, int start, int length, String MZ) {
        this.gene = gene;
        this.seq = seq;
        this.reference = reference;
        this.start = start;
        this.length = length;

        //check if read contains known SNP position

        // find which gene this belongs to , then store it there

        for(Gene possibleGene:geneList){
            if(possibleGene.getIdent() == gene){
                //mapped against gene, easy.
                SImpleRead spl = new SImpleRead(start,MZ);
                  possibleGene.addRead(spl);



            }
            if(possibleGene.getChromosome() == gene){

            }
        }




        try {
            SNP snip = findSNPs(this.reference);
            //snips.add(snip);
            if (!snips.contains(snip)) {
                snips.add(snip);
            }
            if(snips.contains(snip)){
                try {
                    //System.out.println(snips.get(snips.indexOf(snip)).getALTcov());
                    int cov = snips.get(snips.indexOf(snip)).getALTcov();
                    cov++;
                    snips.get(snips.indexOf(snip)).setALTcov(cov);

                    if(cov > 5 ){
                        snips.get(snips.indexOf(snip)).setValidated(1);
                    }
                    //System.out.println(" current coverage " + cov);
                }catch(Exception e){
                    /*System.out.println(e);
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    sw.toString();
                    System.out.println(sw);
                    */
                }
            }
            /*
            if(snips.contains(snip))
            {
                System.out.println(snips.get(snips.lastIndexOf(snip)).toString());
                //int cov = snips.get(snips.lastIndexOf(snip)).getALTcov();
                //cov ++;
                //snips.get(snips.lastIndexOf(snip)).setALTcov(cov);
            }
            */
        }
            catch (Exception e) {
                System.out.println("[Warning] Malformed Read encountered");
                /*StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                sw.toString();
                System.out.println(sw);
                */
            }
        }


    public String whichGene(){
        return "Gene";
    }

    public String getGene() {
        return gene;
    }

    public SNP findSNPs(DNASequence ref) {
        if (this.reference == this.seq){
        }

        if (this.reference != this.seq) {
            int end = this.start + this.seq.getLength();
            for (int i = 1; i < this.seq.getLength(); i++) {
                if (!this.seq.getCompoundAt(i).toString().equals(ref.getCompoundAt(i).toString())) {
                    //System.out.println("Compare " + this.seq.getCompoundAt(i).toString() +ref.getCompoundAt(i).toString() );
                    int pos = i + this.start;
                    try {
                        //System.out.println();
                        SNP snp = new SNP(this.gene, this.seq.toString().charAt(i), ref.toString().charAt(i), pos);
                        //System.out.println("found SNP in :" + this.seq.toString().charAt(i) );
                        return snp;
                    } catch (NullPointerException e) {
                        //System.out.println(this.gene + this.seq.toString().charAt(i) + ref.toString().charAt(i) + pos);
                        System.out.println("cannot create SNP " + e);
                    }
                }
            }
        }
        return null;
    }

    public boolean checkSNP(SNP snip, ArrayList<SNP> snips){
        Iterator<SNP> iter = snips.iterator();

        while(iter.hasNext()){
            SNP data = iter.next();

            if(snip.toString() == data.toString()){
                System.out.println("Found");
                return true;
            }

        }
        return false;
    }

    /*
    public SNP findExisingSNP(ArrayList<SNP> snips){






    }
    */

}
