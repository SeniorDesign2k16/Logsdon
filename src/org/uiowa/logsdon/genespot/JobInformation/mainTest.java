package org.uiowa.logsdon.genespot.JobInformation;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

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

		JobHandler jobHandler = new JobHandler();
		ProteinQuery[] queries = new ProteinQuery[1];

		ProteinQuery query1 = new ProteinQuery("548663");
		queries[0] = query1;

		Genome newGenome = new Genome("GCA_000180675.1", "IDK", "IDK", "IDK", "69293", "RAD51", queries);

		ArrayList<Genome> genomes = new ArrayList<>();
		genomes.add(newGenome);

		Job testJob = new Job("austin-test", .00000001, genomes);

		jobHandler.addJob(testJob);

	}
}
