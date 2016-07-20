 package com.ernstthuer;

 import com.sun.istack.internal.NotNull;
 import org.biojava.nbio.core.sequence.DNASequence;

 import java.io.IOException;
 import java.util.*;

 public class Main {


     public static void main(String[] args) {
         // make that a hashMap,  SNPs by gene
         // gene list is defined in the GFFreader, but is public
         ArrayList<Chromosome> chromosomeArrayList = new ArrayList<>();
         ArrayList<Gene> geneList = new ArrayList<Gene>();
         ArrayList<SNP> snips = new ArrayList<>();
         //List<SNP> snips;
         HashMap<String, DNASequence> fasta = new HashMap<>();
         ArgParse parser = new ArgParse(args);
         /**
          * First load the reference, then the bam file(s)
          *
          *
          */
         for (FileHandler file : parser.fileList) {
             if(file.getType() == "GFF" && file.getDirection() == "Input"){
                 System.out.println("[STATUS]  parsing GFF file");
                 try {
                     geneList = file.gffParser();
                 }catch(Exception e){
                     System.out.println(e);
                 }
             }

             if (file.getType() == "FASTA" && file.getDirection() == "Input") {
                 try {
                     fasta = file.readFasta();
                     System.out.println("Read fasta");
                 } catch (IOException e) {
                     System.out.println(e.getCause());
                     fasta = null;
                 }
             }
         }

         //System.out.println("Amount of " + feature + geneList.size());

         /*
         Iterator<Gene> geneIterator = geneList.iterator();
         while(geneIterator.hasNext()){

             Gene gene = geneIterator.next();
             String name = gene.getChromosome();


             if(chromosomeArrayList.contains(name)){
                 System.out.println("found");
                 Chromosome chromosome =  chromosomeArrayList.get(chromosomeArrayList.indexOf(name));
                 chromosome.addGene(gene);
                 System.out.println("Added gene  : " + gene.getIdent() );
             }
             if(!chromosomeArrayList.contains(name)){
                 System.out.println("Not found");
                 Chromosome chromosome = new Chromosome(name);
                 chromosome.addGene(gene);
                 System.out.println("Added gene to new chromosome  : " + gene.getIdent() );
                 chromosomeArrayList.add(chromosome);
             }

         }
         System.out.println(chromosomeArrayList.size());

         Iterator<Chromosome> itera = chromosomeArrayList.iterator();
         while(itera.hasNext()){
             System.out.println(itera.next().getName());
         }

         */



         for (FileHandler bamfile : parser.fileList) {
             if (bamfile.getType() == "Bam") {
                 if (fasta != null) {
                     bamfile.readBam(fasta, snips);
                 } else {
                     System.out.println("No reference loaded");
                 }
             }
         }


         System.out.println("Amount of SNPs found " + snips.size());
         Iterator<SNP> iter = snips.iterator();

         //  Purge the nonvalidated SNPs with validation below threshold of 1.  1 denominates minimum read count

         int remainCount = 0;
         int removedCount = 0;
         while (iter.hasNext()){
             try {
             SNP foundSNP = iter.next();
             if(foundSNP.isValidated() < 1){
                 //snips.remove(snips.indexOf(foundSNP));
                 iter.remove();
                 removedCount++;
             }else{
                 remainCount ++ ;
             }
             }
             catch(NullPointerException e){
                 System.out.println(e);
             }
             //System.out.println(foundSNP.getPosition() + "  " + iter.next().getALTcov());
         }
         System.out.println("Snips still in dictionary " + snips.size());
         System.out.println("Keeping "+remainCount+" SNPs;   Removed " + removedCount + " SNP due to low coverage");
         // write the modified fasta to file for remapping here


         //Iterator fastait = fasta.entrySet().iterator();


         if(parser.isMaskFasta()) {
             new FastaSilencer(snips, fasta);
         }



             /*
         }

         for(String i : fasta.keySet()){
             System.out.println(i + fasta.get(i));
         }

*/
/*
         try {
             while (fastait.hasNext()) {
                 fastait.next();
                 Map.Entry pair = (Map.Entry) fastait.next();
                 System.out.println(pair.getKey() + " = " + pair.getValue());
                 fastait.remove();
             }
         }
         catch (NullPointerException e){
             System.out.println("Fasta read nullpointer exception, expected from map");
         }

*/

         /**
          * ToDo
          *
          * Gene list works,   SNP calling
          *
          *
          */


     }
 }
















