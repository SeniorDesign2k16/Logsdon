
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
		ArrayList<ArrayList> accessionNumber = new ArrayList<>();
		ArrayList<ArrayList> queryStart = new ArrayList<>();
		ArrayList<ArrayList> queryEnd = new ArrayList<>();
		ArrayList<ArrayList> hitStart = new ArrayList<>();
		ArrayList<ArrayList> hitEnd = new ArrayList<>();
		ArrayList<ArrayList> frame = new ArrayList<>();

		
		for (Hit hit : hitInformation) {

			accessionNumber.add(hit.getAccessionNumber());
			queryStart.add(hit.getQueryFrom());
			queryEnd.add(hit.getQueryTo());
			hitStart.add(hit.getHitFrom());
			hitEnd.add(hit.getHitTo());
			frame.add(hit.getHitFrame());
		}
		
		newRef.child("Accession Number").setValue(accessionNumber);
		newRef.child("Query Start").setValue(queryStart);
		newRef.child("Query End").setValue(queryEnd);
		newRef.child("Hit Start").setValue(hitStart);
		newRef.child("Hit End").setValue(hitEnd);
		newRef.child("Frame").setValue(frame);
		
		counter++;
	}
}
