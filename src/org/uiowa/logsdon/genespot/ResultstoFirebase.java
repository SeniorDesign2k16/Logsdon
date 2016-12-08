package org.uiowa.logsdon.genespot;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.biojava.nbio.core.search.io.Hit;

import com.firebase.client.Firebase;

public class ResultstoFirebase {
	public static int counter = 1;

	public void SendtoGenespot(ArrayList<Hit> hitInformation, String jobId, String speciesName, String queryNumber,
			String geneName, String assembly, String kingdom)
			throws com.firebase.client.FirebaseException, UnsupportedEncodingException {
		Firebase Genespot = new Firebase("https://thegenespot-efb8a.firebaseio.com");
		// loop through with results from genome class

		Firebase cellref = Genespot.child(jobId).child(speciesName);
		cellref.child("Assembly").setValue(assembly);
		cellref.child("Kingdom").setValue(kingdom);
		cellref.child("Gene").setValue(geneName);

		int i = 1;

		for (Hit hit : hitInformation) {

			cellref.child("Gene").child(queryNumber).child("Accession Number" + String.valueOf(i))
					.setValue(hit.getAccessionNumber());
			cellref.child("Gene").child(queryNumber).child("Query Start" + String.valueOf(i))
					.setValue(hit.getQueryFrom());
			cellref.child("Gene").child(queryNumber).child("Query End" + String.valueOf(i)).setValue(hit.getQueryTo());
			cellref.child("Gene").child(queryNumber).child("Hit Start" + String.valueOf(i)).setValue(hit.getHitFrom());
			cellref.child("Gene").child(queryNumber).child("Hit End" + String.valueOf(i)).setValue(hit.getHitTo());
			cellref.child("Gene").child(queryNumber).child("Frame" + String.valueOf(i)).setValue(hit.getHitFrame());

			i++;

		}
		counter++;
	}
}
