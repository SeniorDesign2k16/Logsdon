package org.uiowa.logsdon.genespot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

	public static void main(String[] argv) {

		MakeRequest requestNCBI = new MakeRequest();

		// see that connection is made to the server

		// user has two options: make query, or get a past job

		// get information from user

		// create new Job object --- takes in job name, genomes of interest, genes of interest

		// see if new Genome and/or Gene objects need to be created

		// if new Gene name found, add newly made Gene Object to Genome object

		// send Genome object to MakeRequest

		// else return information to be presented to user

		// String genome, String species, String type, String subType, String assemblyType, String taxID

		File file = new File("testingData.txt");

		String jobName = "Prototype";
		ArrayList<String> genomesOfInterest = new ArrayList<>();
		ArrayList<String> speciesName = new ArrayList<>();
		ArrayList<String> kingdoms = new ArrayList<>();
		ArrayList<String> subTypes = new ArrayList<>();
		ArrayList<String> genesOfInterest = new ArrayList<>();

		genesOfInterest.add("548663");
		genesOfInterest.add("514692457");
		genesOfInterest.add("4275");
		genesOfInterest.add("18420327");
		genesOfInterest.add("511003109");
		/*
		genesOfInterest.add("9904315");
		genesOfInterest.add("159469155");
		genesOfInterest.add("123408472");
		genesOfInterest.add("2108337");
		genesOfInterest.add("395394859");
		genesOfInterest.add("162605684");
		genesOfInterest.add("66822135");
		genesOfInterest.add("923121388");
		genesOfInterest.add("551554835");
		genesOfInterest.add("569359648");
		genesOfInterest.add("301098091");
		*/

		String geneName = "RAD51";
		double evalue = .00000000001;
		ArrayList<String> assemblyType = new ArrayList<>();
		ArrayList<String> taxIDs = new ArrayList<>();

		String[] hold;
		try {

			BufferedReader reader = new BufferedReader(new FileReader(file));
			String text = null;

			while ((text = reader.readLine()) != null) {

				hold = text.split("\t");

				speciesName.add(hold[0]);
				taxIDs.add(hold[1]);
				kingdoms.add(hold[2]);
				subTypes.add(hold[3]);
				genomesOfInterest.add(hold[4]);
				assemblyType.add(hold[5]);

			}
			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		Job testJob = new Job(jobName, genomesOfInterest, speciesName, kingdoms, subTypes, genesOfInterest, geneName,
				evalue, assemblyType, taxIDs);

		testJob.printGenomes();

		MakeRequest sendJob = new MakeRequest();

		sendJob.sendRequest(testJob);

	}
}
