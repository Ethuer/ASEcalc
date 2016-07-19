package com.ernstthuer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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

    private String direction;
    private static String[] lineList = null;
    private String feature;
    public static ArrayList<Gene> geneList;
    private static String type = null;

    public GFFreader(String direction, String feature) {
        this.direction = direction;
        this.feature = feature;
        try {
            this.lineList = openGFF(direction);
        } catch (IOException e) {
            System.out.println("GFF file not found");
            System.out.println(e);
        }
        geneList = geneList(this.lineList);
    }

    public String[] openGFF(String locale) throws IOException {
        ArrayList<String> outList = new ArrayList<String>();
        //System.out.println("This is where the file is " + direction);
        try(BufferedReader br = new BufferedReader(new FileReader(locale))){

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                outList.add(sCurrentLine);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        String[] stockArr = new String[outList.size()];
        String[] linesOfFeatures = outList.toArray(stockArr);

        return linesOfFeatures;

    }


    public ArrayList<Gene> geneList(String[] featureList) {
        ArrayList<Gene> outList = new ArrayList<>();

        for (int i = 0; i  <  featureList.length ; i++) {
            String[] row = featureList[i].split("\t");
            if (row[2].equals(this.feature)) {
                String description = descriptionParser(row[8]);
                int start = parseInt(row[3]);  //start and stop position are read as String
                int stop = parseInt(row[4]);
                Gene newGene = new Gene(row[0], start, stop, description );
                outList.add(newGene);
                //System.out.println(outList.size());//geneList.add(newGene);
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
                if (element.contains("geneID")){
                    type = "ALTERNATIVE";
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

            case "ALTERNATIVE":
                for(String element: desc){
                    if(element.contains("ID")){
                        featureID = element.split("\"")[1];
                        return featureID;
                    }
                }
                break;


        }
        return null;

    }
}
