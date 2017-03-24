package org.uiowa.logsdon.genespot.NCBI;

import org.uiowa.logsdon.genespot.JobInformation.Hit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by austinward on 1/2/17.
 */



public class SequenceRetrieval {


    public void getSequence(Hit[] hits){

        for(Hit currentHit : hits) {

            String start;

            if (currentHit.getHitFrom() - 500 < 0) {

                start = "0";
            } else {

                start = String.valueOf(currentHit.getHitFrom());
            }


            String end = String.valueOf(currentHit.getHitTo() + 500);
            String accession = currentHit.getAccesionNumber().split("\\|")[1];


            String url = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=nuccore&id=" + accession + "&seq_start=" + start + "&seq_stop=" + end + "&rettype=fasta";

            try {

                URL entrez = new URL(url);


                BufferedReader in = new BufferedReader(new InputStreamReader(entrez.openStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    System.out.println(inputLine);
                in.close();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
