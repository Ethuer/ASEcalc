package com.ernstthuer;

import htsjdk.samtools.*;

import java.io.File;

/**
 * Created by ethur on 7/13/16.
 */
public class BamHandler extends SamReaderFactory {

    private String location;



    public BamHandler(String location) {
        this.location = location;

    }

    @Override
    public SamReader open(File file) {
        return null;
    }

    @Override
    public SamReader open(SamInputResource samInputResource) {
        return null;
    }

    @Override
    public ValidationStringency validationStringency() {
        return null;
    }

    @Override
    public SamReaderFactory samRecordFactory(SAMRecordFactory samRecordFactory) {
        return null;
    }

    @Override
    public SamReaderFactory enable(Option... options) {
        return null;
    }

    @Override
    public SamReaderFactory disable(Option... options) {
        return null;
    }

    @Override
    public SamReaderFactory validationStringency(ValidationStringency validationStringency) {
        return null;
    }
}
