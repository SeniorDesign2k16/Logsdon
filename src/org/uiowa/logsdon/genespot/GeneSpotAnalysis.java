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

import java.io.UnsupportedEncodingException;
import java.util.List;

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
		//get from website
		String assemblyNumber = "GCA_000001405.22 ";
		String species = "Homo Sapiens";
		String type = "Animal";
		String subtype = "Vertebrate";
		String assemblyType = "Chromosome";
		String taxID = "9606";

		Genome genome = new Genome(assemblyNumber, species, type, subtype, assemblyType, taxID);

		System.out.print(genome);

		String[] queryIDs = { "548663" };
		String geneName = inputs[1];

		Gene gene = new Gene(geneName, genome, queryIDs); // will add it's self to genome if need be
		MakeRequest requestNCBI = new MakeRequest(inputs[3]);
		requestNCBI.sendRequest(genome, geneName);
		// Inputs[jobid,gene,wordlength,e-value]
		// sendtoNCBIthing(inputs[1],inputs[2],inputs[3]);
		ResultstoFirebase send = new ResultstoFirebase();
		send.SendtoGenespot(Genome[] geneome,inputs[0]);
		return "Here is your job ID," + inputs[0]+", \ncheck back on the results page to check for completion";

	}
}
