package org.uiowa.logsdon.genespot.NCBI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.uiowa.logsdon.genespot.JobInformation.Gene;
import org.uiowa.logsdon.genespot.JobInformation.Genome;
import org.uiowa.logsdon.genespot.JobInformation.Hit;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ResultstoFirebaseCopy {
	DatabaseReference Genespot;

	public void SendtoGenespot(String jobName, Genome currentGenome, Gene currentGene) throws FileNotFoundException {
		FileInputStream serviceAccount = new FileInputStream(ResultstoFirebaseCopy.class.getClassLoader()
				.getResource("thegenespot-efb8a-firebase-adminsdk-1phn3-cbe3ab49a4.json").getPath()
				.replaceAll("%20", " "));

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
				.setDatabaseUrl("https://thegenespot-efb8a.firebaseio.com/").build();

		FirebaseApp.initializeApp(options);

		Genespot = FirebaseDatabase.getInstance().getReference();
		// loop through with results from genome class

		DatabaseReference cellref = Genespot.child(jobName).child(currentGenome.getKingdom())
				.child(currentGenome.getSubType()).child(currentGenome.getTaxID())
				.child(currentGenome.getGenome().replace('.', '_')).child(currentGene.getName());

		Hit[] hits = currentGene.getHits();

		cellref.child("Hit Count").setValue(hits.length, new DatabaseReference.CompletionListener() {
			@Override
			public void onComplete(DatabaseError arg0, DatabaseReference arg1) {
				throw arg0.toException();
			}
		});

		int i = 0;

		while (i < hits.length) {
			cellref.child("Hit" + (i + 1)).child("Accession Number").setValue(
					hits[i].getAccesionNumber().replace('.', 'v'), new DatabaseReference.CompletionListener() {
						@Override
						public void onComplete(DatabaseError arg0, DatabaseReference arg1) {
							throw arg0.toException();
						}
					});
			cellref.child("Hit" + (i + 1)).child("Hit From").setValue(hits[i].getHitFrom(),
					new DatabaseReference.CompletionListener() {
						@Override
						public void onComplete(DatabaseError arg0, DatabaseReference arg1) {
							throw arg0.toException();
						}
					});
			cellref.child("Hit" + (i + 1)).child("Hit To").setValue(hits[i].getHitTo(),
					new DatabaseReference.CompletionListener() {
						@Override
						public void onComplete(DatabaseError arg0, DatabaseReference arg1) {
							throw arg0.toException();
						}
					});

			i++;
		}
	}
}
