package com.ernstthuer;
import htsjdk.samtools.*;
import htsjdk.samtools.util.CloserUtil;
import htsjdk.samtools.ValidationStringency;
import htsjdk.samtools.SamFileValidator;
//import htsjdk.samtools.util.CloserUtil;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Pattern;

import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.io.DNASequenceCreator;
import org.biojava.nbio.core.sequence.io.FastaReader;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;
import org.biojava.nbio.core.sequence.io.GenericFastaHeaderParser;
import org.biojava.nbio.core.sequence.template.Sequence;



/**
 * Created by ethur on 7/13/16.
 *
 * General Class to handle the individual files,
 * handles import of fasta and bam files.
 *
 *
 */
public class FileHandler{
    private String locale;
    private String type;
    private String direction;
    private String feature;

    public FileHandler(String locale, String type, String direction) {
        this.locale = locale;
        this.type = type;
        this.direction = direction;
    }

    public FileHandler(String locale, String type, String direction, String feature) {
        this.locale = locale;
        this.type = type;
        this.direction = direction;
        this.feature = feature;
    }


    public String getLocale() {
        return locale;
    }

    public String getType() {
        return type;
    }

    public String getDirection() {
        return direction;
    }

    public boolean isExistant(){
        File file = new File(this.locale);
        if(file.exists() && !file.isDirectory()) {
            return true;
        }
        else{
            return false;
        }
    }

    public ArrayList<Gene> gffParser(){
        GFFreader gffreader = new GFFreader(this.locale,this.feature );
        ArrayList<Gene> geneList = gffreader.geneList;
        return geneList;
    }


    public HashMap<String, DNASequence> readFasta(ArrayList<Gene> geneList) throws IOException {

        /**
         * Method supplied by biojava to read fasta file into hashMap
         *
         *I should read the nucleotide sequence into potential
         *
         */

        // check if chromosomes or genes were used as reference ....





        HashMap<String, DNASequence> fastaMap;
        try {
            File file = new File(this.locale);
            fastaMap = FastaReaderHelper.readFastaDNASequence(file);


            for(Gene gene:geneList){
                if(fastaMap.containsKey(gene.getChromosome())){
                    //it's on the chromosome
                    gene.loadSequence(fastaMap.get(gene.getChromosome()),true);
                }
                else{
                    //genes were used directly
                    gene.loadSequence(fastaMap.get(gene.getChromosome()),false);
                    //gene.loadSequence(fastaMap.v);

                }


            }
        /*    for (Map.Entry<String, DNASequence> entry : fastaMap.entrySet()) {
                System.out.println(entry.getValue().getOriginalHeader());
            }*/
            return fastaMap;
        } catch (IOException e) {
            e.printStackTrace();
            fastaMap = null;
            return fastaMap;
            }
        }


    public HashMap<String, Read> readBam(HashMap fastaMap, ArrayList<SNP> snips) {

        /**
         * reads a Bam file, stores SNPs.  check if there are gene names used as reference, or chromosome names.
         * associate SNPs to genes.  SNP by gene will be the main SNP storage.
         *
         *
         */
        HashMap<String,Read> ReadMap = new HashMap<>();
        try {
            final SamFileValidator validator = new SamFileValidator(new PrintWriter(System.out), 8000);
            validator.setIgnoreWarnings(true);
            validator.setVerbose(true, 1000);
            validator.setErrorsToIgnore(Collections.singletonList(SAMValidationError.Type.MISSING_READ_GROUP));
            SamReaderFactory factory = SamReaderFactory.makeDefault().validationStringency(ValidationStringency.STRICT);
            SamReader fileBam = factory.open(new File(this.locale));
            SAMRecordIterator iterator = fileBam.iterator();
            //System.out.println(iterator.toList().size());
            int count = 0;
            while (iterator.hasNext()) {

                SAMRecord rec = iterator.next();
                DNASequence readSeq = new DNASequence(rec.getReadString());


                if (!rec.getReadUnmappedFlag()) {
                    //System.out.println("Quality : " + rec.getMappingQuality());


                    /**
                    // This is the mz score,  use that instead of sequences....

                    // potential quality filter ...   System.out.println(rec.getBaseQualityString());
                    String MZ = rec.getSAMString().split("\t")[11].split(":")[2];
                    //System.out.println(MZ);

                    DNASequence reference = new DNASequence(fastaMap.get(rec.getReferenceName()).toString().substring(rec.getAlignmentStart() - 1, rec.getAlignmentEnd()));



                    parseMZscore(rec.getAlignmentStart() , rec.getReferenceName(),  MZ, fastaMap.get(rec.getReferenceName()).toString());

                     //*/
                    //SNP found = new
                    //System.out.println(rec.getSAMString().split("\t")[11].split(":")[2]);
                    // Consider giving the reads to the individual genes, then deleting the genes without SNPs

                    ///** Legacy,  reduce this to MZ string handling, creates

                    DNASequence reference = new DNASequence(fastaMap.get(rec.getReferenceName()).toString().substring(rec.getAlignmentStart() - 1, rec.getAlignmentEnd()));

                    // check if it was mapped against chromosomes or genes

                    new Read( rec.getReferenceName(), snips, readSeq, reference, rec.getAlignmentStart(), rec.getAlignmentEnd());
                    //*/

                    count ++;

                    if(count % 10000 == 0){
                        System.out.println("Counting " +count+" Reads");
                    }


                }
            }
            System.out.println("Number of reads" + count);
            CloserUtil.close(fileBam);
        } catch (Exception e) {
            System.out.println(e);
        }
        //HashMap<String, Read> outSet = new HashMap<>();
        return ReadMap;
    }


    public void parseMZscore(int start , String gene,  String mz, String reference){
        int index = start;

        // starts with digits
        //split by all nondigits
        String[] mzArray = mz.split("\\D");

        if (mzArray.length > 1) {
            //System.out.println(mzArray[0]);
            /**
             * has a mutation
             * find the mutation
             */
            for (int i = 0; i < mzArray.length; i++) {
                System.out.println("MZ arrayloop" +mzArray[i]);
                if (mzArray[i].contains("\\D")) {

                    System.out.println("HERE");

                    //this is a SNP, its position is start+(i-1)
                    //get realvalue

                    char ORG = "N".charAt(0);//reference.getCompoundAt(java.lang.Integer.parseInt(mzArray[(i - 1)])).toString().charAt(0);
                    System.out.println(ORG);
                    try {
                        index = start + java.lang.Integer.parseInt(mzArray[(i - 1)]);
                        SNP snp = new SNP(gene, ORG, mzArray[i].charAt(0), index);
                        System.out.println(ORG + "    :    " + mzArray[i].charAt(0));
                    } catch (Exception e) {
                        //is SNP is at position 0,  htere should be a 0 in the beginning
                        System.out.println(e);
                        index = start;
                        SNP snp = new SNP(gene, ORG, mzArray[i].charAt(0), index);
                    }

                }
            }




        }

       //if(mz.contains("A") ||mz.contains("T") || mz.contains("G")||   mz.contains("C") || mz.contains("N") );

    }

}
