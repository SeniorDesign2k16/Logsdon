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

		String assemblyNumber = "GCA_000001405.22 ";
		String species = "Homo Sapiens";
		String type = "Animal";
		String subtype = "Vertebrate";
		String assemblyType = "Chromosome";
		String taxID = "9606";

		Genome genome = new Genome(assemblyNumber, species, type, subtype, assemblyType, taxID);

		System.out.print(genome);

		String[] queryIDs = { "548663" };
		String geneName = "RAD51";

		Gene gene = new Gene(geneName, genome, queryIDs); // will add it's self to genome if need be

		requestNCBI.sendRequest(genome, geneName);

	}
}
