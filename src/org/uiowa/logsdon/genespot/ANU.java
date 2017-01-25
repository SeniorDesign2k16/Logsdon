package org.uiowa.logsdon.genespot;

import java.io.UnsupportedEncodingException;

import com.firebase.client.Firebase;

public class ResultstoFirebase {
	public static void SendtoGenespot(Results[] result, String jobId, int cell, boolean done)
			throws com.firebase.client.FirebaseException, UnsupportedEncodingException {
		Firebase Genespot = new Firebase("https://thegenespot-efb8a.firebaseio.com");
		// loop through with results from genome class

		Firebase cellref = Genespot.child(jobId).child("Cell" + cell);
		// change setvalue to get gene
		cellref.child("Gene").setValue("Rad51");
		cellref.child("Organism").setValue("Human");
		cellref.child("Present").setValue(1);
		Genespot.child(jobId).child("Complete").setValue(done);

	}
}
