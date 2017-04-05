package org.uiowa.logsdon.genespot.NCBI;

import org.uiowa.logsdon.genespot.JobInformation.Gene;
import org.uiowa.logsdon.genespot.JobInformation.Genome;
import org.uiowa.logsdon.genespot.JobInformation.Hit;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by austinward on 4/5/17.
 */
public class GeneDatabase {

    DatabaseReference Genespot;

    public void update(String jobName, Genome currentGenome) throws FileNotFoundException {
        FileInputStream serviceAccount = new FileInputStream(ResultstoFirebase.class.getClassLoader()
                .getResource("thegenespot-efb8a-firebase-adminsdk-1phn3-cbe3ab49a4.json").getPath()
                .replaceAll("%20", " "));

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                .setDatabaseUrl("https://thegenespot-efb8a.firebaseio.com/").build();

        FirebaseApp.initializeApp(options);

        Genespot = FirebaseDatabase.getInstance().getReference();
        // loop through with results from genome class

        //JobName
            //GeneName
                //Kingdom
                    //subtype
                        //taxID
                            //assembly Number 1
                                 //Hits : {{sequence, max score,score}, }
                            //assembly Number 2
                                //Hits : {{sequence, score}}

        DatabaseReference cellref = Genespot.child(jobName).child(currentGenome.getGeneName()).child(currentGenome.getKingdom())
                .child(currentGenome.getSubType()).child(currentGenome.getTaxID())
                .child(currentGenome.getGenome().replace('.', '_'));

        int queryCount = currentGenome.getQueryCount();
        Hit[] hits = currentGenome.getHits();

        ArrayList<String[]> hitArray = new ArrayList<>();

        for(Hit currentHit : hits){

            String[] array = new String[3];
            array[0] = currentHit.getSequence();
            array[1] = String.valueOf(queryCount);
            array[2] = currentHit.getScoreSequenceString();

            hitArray.add(array);
        }

        cellref.child("Hits").setValue(hitArray.toArray());

        /*

        cellref.child("Hit" + (i + 1)).child("Accession Number").setValue(
                hits[i].getAccesionNumber().replace('.', 'v'), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError arg0, DatabaseReference arg1) {
                        throw arg0.toException();
                    }
                });

        */
    }
}
