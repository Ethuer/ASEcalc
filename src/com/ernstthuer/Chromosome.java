package com.ernstthuer;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ethur on 7/20/16.
 */
public class Chromosome {

    /**
     * Needs chromosome distinction,  possibility for ASE on chromosome level.
     */
    private String name;
    private ArrayList<Gene> genesOnChromosome = new ArrayList<>();

    public Chromosome(String name) {
        this.name = name;
    }

    public ArrayList<Gene> getGenesOnChromosome() {
        return genesOnChromosome;
    }

    public void setGenesOnChromosome(ArrayList<Gene> genesOnChromosome) {
        this.genesOnChromosome = genesOnChromosome;
    }

    public void addGene(Gene gene){
        this.genesOnChromosome.add(gene);
        System.out.println("number of genes on chromosome " + genesOnChromosome.size());

        Iterator<Gene> iter = genesOnChromosome.iterator();
        while(iter.hasNext()){
            Gene gene2 = iter.next();
            System.out.println(gene2.getIdent());

        }


    }


    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chromosome that = (Chromosome) o;

        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
