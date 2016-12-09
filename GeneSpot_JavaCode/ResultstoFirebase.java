package org.uiowa.logsdon.genespot;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.firebase.client.Firebase;

public class ResultstoFirebase {
	private static int counter = 1;
	private String speciesHold = "";
	
	public void SendtoGenespot(ArrayList<Hit> hitInformation, String jobId, String speciesName, String queryNumber,
			String geneName, String assembly, String kingdom, int queryCount)
			throws com.firebase.client.FirebaseException, UnsupportedEncodingException {
		Firebase Genespot = new Firebase("https://thegenespot-efb8a.firebaseio.com");
		// loop through with results from genome class

		if(!speciesHold.equals(speciesName)){
			
			Firebase cellref = Genespot.child(jobId).child(speciesName);
			cellref.child("Assembly").setValue(assembly);
			cellref.child("Kingdom").setValue(kingdom);
			cellref.child("Gene").setValue(geneName);
			cellref.child("Query Count").setValue(queryCount);
			
			speciesHold = speciesName;
			counter = 1;
			
		}

		Firebase newRef = Genespot.child(jobId).child(speciesName).child("Query"+String.valueOf(counter)).child(queryNumber);
		
		for (Hit hit : hitInformation) {

			newRef.child("Accession Number").setValue(hit.getAccessionNumber());
			newRef.child("Query Start").setValue(hit.getQueryFrom());
			newRef.child("Query End").setValue(hit.getQueryTo());
			newRef.child("Hit Start").setValue(hit.getHitFrom());
			newRef.child("Hit End").setValue(hit.getHitTo());
			newRef.child("Frame").setValue(hit.getHitFrame());
		}
		
		counter++;
	}
}
