package org.uiowa.logsdon.genespot.JobInformation;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.uiowa.logsdon.genespot.NCBI.MakeRequest;

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
 * Author: austinward 
 *
 */
public class mainTest {

	public static void main(String[] argv) throws UnsupportedEncodingException, FileNotFoundException {

		/*
		String jobName = "UpdatedTest2";

		double evalue = .00000000001;

		ProteinQuery[] queries = new ProteinQuery[2];
		queries[0] = new ProteinQuery("548663");
		queries[1] = new ProteinQuery("514692457");

		Gene[] genes = new Gene[1];
		Gene gene = new Gene("Rad51", queries);

		genes[0] = gene;

		Genome[] genomes = new Genome[1];

		// Gasterosteus aculeatus 69293 Animals Fishes GCA_000180675.1 Contig -
		// ftp://ftp.ncbi.nlm.nih.gov/genomes/all/GCA/000/180/675/GCA_000180675.1_ASM18067v1
		Genome genome = new Genome("GCA_000180675.1", "Animal", "Fishes", "Contig", "69293", genes);

		genomes[0] = genome;

		Job newJob = new Job(jobName, evalue, genomes);

		MakeRequest request = new MakeRequest();

		request.sendRequest(newJob);
		*/

	}
}
