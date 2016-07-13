package com.ernstthuer;
import htsjdk.samtools.SamReader;
import htsjdk.samtools.SamReaderFactory;

import java.io.File;
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
        File file = new File(this.locale);
        BamHandler infile = new BamHandler(this.locale);

        infile.open(file);

        HashSet<String> outSet = new HashSet<>();
        return outSet;



    }




}
