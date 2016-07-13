 package com.ernstthuer;

public class Main {

    public static void main(String[] args) {

        ArgParse parser = new ArgParse(args);

        for (FileHandler file:parser.fileList){
            System.out.println(file.isExistant());
            System.out.println(file.getType());
        }












    }
}
