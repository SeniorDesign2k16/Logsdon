package org.uiowa.logsdon.NCBI;

import java.io.UnsupportedEncodingException;
import JobInformation.*;
import com.firebase.client.Firebase;

public class ResultstoFirebaseCopy {

    public void SendtoGenespot(String jobName, Genome currentGenome, Gene currentGene)
            throws com.firebase.client.FirebaseException, UnsupportedEncodingException {

        Firebase Genespot = new Firebase("https://thegenespot-efb8a.firebaseio.com");
        // loop through with results from genome class


        Firebase cellref = Genespot.child(jobName).child(currentGenome.getKingdom()).child(currentGenome.getSubType())
                .child(currentGenome.getTaxID()).child(currentGenome.getGenome()).child(currentGene.getName());


        ProteinQuery[] queries = currentGene.getProteinQueries();

        cellref.child("Query Count").setValue(queries.length);


        int i = 0;

        while(i < queries.length){

            cellref.child("Query"+(i+1)).child("Protein ID").setValue(queries[i].getQueryNumber());

            Hit[] hits = queries[i].getHitObjects();

            cellref.child("Query"+(i+1)).child("Hit Count").setValue(hits.length);

            int x = 1;

            for(Hit currentHit : hits){

                cellref.child("Query"+(i+1)).child("Hit"+x).child("Accesion Number").setValue(currentHit.getAccesionNumber());
                cellref.child("Query"+(i+1)).child("Hit"+x).child("Hit Start").setValue(currentHit.getHitFrom());
                cellref.child("Query"+(i+1)).child("Hit"+x).child("Hit End").setValue(currentHit.getHitTo());
                cellref.child("Query"+(i+1)).child("Hit"+x).child("Hit Frame").setValue(currentHit.getHitFrom());

                x++;
            }

            i++;
        }
    }
}
