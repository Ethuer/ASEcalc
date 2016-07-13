package com.ernstthuer;

/**
 * Created by ethur on 7/13/16.
 */
public class File {
    private String locale;
    private String type;
    private String direction;

    public File(String locale, String type, String direction1) {
        this.locale = locale;
        this.type = type;
        this.direction = direction1;


    }

    public boolean isExistant(){
        return true;
    }




}
