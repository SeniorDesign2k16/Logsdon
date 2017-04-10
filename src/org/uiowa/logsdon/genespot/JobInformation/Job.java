package org.uiowa.logsdon.genespot.JobInformation;
/*
 *
 * Created on Dec 5, 2016
 * Author: austinward 
 *
 */

import java.util.ArrayList;

/*

This object will be made once the server accepts a request from

*/
public class Job {

	private final String jobName; // name of job
	private final double evalue; // evalue for gene search

	private ArrayList<Genome> genomesOfInterest;

	// JobInformation.JobInformation Name -- evalue threshold --- number of genomes
	public Job(String jobName, double evalue, ArrayList<Genome> genomesOfInterest) {

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

	public ArrayList<Genome> getGenomesOfInterest() {

		return this.genomesOfInterest;
	}
}
