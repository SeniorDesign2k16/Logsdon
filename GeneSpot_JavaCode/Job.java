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
	private ArrayList<String> geneNames = new ArrayList<>();

	private final double evalue;

	// Job Name Assembly Number FASTA Gene queries
	public Job(String jobName, ArrayList<Genome> genomes, double evalue, ArrayList<String> geneNames) {

		this.geneNames = geneNames;
		this.jobName = jobName;
		this.evalue = evalue;
		this.genomes = genomes;

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

	public ArrayList<String> getGeneNames(){

		return geneNames;
	}

	public void printJob() {

		int i = 0;

		System.out.println("-------------------------------");
		System.out.println("*                             *");
		System.out.println("*                             *");
		System.out.println("*      Job Name: "+getJobName()+"      *");
		System.out.println("*      E-value: "+getEvalue()+"       *");
		System.out.println("*                             *");
		System.out.println("*                             *");
		System.out.println("-------------------------------");


		while (i < genomes.size()) {

			int x = 0;

			while (x < genomes.get(i).getGenes().size()) {

				System.out.println("Genome:" + genomes.get(i).getGenome());
				System.out.println("Gene:" + genomes.get(i).getGenes().get(x).getName());
				genomes.get(i).getGenes().get(x).printQueries();

				x++;
			}
			i++;
		}

	}
}
