package org.uiowa.logsdon.JobInformation;
/*
 *
 * Created on Dec 5, 2016
 * Author: austinward 
 *
 */

/*

This object will be made once the server accepts a request from

*/
public class Job {

	private final String jobName;								//name of job
	private final double evalue;								//evalue for gene search

	private Genome[] genomesOfInterest;

	// JobInformation.JobInformation Name -- evalue threshold --- number of genomes
	public Job(String jobName, double evalue, Genome[] genomesOfInterest) {

		this.jobName = jobName;
		this.evalue = evalue;
		this.genomesOfInterest = genomesOfInterest;
	}

	public String getJobName() {

		return jobName;
	}

	public double getEvalue() {

		return evalue;
	}

	public void addGenomes(Genome[] newGenomes){

		int newGenomeCount = newGenomes.length;
		int oldGenomeCount = this.genomesOfInterest.length;

		Genome[] oldGenomes = this.genomesOfInterest;

		this.genomesOfInterest = new Genome[newGenomeCount + oldGenomeCount];

		int counter = 0;

		//adding back the prior genomes of interest
		for(Genome currentGenome : oldGenomes ){

			genomesOfInterest[counter] = currentGenome;

			counter++;
		}

		//adding new genomes of interest
		for(Genome currentGenome : newGenomes){


			genomesOfInterest[counter] = currentGenome;

			counter++;
		}
	}

	public Genome[] getGenomesOfInterest(){

		return this.genomesOfInterest;
	}
}
