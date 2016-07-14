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

    public HashSet<String> readBam(){
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
                System.out.println(rec);
            }
            CloserUtil.close(fileBam);



        }catch(Exception e){
            System.out.println(e);

        }

            /*final SamReaderFactory factory =
                SamReaderFactory.makeDefault().enable(SamReaderFactory.Option.INCLUDE_SOURCE_IN_RECORDS)
                //.validationStringency(ValidationStringency.valueOf(ValidationStringency.SILENT))
                ;
                */


        //SamReaderFactory.makeDefault().open();
        //final SamReader fileBam = factory.open(new File(this.locale));
        /*while(fileBam.iterator().hasNext()){
            System.out.println(fileBam.iterator().next());
        }*/




        HashSet<String> outSet = new HashSet<>();
        return outSet;
    }

}
