 package com.ernstthuer;

public class Main {

    public static void main(String[] args) {

        ArgParse parser = new ArgParse(args);

        for (File file:parser.fileList){
            System.out.println(file.isExistant());
        }













	// write your code here
    }
}
