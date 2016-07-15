package com.ernstthuer;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;


/**
 * Created by Ernst Thuer  on 7/13/16.
 */
public class ArgParse {
    public static ArgumentParser parser = ArgumentParsers.newArgumentParser("Checksum").defaultHelp(true).description("ACEcalc");
    public static List<FileHandler> fileList = new ArrayList<>();

    public ArgParse(String[] args) {

        this.parser.addArgument("-f","--fasta")
                .help("input file in FASTA format").required(true).dest("inFasta");
        this.parser.addArgument("-o","--outfile")
                .help("output file in FASTA format").required(false).setDefault("output.csv").dest("outFinal");
        this.parser.addArgument("-m","--mask")
                .choices("True","False").setDefault(true).dest("mask").help("create an intermediate masked FASTA");
        this.parser.addArgument("-mo","--maskFastaOutput")
                .dest("mOut").setDefault("null").help("write an intermediate masked FASTA to file");
        this.parser.addArgument("-b", "--bamInput")
                .required(true).dest("bamInput").nargs("+");



        Namespace ns = null;


        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }

        System.out.println(ns);

        for(Object element : ns.getList("bamInput")){
            try {
                //System.out.println(element);
                FileHandler bamFile = new FileHandler( element.toString(), "Bam", "Input");
                fileList.add(bamFile);
            }catch(NullPointerException e){
                System.out.println(e);
            }

        }

        FileHandler inFasta = new FileHandler(ns.get("inFasta").toString(),"FASTA","Input");
        FileHandler outFasta = new FileHandler(ns.get("mOut").toString(),"FASTA","Output");
        FileHandler finalOut = new FileHandler(ns.get("outFinal").toString(),"vcf","Output");




        fileList.add(inFasta); fileList.add(outFasta);fileList.add(finalOut);



/*
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance(ns.getString("file"));
        } catch (NoSuchAlgorithmException e) {
            System.err.printf("Could not get instance of algorithm %s: %s",
                    ns.getString("type"), e.getMessage());
            System.exit(1);
        }

        for (String name :ns.<String> getList("file")){
            System.out.println(name);

        }
*/

/*
        for (String name : ns.<String> getList("file")) {
            Path path = Paths.get(name);
            try (ByteChannel channel = Files.newByteChannel(path,
                    StandardOpenOption.READ);) {
                ByteBuffer buffer = ByteBuffer.allocate(4096);
                while (channel.read(buffer) > 0) {
                    buffer.flip();
                    digest.update(buffer);
                    buffer.clear();
                }
            } catch (IOException e) {
                System.err
                        .printf("%s: failed to read data: %s", e.getMessage());
                continue;
            }
            byte md[] = digest.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0, len = md.length; i < len; ++i) {
                String x = Integer.toHexString(0xff & md[i]);
                if (x.length() == 1) {
                    sb.append("0");
                }
                sb.append(x);
            }
            System.out.printf("%s  %s\n", sb.toString(), name);
        }*/


    }

//
//    try {
//        ns = parser.parseArgs(args);
//    } catch (ArgumentParserException e) {
//        parser.handleError(e);
//        System.exit(1);
//    }


    public static void addArg(ArgumentParser parser){
        parser.addArgument();
    }

















//
//
//            addArgument("-t", "--type")
//            .choices("SHA-256", "SHA-512", "SHA1").setDefault("SHA-256")
//    .help("Specify hash function to use");
//    parser.addArgument("file").nargs("*")
//    .help("FileHandler to calculate checksum");
//    Namespace ns = null;
//    try {
//        ns = parser.parseArgs(args);
//    } catch (ArgumentParserException e) {
//        parser.handleError(e);
//        System.exit(1);
//    }
//    MessageDigest digest = null;
//    try {
//        digest = MessageDigest.getInstance(ns.getString("type"));
//    } catch (NoSuchAlgorithmException e) {
//        System.err.printf("Could not get instance of algorithm %s: %s",
//                ns.getString("type"), e.getMessage());
//        System.exit(1);
//    }
//



}
