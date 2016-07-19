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


    public LinkedHashMap<String, DNASequence> readFasta() throws IOException {

        LinkedHashMap<String, DNASequence> fastaMap;
        try {
            File file = new File(this.locale);
            fastaMap = FastaReaderHelper.readFastaDNASequence(file);
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


    public HashMap<String, Read> readBam(LinkedHashMap fastaMap, ArrayList<SNP> snips) {

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
            SamReaderFactory factory = SamReaderFactory.makeDefault().validationStringency(ValidationStringency.LENIENT);
            SamReader fileBam = factory.open(new File(this.locale));
            SAMRecordIterator iterator = fileBam.iterator();
            //System.out.println(iterator.toList().size());
            int count = 0;
            while (iterator.hasNext()) {

                SAMRecord rec = iterator.next();
                DNASequence readSeq = new DNASequence(rec.getReadString());
                if (!rec.getReadUnmappedFlag()) {


                    // Consider giving the reads to the individual genes, then deleting the genes without SNPs
                    DNASequence reference = new DNASequence(fastaMap.get(rec.getReferenceName()).toString().substring(rec.getAlignmentStart() - 1, rec.getAlignmentEnd()));
                    new Read(snips, readSeq, reference, rec.getAlignmentStart(), rec.getAlignmentEnd());
                    count++;

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

}
