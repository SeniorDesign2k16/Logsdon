package NCBI;

import java.io.UnsupportedEncodingException;
import JobInformation.*;
import com.firebase.client.Firebase;

public class ResultstoFirebaseCopy {

    public void SendtoGenespot(String jobName, Genome currentGenome, Gene currentGene)
            throws com.firebase.client.FirebaseException, UnsupportedEncodingException {

        Firebase Genespot = new Firebase("https://thegenespot-efb8a.firebaseio.com/");
        // loop through with results from genome class

        Firebase cellref = Genespot.child(jobName).child(currentGene.getName()).child(currentGenome.getGenome().replace('.', 'v'));


        Hit[] hits = currentGene.getHits();

        cellref.child("Hit Count").setValue(hits.length);


        int i = 0;

        while(i < hits.length){

            cellref.child("Hit"+(i+1)).child("Accession Number").setValue(hits[i].getAccesionNumber().replace('.', 'v'));
            cellref.child("Hit"+(i+1)).child("Hit From").setValue(hits[i].getHitFrom());
            cellref.child("Hit"+(i+1)).child("Hit To").setValue(hits[i].getHitTo());

            i++;
        }
    }
}
