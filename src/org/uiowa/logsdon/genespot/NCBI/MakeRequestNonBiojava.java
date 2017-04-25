package org.uiowa.logsdon.genespot.NCBI;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.uiowa.logsdon.genespot.JobInformation.Hit;
import org.uiowa.logsdon.genespot.JobInformation.ProteinQuery;
import org.uiowa.logsdon.genespot.JobInformation.Genome;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;


/**
 * Created by austinward on 3/20/17.
 */
public class MakeRequestNonBiojava {

    private static final String newline = "%0D%0A";
    private String QUERIES = "";
    private String DATABASE = "genomic/";
    private Genome genome;

    //eventually will take in current genome and current gene
    public void makeRequestTBLASTN(Genome genome){

        DATABASE = "genomic/";
        QUERIES = "";

        SequenceRetrieval sequenceRetrieval = new SequenceRetrieval();
        DATABASE+=(genome.getTaxID()+"/" + genome.getAssembly());
        this.genome = genome;

        ArrayList<Hit> hits = new ArrayList<>();

        try {

            //create query string - example = 548663%0D%0A514692457
            int i = 0;
            ProteinQuery[] proteinQueries = genome.getProteinQueries();

            while(i < proteinQueries.length){

                if(i != proteinQueries.length - 1){


                    QUERIES+=(proteinQueries[i].getQueryID()+newline);

                }

                else{

                    QUERIES+=proteinQueries[i].getQueryID();
                }


                i++;
            }

            //below is how to have multiple queries
            Document doc = Jsoup.connect("https://blast.ncbi.nlm.nih.gov/Blast.cgi?QUERY="+QUERIES+"&GET_SEQUENCE=on&EXPECT=.000001&PROGRAM=tblastn&DATABASE="+DATABASE+"&CMD=Put").post();

            Element element = doc.body().getElementById("rid");

            //System.out.println(doc);

            String rid = element.attr("value");
            System.out.println(rid);

            Document doc2 = Jsoup.connect("https://blast.ncbi.nlm.nih.gov/Blast.cgi?CMD=Get&RID="+rid+"&FORMAT_TYPE=xml").get();
            //Document doc2 = Jsoup.connect("https://blast.ncbi.nlm.nih.gov/Blast.cgi?CMD=Get&RID=DWZZJM4G016&FORMAT_TYPE=xml" + id).get();

            //System.out.println(doc2);

            try{

                //waiting for job to complete
                while(doc2.body().getElementById("statInfo").attr("class").equals("WAITING")){
                    
                    Thread.sleep(60000);

                    //doc2 = Jsoup.connect("https://blast.ncbi.nlm.nih.gov/Blast.cgi?CMD=Get&RID=DWZZJM4G016&FORMAT_TYPE=xml").get();

                    doc2 = Jsoup.connect("https://blast.ncbi.nlm.nih.gov/Blast.cgi?CMD=Get&RID="+rid+"&FORMAT_TYPE=xml").get();
                    //System.out.println(doc2);
                }

            }

            catch (NullPointerException e){

                e.printStackTrace();
            }

            //doc2 will contain hits in a form with name attribute = overview -- needs parsed to create hit objects

            //System.out.println(doc2);

            Elements hitsXML = doc2.getElementsByTag("hit");

            for(Element currentHit : hitsXML){

                String accessionNumber;
                String[] hold = currentHit.getElementsByTag("hit_id").text().split("\\|");

                //contains GI number
                if(hold.length == 4){

                    accessionNumber = hold[3];

                }

                //no GI number
                else{

                    accessionNumber = hold[1];
                }

                for(Element current : currentHit.getElementsByTag("hit_hsps")){

                    for(Element current2 : current.getElementsByTag("hsp")){

                        //System.out.println(current2.getElementsByTag("hsp_hit-from").text());
                        String hitFrom = current2.getElementsByTag("hsp_hit-from").text();
                        String hitTo = current2.getElementsByTag("hsp_hit-to").text();
                        //System.out.println(current2.getElementsByTag("hsp_hit-to").text());

                        Hit newHit = new Hit(accessionNumber, hitFrom, hitTo);

                        newHit.addHit(hits);
                    }
                }

            }

            sequenceRetrieval.getSequence(hits);

            for(Hit currentHit : hits){

                System.out.println("Accession: " + currentHit.getAccesionNumber());
                System.out.println("Hit from: " + currentHit.getHitFrom());
                System.out.println("Hit to: " + currentHit.getHitTo());
                System.out.println("Sequence: " + currentHit.getSequence());
                System.out.println("Sequence Title: " + currentHit.getSequenceTitle());
                System.out.println();

            }

            makeRequestTBLASTX(hits);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //will take in hits - also needs query list
    private void makeRequestTBLASTX(ArrayList<Hit> hits) {

        for(Hit currentHit : hits){

            try {

                String queryFrom = String.valueOf(currentHit.getHitFrom());
                String queryTo = String.valueOf(currentHit.getHitTo());
                String accessionNumber = currentHit.getAccesionNumber();

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //submitting the job
                Document doc = Jsoup.connect("https://blast.ncbi.nlm.nih.gov/BlastAlign.cgi?PROGRAM=blastx&PAGE_TYPE=BlastSearch&LINK_LOC=blasthome&QUERY_FROM="+queryFrom+"&QUERY_TO="+queryTo+"&QUERY="+accessionNumber+"&SUBJECTS="+QUERIES+"&EXPECT=1e-10&CMD=Put").post();

                Element element = doc.body().getElementById("rid");
                String rid = element.attr("value");

                System.out.println(rid);


                Document doc2 = Jsoup.connect("https://blast.ncbi.nlm.nih.gov/Blast.cgi?CMD=Get&RID=" + rid + "&FORMAT_TYPE=xml").get();

                try {

                    //waiting for job to complete
                    while (doc2.body().getElementById("statInfo").attr("class").equals("WAITING")) {


                        try {
                            Thread.sleep(20000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        doc2 = Jsoup.connect("https://blast.ncbi.nlm.nih.gov/Blast.cgi?CMD=Get&RID="+rid+"&FORMAT_TYPE=xml").get();

                        //doc2 = Jsoup.connect("https://blast.ncbi.nlm.nih.gov/Blast.cgi?CMD=Get&RID=DJBG3DGC016&FORMAT_TYPE=xml" + id).get();

                    }

                    //System.out.println(doc2);


                    //no wait time
                } catch (NullPointerException e) {

                } catch (SocketTimeoutException e){

                    System.out.println("Connection Lost");
                }

                //System.out.println(doc2);

                Elements hitInformation = doc2.getElementsByTag("hit_hsps");

                for (Element currentHitHSPS : hitInformation){

                    Elements hsp = currentHitHSPS.getElementsByTag("hsp");

                    for(Element currentHsp : hsp){

                        //System.out.println("HERE");

                        //System.out.println(currentHsp.getElementsByTag("hsp_query-from").text());
                        //System.out.println(currentHsp.getElementsByTag("hsp_query-to").text());
                        //System.out.println(currentHsp.getElementsByTag("hsp_midline").text());

                        currentHit.score(currentHsp.getElementsByTag("hsp_query-from").text(),
                                currentHsp.getElementsByTag("hsp_query-to").text(), currentHsp.getElementsByTag("hsp_hseq").text());

                    }

                }

                genome.setHits(hits);


            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }
}
