/*
 *                    BioJava development code
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  If you do not have a copy,
 * see:
 *
 *      http://www.gnu.org/copyleft/lesser.html
 *
 * Copyright for this code is held jointly by the individual
 * authors.  These should be listed in @author doc comments.
 *
 * For more information on the BioJava project and its aims,
 * or to join the biojava-l mailing list, visit the home page
 * at:
 *
 *      http://www.biojava.org/
 *
 * Created on Nov 30, 2016
 * Author: AnubhavPC
 *
 */
package org.uiowa.logsdon.genespot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletContext;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/GeneSpot")
public class GeneSpotAnalysis {
	@POST
	public String Genespot(@FormParam("inputArray[]") List<String> datalist)
			throws com.firebase.client.FirebaseException, UnsupportedEncodingException {
		// Results results=Send to other
		// ResultstoFirebase
		String[] inputs = datalist.toArray(new String[0]);
		// get from website
		String[] assemblyNumber = inputs[6].split("\\(");
		System.out.println(assemblyNumber[0]);
		// file String species =;
		String type = inputs[5];
		// fileString subtype = "Vertebrate";
		// fileString assemblyType = "Chromosome";
		// fileString taxID = "9606";
		String lineFromFile = null;
		Scanner file = null;
		String filename = "/org/resources/finalData.txt";
		URL schemaURL = ResultstoFirebase.class.getResource(filename);
		File dataFile = null;
		try {
			dataFile = new File(schemaURL.toURI());
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			file = new Scanner(dataFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (file.hasNextLine()) {
			lineFromFile = file.nextLine();
			if (lineFromFile.contains(assemblyNumber[0])) {
				System.out.println(lineFromFile);
				break;
			}
		}

		String[] results = lineFromFile.split("\t");
		Genome genome = new Genome(assemblyNumber[0], results[0], type, results[3], results[5], results[1]);

		System.out.print(genome);
		//make differentation betwwen sequences and queriesID Make arrayList insead of 
		ArrayList<String> queryIDs;
		queryIDs.add("548663");
		String geneName = inputs[1];

		Gene gene = new Gene(geneName, genome, queryIDs); // will add it's self to genome if need be
		Job job = new Job()
		return "Here is your job ID," + inputs[0] + ", \ncheck back on the results page to check for completion";

	}
}
