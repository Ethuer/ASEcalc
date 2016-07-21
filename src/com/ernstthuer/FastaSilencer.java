package com.ernstthuer;

import org.biojava.nbio.core.exceptions.CompoundNotFoundException;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.io.DNASequenceCreator;
import org.biojava.nbio.core.sequence.io.FastaWriterHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;



/**
 * Created by ethur on 7/20/16.
 */
public class FastaSilencer {
    /**
     * Invokes a set of methods to modify fasta sequences.
     *
     *  needs all SNIPs,  and the fasta sequence
     *
     *
     *  could also transfer SNPs to genes
     *
     */


    private HashMap<String, DNASequence> fasta = new HashMap<>();
    private ArrayList<SNP> snips = new ArrayList<>();


    public FastaSilencer(ArrayList<SNP> snips, HashMap<String, DNASequence> fasta) {
        this.fasta = fasta;
        this.snips = snips;

        // find positions on the fasta, then silence and write to output

        for(String i : fasta.keySet()){
            //System.out.println(i + fasta.get(i));
            DNASequence toSilence = fasta.get(i);
            StringBuilder buildStringFasta = new StringBuilder(toSilence.getSequenceAsString());

            // find all SNPs on that sequence in an ArrayList subset,  then change the positions to 'N'

            Iterator<SNP> snpIterator = snips.iterator();

            while(snpIterator.hasNext()){
                SNP snp = snpIterator.next();
                try {
                    if (snp.getGene().equals(i)) {

                        int position = snp.getPosition();
                        char ORG = snp.getORG();
                        //DNASequence thisFasta = fasta.get(i);
                        //System.out.println(buildStringFasta.substring((position - 2), (position + 1)) + " " + ORG);
                        buildStringFasta.setCharAt(position-1, 'N');
                        //System.out.println(buildStringFasta.substring((position - 2), (position + 1)) + " " + ORG);
                    }

                }
                catch(NullPointerException e){
                    System.out.println(e);
                    //System.out.println(snp.toString());
                }
            }

            try {
                DNASequence dna = new DNASequence(buildStringFasta.toString());
                dna.setDescription(i);

                System.out.println(dna.getLength());

                fasta.put(i,dna);

            }catch (CompoundNotFoundException e){
                System.out.println(e);
            }


            //System.out.println(buildStringFasta);



        }


        String filename = "/home/ethur/IdeaProjects/ACEcalc0.31/out/TestFile.fasta";
        // new fasta file should be available here...
        try {
            BufferedOutputStream outFast = new BufferedOutputStream(new FileOutputStream(filename));

            //for(DNASequence outSeq : fasta.values()) {
            FastaWriterHelper.writeNucleotideSequence(outFast, fasta.values());  //(outFast, i);
            System.out.println("[STATUS] Writing masked FASTA to file");
            //}
            }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
