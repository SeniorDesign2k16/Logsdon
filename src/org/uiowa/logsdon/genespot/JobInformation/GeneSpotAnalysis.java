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
package org.uiowa.logsdon.genespot.JobInformation;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/GeneSpot")
public class GeneSpotAnalysis {

	//create genome objects and add them to an array list
	//create job object
	//create jobHandler object
	//submit job object to jobHandler object method addJob
	//done

	@POST
	public String Genespot(@FormParam("inputArray[]") List<String> datalist)

			throws UnsupportedEncodingException, FileNotFoundException {

		// [JobName,geneNmae, sequences,evalue,kingdom, subtype, (need genome), assembly level]
		String[] inputs = datalist.toArray(new String[0]);
		String jobName = inputs[0];
		String[] geneName = inputs[1].split("%");
		String[] sequences = inputs[2].split("%");
		String evaluestring = inputs[3];
		String kingdom = inputs[4];
		String subtype = inputs[5];
		String genomeName = inputs[6];
		String assembly = inputs[7];
		Gene[] genes = new Gene[geneName.length];
		// change to size of genome array
		ArrayList<Genome> genomes = new ArrayList<Genome>();
		double evalue = Double.parseDouble(evaluestring);
		for (int i = 0; i < sequences.length; i++) {
			String[] queriesTotal = sequences[i].split("\n");
			// System.out.println(queriesTotal[0]);
			int n = 0;
			if (queriesTotal.length > 30) {
				n = 31;
			} else {
				n = queriesTotal.length;
			}
			ProteinQuery[] queries = new ProteinQuery[queriesTotal.length];
			// giant loop for counter
			for (int j = 0; j < n; j++) {
				String[] sequence = queriesTotal[j].split("[|]");
				queries[j] = new ProteinQuery(sequence[1]);

			}

			// put in Genome class
			Genome genome = new Genome(genomeName, kingdom, subtype, assembly, "69293", geneName[i], queries);

			genomes.add(genome);
		}

		// Gasterosteus aculeatus 69293 Animals Fishes GCA_000180675.1 Contig -
		// //ftp://ftp.ncbi.nlm.nih.gov/genomes/all/GCA/000/180/675/GCA_000180675.1_ASM18067v1 subtype = "Fishes";
		// Need to get tax id from parsing big file.

		Job newJob = new Job(jobName, evalue, genomes);

		JobHandler request = new JobHandler();

		request.addJob(newJob);

		return jobName;
	}
}
