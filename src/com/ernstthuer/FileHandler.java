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




    public FileHandler(String locale, String type, String direction) {
        this.locale = locale;
        this.type = type;
        this.direction = direction;
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



    public LinkedHashMap<String, DNASequence> readFasta() throws IOException {

        //List<String> seqList ;
        LinkedHashMap<String, DNASequence> fastaMap;
        try {
            File file = new File(this.locale);
            fastaMap = FastaReaderHelper.readFastaDNASequence(file);
            for (Map.Entry<String, DNASequence> entry : fastaMap.entrySet()) {
                System.out.println(entry.getValue().getOriginalHeader());

            }
            return fastaMap;
        } catch (IOException e) {
            e.printStackTrace();
            fastaMap = null;
            return fastaMap;
            }

        }





    public HashMap<String, Read> readBam(LinkedHashMap fastaMap) {
        HashMap<String,Read> ReadMap;
        try {
            final SamFileValidator validator = new SamFileValidator(new PrintWriter(System.out), 8000);
            validator.setIgnoreWarnings(true);
            validator.setVerbose(true, 1000);
            validator.setErrorsToIgnore(Collections.singletonList(SAMValidationError.Type.MISSING_READ_GROUP));
            SamReaderFactory factory = SamReaderFactory.makeDefault().validationStringency(ValidationStringency.LENIENT);
            SamReader fileBam = factory.open(new File(this.locale));
            final SAMRecordIterator iterator = fileBam.iterator();
            while (iterator.hasNext()) {

                final SAMRecord rec = iterator.next();
                if(rec.getInferredInsertSize() == 0 && !rec.getReadUnmappedFlag()) {

                    /**
                     * Is there a reference sequence saved in SamReader ??
                     */

                    //DNASequence readSeq = rec.getReadString();
                    DNASequence readSeq = new DNASequence(rec.getReadString());

                    // store reads in genes   no, better for memory to just store the SNP occurrences, no need for the rest
                    Read read = new Read(readSeq, rec.getAlignmentStart(), rec.getAlignmentEnd());
                    DNASequence reference = new DNASequence(fastaMap.get(rec.getReferenceName()).toString().substring(rec.getAlignmentStart(),rec.getAlignmentEnd()));
                    read.findSNPs(reference);
                    //System.out.println(fastaMap.keySet().contains(rec.getReferenceName())); //contains(rec.getReferenceName()));
                    //System.out.println();

                }
                //read.findSNPs(rec.getr);
                //System.out.println(rec.getAttributes());
                //System.out.println(rec.getReferenceName());
                //System.out.println("ToString " +rec.toString());
            }
            CloserUtil.close(fileBam);

        }catch(Exception e){
            System.out.println(e);

        }




        HashMap<String, Read> outSet = new HashMap<>();
        return outSet;
    }

}
