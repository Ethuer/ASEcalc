package com.ernstthuer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

/**
 * Created by ethur on 7/18/16.
 */
public class GFFreader {

    /**
     * GFF files description columns are diverse and parsing should implement a Sniffer, or preset choices.
     */

    //public enum feature {
    //    GENE, EXON, CDS, TRANSCRIPT
    //}

    private String direction;

    private static String[] lineList;
    private String feature;
    public static List<Gene> geneList;
    private static String type = null;



    public GFFreader(String direction, String feature) {
        this.direction = direction;
        this.feature = feature;

        try {
            this.lineList = openGFF(direction);
        } catch (IOException e) {
            System.out.println("GFF file not found");
            System.out.println(e.getStackTrace());
        }

        geneList = geneList(this.lineList);
    }

    public String[] openGFF(String direction) throws IOException {

        FileReader reader = new FileReader(direction);
        BufferedReader bufferedReader = new BufferedReader(reader);
        int numberOfLines = countLines(bufferedReader);
        String[] linesOfFeatures = new String[numberOfLines];

        for (int i = 0; i < numberOfLines; i++) {
            linesOfFeatures[i] = bufferedReader.readLine();
        }
        bufferedReader.close();
        return linesOfFeatures;

    }

    public int countLines(BufferedReader reader) throws IOException {

        String hasLine;
        int numberOfLines = 0;

        while ((hasLine = reader.readLine()) != null) {
            numberOfLines++;
        }
        return numberOfLines;

    }

    public List<Gene> geneList(String[] featureList) {
        List<Gene> outList = null;
        for (String entry : featureList) {
            String[] row = entry.split("\t");
            if (row[2].equals(this.feature)) {
                // new feature here
                String description = descriptionParser(row[8]);
                int start = parseInt(row[3]);
                int stop = parseInt(row[4]);
                Gene newGene = new Gene(row[0], start, stop, description );
                outList.add(newGene);
            }
        }
        return outList;
    }


    public String descriptionParser(String fullDescription) {

        String featureID;
        String[] desc = fullDescription.split(";");

        Pattern CGD = Pattern.compile("ID=*;");
        Pattern ENSEMBL = Pattern.compile("gene_id \"*\";");
        Pattern ENSEXP = Pattern.compile("hid=trf;");

        // Simple sniffer
        while (type == null) {
            for (String element : desc) {
                if (element.contains("ID")) {
                    type = "CGD";
                }
                if (element.contains("gene_id")) {
                    type = "ENSEMBL";
                }
                if (element.contains("gid")) {
                    type = "ENSEXP";
                }
            }
        }

        switch (type) {
            case "CGD":
                for(String element: desc){
                    if(element.contains("ID")){
                        featureID = element.split("=")[1];
                        return featureID;
                    }
                }
                break;

            case "ESEMBL":
                for(String element: desc){
                    if(element.contains("_id")){
                        featureID = element.split("\"")[1];
                        return featureID;
                    }
                }
                break;

            case "ENSEXP":
                break;


        }
        return null;

    }
}
