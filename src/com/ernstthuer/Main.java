 package com.ernstthuer;

 import java.io.IOException;

 public class Main {

    public static void main(String[] args) {

        ArgParse parser = new ArgParse(args);

        for (FileHandler file:parser.fileList){
            System.out.println(file.isExistant());
            System.out.println(file.getType());
            if (file.getType()== "FASTA" && file.getDirection() == "Input"){
                try{
                    file.readFasta();}
                catch(IOException e){
                    System.out.println(e.getCause());
                }

            }
            if (file.getType()== "Bam"){
                file.readBam();
            }

        }














    }
}
