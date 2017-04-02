package org.uiowa.logsdon.genespot.JobInformation;
/*

 *
 * Created on Dec 2, 2016
 * Author: austinward 
 *
 */

import java.util.ArrayList;

public class Gene {

	private final String geneName;
	private final ProteinQuery[] proteinQueries;

	private ArrayList<Hit> hits;

	public Gene(String geneName, ProteinQuery[] proteinQueries) {

		this.geneName = geneName;
		this.proteinQueries = proteinQueries;
	}

	public String getName() {

		return geneName;
	}

	public ProteinQuery[] getProteinQueries() {

		return this.proteinQueries;
	}

	public void setHits(ArrayList<Hit> hits){

		this.hits = hits;
	}

	public Hit[] getHits() {

		return hits.toArray(new Hit[0]);

	}
}
