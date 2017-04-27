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
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/GeneSpot")
public class GeneSpotAnalysis {

	// create genome objects and add them to an array list
	// create job object
	// create jobHandler object
	// submit job object to jobHandler object method addJob
	// done

	@POST
	public String Genespot(@FormParam("inputArray[]") List<String> datalist)

			throws UnsupportedEncodingException, FileNotFoundException {

		// [JobName,geneNmae, sequences,evalue,genomes]
		String[] inputs = datalist.toArray(new String[0]);
		String jobName = inputs[0];
		String[] geneName = inputs[1].split("%");
		String[] sequences = inputs[2].split("%");
		String evaluestring = inputs[3];
		String[] genomeInfo = inputs[4].replace(" ", "").split("%");
		System.out.println(Arrays.toString(genomeInfo));

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

			// put in Genome class, TAX ID IS UNIQUE FOR EACH GENOME!!! so when a genome is chosen,
			// regex all genome/tax id then seperate the other two
			for (int k = 1; k < genomeInfo.length; k++) {
				// kingdom, subtype, assembly, level type, taxID, species Name
				String[] speciesHold = genomeInfo[k].split("[|]");
				String[] info = genomeInfo[k].replace(" ", "").split("[|]");
				System.out.println(Arrays.toString(info));
				System.out.println(speciesHold[5]);
				Genome genome = new Genome(info[2], info[0], info[1], info[3], info[4], geneName[i], queries,
						speciesHold[5]);
				genomes.add(genome);
			}
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
