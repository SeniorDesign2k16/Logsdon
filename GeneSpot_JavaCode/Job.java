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
 * Created on Dec 5, 2016
 * Author: austinward 
 *
 */

/*

This object will be made once the server a request from 


*/
public class Job {

	private final String jobName;
	private ArrayList<Genome> genomes = new ArrayList<>();

	private final double evalue;
	private final String geneName;

	// Job Name Assembly Number FASTA Gene queries
	public Job(String jobName, ArrayList<String> genomesOfInterest, ArrayList<String> speciesName,
			ArrayList<String> kingdoms, ArrayList<String> subTypes, ArrayList<String> genesOfInterest, String geneName,
			double evalue, ArrayList<String> assemblyType, ArrayList<String> taxIDs) {

		this.jobName = jobName;
		this.evalue = evalue;
		this.geneName = geneName;

		int i = 0;
		
		while (i < genomesOfInterest.size()) {

			Genome currentGenome = new Genome(genomesOfInterest.get(i), speciesName.get(i), kingdoms.get(i),
					subTypes.get(i), assemblyType.get(i), taxIDs.get(i));

			new Gene(geneName, currentGenome, genesOfInterest); // need to parse which kingdom each query is coming from
																// in Gene Object

			genomes.add(currentGenome);

			updateDB(); // Initializes database

			i++;
		}
	}

	// called after each genome has gone through the MakeRequest protocol
	public void updateDB() {

		// Anu's code here

	}

	public ArrayList<Genome> getGenomes() {

		return genomes;
	}

	public String getJobName() {

		return jobName;
	}

	public double getEvalue() {

		return evalue;
	}

	public String getGeneName() {

		return geneName;
	}

	public void printGenomes() {

		int i = 0;

		while (i < genomes.size()) {

			System.out.println("Genome:" + genomes.get(i).getGenome());
			int x = 0;
			while (x < genomes.get(i).getGenes().size()) {

				System.out.println("     Gene:" + genomes.get(i).getGenes().get(x));

				x++;
			}

			i++;
		}

	}
}
