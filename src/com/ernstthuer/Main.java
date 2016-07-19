 package com.ernstthuer;

 import org.biojava.nbio.core.sequence.DNASequence;

 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.LinkedHashMap;
 import java.util.List;

 public class Main {


     public static void main(String[] args) {
         // make that a hashMap,  SNPs by gene
         // gene list is defined in the GFFreader, but is public

         ArrayList<Gene> geneList = new ArrayList<Gene>();
         ArrayList<SNP> snips = new ArrayList<>();
         //List<SNP> snips;
         LinkedHashMap<String, DNASequence> fasta = null;
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

         for (FileHandler bamfile : parser.fileList) {
             if (bamfile.getType() == "Bam") {
                 if (fasta != null) {
                     bamfile.readBam(fasta, snips);
                 } else {
                     System.out.println("No reference loaded");
                 }
             }
         }


         System.out.println(snips.size());
         Iterator<SNP> iter = snips.iterator();
         while (iter.hasNext()){
             System.out.println(iter.next().getPosition() + "  " + iter.next().getALTcov());
         }
         /**
          * ToDo
          *
          * Gene list works,   SNP calling
          *
          *
          */


     }
 }
















