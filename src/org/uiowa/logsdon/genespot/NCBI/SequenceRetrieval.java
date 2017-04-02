package org.uiowa.logsdon.genespot.NCBI;

import org.uiowa.logsdon.genespot.JobInformation.Hit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by austinward on 1/2/17.
 */



public class SequenceRetrieval {


    public void getSequence(ArrayList<Hit> hits){

        for(Hit currentHit : hits) {

            String start;

            if (currentHit.getHitFrom() - 500 <= 0) {

                start = "0";

            } else {

                start = String.valueOf(currentHit.getHitFrom() - 500 );
            }

            String end = String.valueOf(currentHit.getHitTo() + 500);

            currentHit.setHitFrom(Integer.parseInt(start));
            currentHit.setHitTo(Integer.parseInt(end));

            String accession = currentHit.getAccesionNumber();

            String url = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=nuccore&id=" + accession + "&seq_start=" + start + "&seq_stop=" + end + "&rettype=fasta";

            try {

                URL entrez = new URL(url);


                BufferedReader in = new BufferedReader(new InputStreamReader(entrez.openStream()));

                String inputLine;

                StringBuilder sequence = new StringBuilder();
                StringBuilder sequenceTitle = new StringBuilder();
                while ((inputLine = in.readLine()) != null){

                    //System.out.println(inputLine);
                    if(inputLine.startsWith(">")){

                        sequenceTitle.append(inputLine.replaceFirst("\\n",""));

                    }

                    else{

                        sequence.append(inputLine.replaceAll("\\n","").replace("\r", ""));
                    }

                }


                currentHit.setSequence(sequence.toString());                            //setting the found found sequence
                currentHit.setSequenceTitle(sequenceTitle.toString());                  //setting the sequence title

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
