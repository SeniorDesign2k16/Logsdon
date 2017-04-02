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
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.uiowa.logsdon.genespot.NCBI.MakeRequest;

@Path("/GeneSpot")
public class GeneSpotAnalysis {

	@POST
	public String Genespot(@FormParam("inputArray[]") List<String> datalist)
			throws UnsupportedEncodingException, FileNotFoundException {
		// [JobName,geneNmae, sequences,evalue,kingdom, subtype, (need genome), assembly level]
		String[] inputs = datalist.toArray(new String[0]);
		String jobName = inputs[0];
		System.out.println(jobName);
		String geneName = inputs[1];
		System.out.println(geneName);
		String sequences = inputs[2];
		System.out.println(sequences);
		String evaluestring = inputs[3];
		System.out.println(evaluestring);
		String kingdom = inputs[4];
		System.out.println(kingdom);
		String subtype = inputs[5];
		System.out.println(subtype);
		String genomeName = inputs[6];
		System.out.println(genomeName);
		String assembly = inputs[7];
		System.out.println(assembly);
		double evalue = Double.parseDouble(evaluestring);
		String[] queriesTotal = sequences.split("\n");
		// System.out.println(queriesTotal[0]);
		ProteinQuery[] queries = new ProteinQuery[queriesTotal.length];
		// giant loop for counter
		for (int i = 0; i < queriesTotal.length - 1; i++) {
			String[] sequence = queriesTotal[i].split("[|]");
			queries[i] = new ProteinQuery(sequence[1]);

		}

		Gene[] genes = new Gene[1];
		// pssrse a string of gene names to loop
		Gene gene = new Gene(geneName, queries);

		genes[0] = gene;

		Genome[] genomes = new Genome[1];

		// Gasterosteus aculeatus 69293 Animals Fishes GCA_000180675.1 Contig -
		// //ftp://ftp.ncbi.nlm.nih.gov/genomes/all/GCA/000/180/675/GCA_000180675.1_ASM18067v1 subtype = "Fishes";
		// Need to get tax id from parsing big ass file.
		Genome genome = new Genome(genomeName, kingdom, subtype, assembly, "69293", genes);

		genomes[0] = genome;

		Job newJob = new Job(jobName, evalue, genomes);

		MakeRequest request = new MakeRequest();

		request.sendRequest(newJob);

		return jobName;

	}
}
