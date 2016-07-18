package com.ernstthuer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by ethur on 7/18/16.
 */
public class GFFreader {

    /**
     * GFF files are diverse and parsing should implement a Sniffer, or preset choices.
     *
     *
     * @param
     * @return
     * @throws IOException
     */

    public enum feature {GENE,EXON,CDS,TRANSCRIPT};
    private String direction;

    private static String[] lineList;
    private String feature;
    private static List<Gene> geneList;


    public GFFreader(String direction, String feature) {
        this.direction = direction;
        this.feature = feature;

        try {
            this.lineList = openGFF(direction);
        }catch(IOException e){
            System.out.println("GFF file not found");
            System.out.println(e.getStackTrace());
        }


    }

    public String[] openGFF(String direction) throws IOException{

        FileReader reader = new FileReader(direction);
        BufferedReader bufferedReader = new BufferedReader(reader);

        String hasLine;

        int numberOfLines = countLines(bufferedReader);
        String[] linesOfFeatures = new String[numberOfLines];

        for (int i=0; i < numberOfLines; i++) {
            linesOfFeatures[ i ] = bufferedReader.readLine();

        }

        bufferedReader.close( );
        return linesOfFeatures;

    }

    public int countLines (BufferedReader reader) throws IOException {

        String hasLine ;
        int numberOfLines = 0;

        while((hasLine = reader.readLine()) != null){
            numberOfLines ++;
        }
        return numberOfLines;

    }

    public List<Gene> geneList(String[] featureList){
        List<Gene> outList = null;

        for(String entry: featureList){
            String[] row = entry.split("\t");


        }

        return outList;
    }




}
