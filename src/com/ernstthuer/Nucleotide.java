package com.ernstthuer;

/**
 * Created by ethur on 7/21/16.
 */
public class Nucleotide{

    private final String gene;
    private final char nucleot;
    private final int position;
    private int count;


    public Nucleotide(String gene, char nucleot, int position) {
        this.nucleot = nucleot;
        this.position = position;
        this.gene = gene;
        this.count = 0;
    }

    public void addCount(){
        this.count++;
    }


    // comparative addition need this for finding nucleotides.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Nucleotide that = (Nucleotide) o;

        return position == that.position;

    }

    @Override
    public int hashCode() {
        return position;
    }
}
