package com.ernstthuer;

import org.biojava.nbio.core.sequence.DNASequence;

import java.util.ArrayList;
import java.util.HashMap;

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



    }





}
