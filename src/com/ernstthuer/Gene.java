package com.ernstthuer;

import org.biojava.nbio.core.exceptions.CompoundNotFoundException;
import org.biojava.nbio.core.sequence.DNASequence;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ethur on 7/18/16.
 */
public class Gene {

    /**
     * The term Gene is used loosely,  this could be also refering to exon or transctips.
     */
    private ArrayList<Nucleotide> sequenceCoverage = new ArrayList<>();
    private DNASequence sequence = new DNASequence();
    private String chromosome;
    private int start;
    private int stop;
    private String ident;
    private List<SNP> geneSNPList = new ArrayList<>();
    private List<Read> geneReadList = new ArrayList<>();
    private String ASE;

    public Gene(String chromosome, int start, int stop, String description) {
        this.chromosome = chromosome;
        this.start = start;
        this.stop = stop;
        this.ident = description;
    }

    public String getChromosome() {
        return chromosome;
    }

    public int getStart() {
        return start;
    }

    public int getStop() {
        return stop;
    }

    public String getIdent() {
        return ident;
    }

    public void loadSequence(DNASequence fullGenome, boolean fullChrom) {
        if (fullChrom) {
            try {
                this.sequence = new DNASequence(fullGenome.getSubSequence(this.start, this.stop).toString());
            } catch (CompoundNotFoundException e) {
                System.out.println("coldn't parse fasta sequence " + e);
            }
        } else {
            try {
                this.sequence = fullGenome;
            } catch (Exception e) {
                System.out.println("coldn't parse fasta sequence " + e);
            }
        }

    }
}

