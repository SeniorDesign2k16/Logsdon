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

public class ResultstoFirebase {

	DatabaseReference Genespot;

	public void SendtoGenespot(String jobName, Genome currentGenome, Gene currentGene) throws FileNotFoundException {
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
				//Species Name
					//Number of Hits  =
					//assembly =
					//presence = "lightgrey"
					//Hit Count = N;
					//Hit1 = {sequence, max ,score}
					//HitN =

		DatabaseReference cellref = Genespot.child(jobName).child(currentGene.getName()).child(currentGenome.getGenome());

		cellref.child("presence").setValue("lightgrey");


		Hit[] hits = currentGene.getHits();

		cellref.child("Hit Count").setValue(hits.length, new DatabaseReference.CompletionListener() {
			@Override
			public void onComplete(DatabaseError arg0, DatabaseReference arg1) {
				throw arg0.toException();
			}
		});

		int i = 0;

		while (i < hits.length) {

			String header = hits[i].getSequenceTitle();
			String sequence = hits[i].getSequence();
			String scoreSequence = hits[i].getScoreSequenceString();
			String max = String.valueOf(currentGene.getQueryCount());

			StringBuilder builder = new StringBuilder();
			builder.append(header);
			builder.append("|");
			builder.append(sequence);
			builder.append("|");
			builder.append(scoreSequence);
			builder.append("|");
			builder.append(max);

			cellref.child("Hit" + (i + 1)).setValue(builder.toString());
		}
	}
}
