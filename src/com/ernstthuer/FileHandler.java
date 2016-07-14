package com.ernstthuer;
import htsjdk.samtools.*;
import htsjdk.samtools.util.CloserUtil;
import htsjdk.samtools.ValidationStringency;
import htsjdk.samtools.SamFileValidator;
//import htsjdk.samtools.util.CloserUtil;

import java.io.File;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by ethur on 7/13/16.
 *
 * General Class to handle the individual files,  no need for inheriting here, only 3 file types
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




    public HashSet<Read> readBam(){
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
                //Read read = new Read(rec.getCigarString(),rec.getAlignmentStart(),rec.getAlignmentEnd());
                //read.findSNPs()
                System.out.println(rec.getCigarString());
                //System.out.println(rec.getReferencePositionAtReadPosition(1));
                //System.out.println(rec.getCigar());
            }
            CloserUtil.close(fileBam);



        }catch(Exception e){
            System.out.println(e);

        }




        HashSet<Read> outSet = new HashSet<>();
        return outSet;
    }

}
